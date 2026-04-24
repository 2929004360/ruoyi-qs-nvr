package com.ruoyi.haikang.isup.handler;

import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.isup.manager.StreamManager;
import com.ruoyi.haikang.isup.service.haikang.stream.HCISUPStream;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 预览流处理器 - 每个预览会话独立的回调实例
 * 注意：不使用 @Component/@Service 注解，避免单例模式导致的状态混乱
 */
@Slf4j
public class PreviewStreamHandler implements HCISUPStream.PREVIEW_DATA_CB {
    // 状态变量
    private int seqNum = 0;
    private int currentTimestamp = 0;
    private final int CLOCK_RATE = 90000;
    private final int FPS = 25;
    private final int TIMESTAMP_INCREMENT = CLOCK_RATE / FPS; // 3600

    // 存储每个预览句柄对应的连接信息
    private class RtpConnection {
        int seqNum = 0;
        int timestamp = 0;
        int rtpPort = 0;
        String ssrc;
        DatagramSocket udpSocket;
        InetAddress targetAddress;
    }

    // 使用线程安全的 Map 存储每个句柄对应的连接
    public final Map<Integer, RtpConnection> connectionMap = new ConcurrentHashMap<>();

    @Override
    public void invoke(int iPreviewHandle, HCISUPStream.NET_EHOME_PREVIEW_CB_MSG pPreviewCBMsg, Pointer pUserData) throws IOException {
        // 通过 iPreviewHandle 获取 sessionID
        Integer sessionID = StreamManager.previewHandSAndSessionIDandMap.get(iPreviewHandle);
        if (sessionID == null) {
//            log.error("未找到预览句柄 {} 对应的 sessionID", iPreviewHandle);
            return;
        }

        // 通过 sessionID 获取对rtpServerParam
        RtpServerParam rtpServerParam = StreamManager.luserIdAndRtpServerParamMap.get(sessionID);

        if (rtpServerParam == null) {
//            log.error("未找到 sessionID {} 对应的 rtpServerParam", sessionID);
            return;
        }

        // 获取或创建该句柄对应的连接
        RtpConnection connection = connectionMap.computeIfAbsent(iPreviewHandle, handle -> {
            RtpConnection conn = new RtpConnection();
            try {
                // 1. 解析目标地址
                conn.targetAddress = InetAddress.getByName(rtpServerParam.getIp());
                // 2. 创建本地 UDP Socket
                conn.udpSocket = new DatagramSocket();
                // 3. 设置 SSRC
                conn.ssrc = rtpServerParam.getSsrc();
                conn.rtpPort = rtpServerParam.getPort();
                log.info("预览句柄: {} ==== RTP Socket创建成功，ip: {}, 端口: {}, ssrc: {}, sessionID: {}", iPreviewHandle, rtpServerParam.getIp(), rtpServerParam.getPort(), rtpServerParam.getSsrc(), sessionID);
            } catch (Exception e) {
                log.error("创建RTP Socket失败，句柄: {}, ip: {}, 端口: {}, ssrc: {}, sessionID: {}", iPreviewHandle, rtpServerParam.getIp(), rtpServerParam.getPort(), rtpServerParam.getSsrc(), sessionID, e);
                return null;
            }
            return conn;
        });

        if (connection == null || connection.udpSocket == null || connection.targetAddress == null) {
            log.error("RTP连接不可用，句柄: {}", iPreviewHandle);
            return;
        }

        byte[] dataStream = pPreviewCBMsg.pRecvdata.getByteArray(0, pPreviewCBMsg.dwDataLen);
        if (dataStream != null && dataStream.length > 0) {
            if (pPreviewCBMsg.byDataType == 2) {
                int dwBufSize = pPreviewCBMsg.dwDataLen;
                Pointer pBuffer = pPreviewCBMsg.pRecvdata;
                // 【修复点 1】时间戳逻辑：一帧只有一个时间戳，不要在循环里累加
                int frameTimestamp = currentTimestamp;
                currentTimestamp += TIMESTAMP_INCREMENT; // 为下一帧准备

                byte pt = 96; // H.264 Payload Type

                ByteBuffer buffer = ByteBuffer.allocate(4);

                buffer.order(ByteOrder.BIG_ENDIAN);

                buffer.putInt(Integer.parseInt(connection.ssrc));
                byte[] ssrc = buffer.array();
                byte[] tsBytes = intToBytes(frameTimestamp);

                // 缓冲区大小 (MTU 1400 - UDP 头 - IP 头，这里留足余量)
                // RTP Header = 12 字节
                int maxPayloadSize = 1400 - 12;
                byte[] rtpPacket = new byte[1400];

                // --- 构建标准 RTP Header (12 Bytes) ---
                // 注意：我们不再在开头放“长度”，而是直接放标准的 RTP 头
                // Index 0: Version(2) + P(0) + X(0) + CC(0) = 0x80
                rtpPacket[0] = (byte) 0x80;

                // Index 1: Marker(0) + PayloadType(7bits). Marker 稍后根据情况设置
                rtpPacket[1] = (byte) (pt & 0x7F);

                // Index 2-3: Sequence Number (Big Endian) - 标准位置
                // Index 4-7: Timestamp
                // Index 8-11: SSRC

                int offset = 0;
                int dataSize = dwBufSize;

                while (dataSize > 0) {
                    int chunkSize = Math.min(maxPayloadSize, dataSize);
                    boolean isLastFragment = (dataSize <= maxPayloadSize);

                    // 1. 写入/更新 Header
                    // 序列号自增
                    seqNum++;
                    byte[] seqBytes = shortToBytes(seqNum);
                    rtpPacket[2] = seqBytes[0];
                    rtpPacket[3] = seqBytes[1];

                    // 时间戳 (本帧所有包相同)
                    System.arraycopy(tsBytes, 0, rtpPacket, 4, 4);

                    // SSRC
                    System.arraycopy(ssrc, 0, rtpPacket, 8, 4);

                    // Marker 位：只有最后一个包设为 1
                    if (isLastFragment) {
                        rtpPacket[1] = (byte) (rtpPacket[1] | 0x80);
                    } else {
                        rtpPacket[1] = (byte) (rtpPacket[1] & 0x7F); // 确保中间包 Marker 为 0
                    }


                    // 2. 读取 Payload 数据
                    // 从 pBuffer 读取 chunkSize 长度的数据到 rtpPacket 的第 12 字节之后
                    pBuffer.read(offset, rtpPacket, 12, chunkSize);

                    // 3. 发送 UDP 包
                    int packetLength = 12 + chunkSize;
                    DatagramPacket packet = new DatagramPacket(rtpPacket, packetLength, connection.targetAddress, connection.rtpPort);
                    connection.udpSocket.send(packet);

                    // 更新偏移和剩余大小
                    offset += chunkSize;
                    dataSize -= chunkSize;
                }
            }
        }
    }

    /**
     * 关闭指定句柄的RTP连接
     *
     * @param iPreviewHandle 预览句柄
     */
    public void close(int iPreviewHandle) {
        RtpConnection connection = connectionMap.remove(iPreviewHandle);
        if (connection != null && connection.udpSocket != null && !connection.udpSocket.isClosed()) {
            try {
                connection.udpSocket.close();
                log.info("关闭RTP连接成功，句柄: {}", iPreviewHandle);
            } catch (Exception e) {
                log.error("关闭RTP连接失败，句柄: {}", iPreviewHandle, e);
            }
        }
    }

    /**
     * 将int值转换为4字节的字节数组 大端序
     *
     * @param value 要转换的int值
     * @return 包含该int值的4字节表示形式的字节数组
     */
    private byte[] intToBytes(int value) {
        return new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value
        };
    }

    /**
     * 将short值转换为2字节的字节数组 大端序
     *
     * @param value 要转换的int值
     * @return 包含该int值的2字节表示形式的字节数组
     */
    private byte[] shortToBytes(int value) {
        return new byte[]{
                (byte) (value >>> 8),
                (byte) value
        };
    }
}
