package com.ruoyi.haikang.callback;

import com.ruoyi.haikang.net.HCNetSDK;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.ByteByReference;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FRealDataForRtpOverTcpCallback implements HCNetSDK.FRealDataCallBack_V30 {
    private final String host;

    private String ssrc;
    private final int rtpPort;

    private DatagramSocket udpSocket;
    private InetAddress targetAddress;

    // 状态变量
    private int seqNum = 0;
    private int currentTimestamp = 0;
    private static final int CLOCK_RATE = 90000;
    private static final int FPS = 25;
    private static final int TIMESTAMP_INCREMENT = CLOCK_RATE / FPS; // 3600

    public FRealDataForRtpOverTcpCallback(String host, Integer rtpPort, String ssrc) {
        this.host = host;
        this.rtpPort = rtpPort;
        try {
            // 1. 解析目标地址
            this.targetAddress = InetAddress.getByName(host);
            // 2. 创建本地 UDP Socket
            udpSocket = new DatagramSocket();
            // 3. 设置 SSRC
            this.ssrc = ssrc;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * int 转 4 字节大端序
     */
    public static byte[] intToBytes(int value) {
        return new byte[]{(byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value};
    }

    /**
     * int 转 2 字节大端序
     */
    public static byte[] shortToBytes(int value) {
        return new byte[]{(byte) (value >>> 8), (byte) value};
    }

    public void close() {
        if (udpSocket != null && !udpSocket.isClosed()) {
            udpSocket.close();
        }
    }

    @Override
    public void invoke(int lRealHandle, int dwDataType, ByteByReference pBuffer, int dwBufSize, Pointer pUser) throws IOException {
        if (udpSocket == null || targetAddress == null) {
            return;
        }

        if (dwDataType == HCNetSDK.NET_DVR_STREAMDATA) {
            // 【修复点 1】时间戳逻辑：一帧只有一个时间戳，不要在循环里累加
            int frameTimestamp = currentTimestamp;
            currentTimestamp += TIMESTAMP_INCREMENT; // 为下一帧准备

            byte pt = 96; // H.264 Payload Type

            ByteBuffer buffer = ByteBuffer.allocate(4);

            buffer.order(ByteOrder.BIG_ENDIAN);

            buffer.putInt(Integer.parseInt(this.ssrc));
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
                pBuffer.getPointer().read(offset, rtpPacket, 12, chunkSize);

                // 3. 发送 UDP 包
                int packetLength = 12 + chunkSize;
                DatagramPacket packet = new DatagramPacket(rtpPacket, packetLength, targetAddress, rtpPort);
                udpSocket.send(packet);

                // 更新偏移和剩余大小
                offset += chunkSize;
                dataSize -= chunkSize;
            }
        }
    }
}