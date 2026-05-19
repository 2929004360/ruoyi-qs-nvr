package com.ruoyi.haikang.isup.handler;

import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.isup.manager.StreamManager;
import com.ruoyi.haikang.isup.service.haikang.stream.HCISUPStream;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 回放流处理器 - 每个回放会话独立的回调实例
 * 注意：不使用 @Component/@Service 注解，避免单例模式导致的状态混乱
 */
@Slf4j
public class PlaybackStreamHandler implements HCISUPStream.PLAYBACK_DATA_CB {
    // 常量
    private static final int CLOCK_RATE = 90000;
    private static final int FPS = 25;
    private static final int TIMESTAMP_INCREMENT = CLOCK_RATE / FPS; // 3600
    private static final int MAX_PAYLOAD_SIZE = 1400 - 12; // RTP包大小减去头部

    // 下载相关的latch map - 静态，用于跨实例访问
    public static final Map<String, CountDownLatch> downloadLatchMap = new ConcurrentHashMap<>();

    // 存储每个回放句柄对应的连接信息
    private class RtpConnection {
        int seqNum = 0;
        int timestamp = 0;
        int rtpPort = 0;
        String ssrc;
        DatagramSocket udpSocket;
        InetAddress targetAddress;
        
        // 帧队列相关
        ByteArrayOutputStream currentFrameBuffer = new ByteArrayOutputStream();
        LinkedBlockingQueue<byte[]> frameQueue = new LinkedBlockingQueue<>();
        
        // 发送线程
        Thread sendThread;
        volatile boolean running = true;
    }

    // 使用线程安全的 Map 存储每个句柄对应的连接
    public final Map<Integer, RtpConnection> connectionMap = new ConcurrentHashMap<>();

    @Override
    public boolean invoke(int iPlayBackLinkHandle, HCISUPStream.NET_EHOME_PLAYBACK_DATA_CB_INFO pDataCBInfo, Pointer pUserData) {
        // 通过 iPlayBackLinkHandle 获取 sessionID
        Integer sessionID = StreamManager.playbackHandSAndSessionIDandMap.get(iPlayBackLinkHandle);
        if (sessionID == null) {
            return true;
        }

        // 获取回放类型
        String type = StreamManager.playbackUserIDandTypeMap.get(sessionID);
        
        if ("download".equals(type)) {
            // 下载模式：将数据写入文件
            String filePath = StreamManager.playbackUserIDandFilePathMap.get(sessionID);
            if (filePath == null) {
                log.error("下载文件路径未设置，sessionID: {}", sessionID);
                return true;
            }

            if (pDataCBInfo.dwType == 2 && pDataCBInfo.pData != null && pDataCBInfo.dwDataLen > 0) {
                try {
                    File file = new File(filePath);
                    // 确保父目录存在
                    if (file.getParentFile() != null && !file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    
                    try (FileOutputStream fos = new FileOutputStream(file, true)) {
                        byte[] data = pDataCBInfo.pData.getByteArray(0, pDataCBInfo.dwDataLen);
                        fos.write(data);
                    }
                } catch (Exception e) {
                    log.error("写入录像文件失败，sessionID: {}, filePath: {}", sessionID, filePath, e);
                }
            } else if (pDataCBInfo.dwType == 3) {
                log.info("收到回放停止信令（下载模式），句柄: {}, sessionID: {}", iPlayBackLinkHandle, sessionID);
                // 唤醒下载等待的线程
                String downloadKey = StreamManager.sessionIDandDownloadKeyMap.get(sessionID);
                if (downloadKey != null) {
                    CountDownLatch latch = downloadLatchMap.get(downloadKey);
                    if (latch != null) {
                        latch.countDown();
                        log.info("已唤醒下载等待线程，downloadKey: {}", downloadKey);
                    }
                }
            }
            return true;
        }

        // 回放模式：正常的RTP发送
        // 通过 sessionID 获取 rtpServerParam
        RtpServerParam rtpServerParam = StreamManager.luserIdAndPlaybackRtpServerParamMap.get(sessionID);
        if (rtpServerParam == null) {
            return true;
        }

        // 获取或创建该句柄对应的连接
        RtpConnection connection = connectionMap.computeIfAbsent(iPlayBackLinkHandle, handle -> {
            RtpConnection conn = new RtpConnection();
            try {
                // 1. 解析目标地址
                conn.targetAddress = InetAddress.getByName(rtpServerParam.getIp());
                // 2. 创建本地 UDP Socket
                conn.udpSocket = new DatagramSocket();
                // 3. 设置 SSRC
                conn.ssrc = rtpServerParam.getSsrc();
                conn.rtpPort = rtpServerParam.getPort();
                
                // 4. 启动发送线程
                conn.sendThread = new Thread(() -> sendLoop(conn), "RtpSender-" + handle);
                conn.sendThread.start();
                
                log.info("回放句柄: {} ==== RTP Socket创建成功，ip: {}, 端口: {}, ssrc: {}, sessionID: {}", 
                        iPlayBackLinkHandle, rtpServerParam.getIp(), rtpServerParam.getPort(), rtpServerParam.getSsrc(), sessionID);
            } catch (Exception e) {
                log.error("创建RTP Socket失败，句柄: {}, ip: {}, 端口: {}, ssrc: {}, sessionID: {}", 
                        iPlayBackLinkHandle, rtpServerParam.getIp(), rtpServerParam.getPort(), rtpServerParam.getSsrc(), sessionID, e);
                return null;
            }
            return conn;
        });

        if (connection == null || connection.udpSocket == null || connection.targetAddress == null) {
            log.error("RTP连接不可用，句柄: {}", iPlayBackLinkHandle);
            return true;
        }

        // 检查数据类型
        if (pDataCBInfo.dwType == 2) { // 码流数据
            int dwBufSize = pDataCBInfo.dwDataLen;
            Pointer pBuffer = pDataCBInfo.pData;
            
            if (pBuffer != null && dwBufSize > 0) {
                try {
                    byte[] data = pBuffer.getByteArray(0, dwBufSize);
                    processFrameData(connection, data);
                } catch (Exception e) {
                    log.error("[海康ISUP] 处理回调数据异常，句柄: {}", iPlayBackLinkHandle, e);
                }
            }
        } else if (pDataCBInfo.dwType == 3) { // 回放停止信令
            log.info("收到回放停止信令，句柄: {}", iPlayBackLinkHandle);
            close(iPlayBackLinkHandle);
        }

        return true;
    }

    /**
     * 处理帧数据
     */
    private void processFrameData(RtpConnection connection, byte[] data) throws IOException {
        int packHeaderPos = findPackHeader(data, 0);
        
        if (packHeaderPos == -1) {
            connection.currentFrameBuffer.write(data);
            return;
        }
        
        if (packHeaderPos > 0) {
            connection.currentFrameBuffer.write(data, 0, packHeaderPos);
            if (connection.currentFrameBuffer.size() > 0) {
                connection.frameQueue.offer(connection.currentFrameBuffer.toByteArray());
                connection.currentFrameBuffer = new ByteArrayOutputStream();
            }
        }
        
        int nextPos = findPackHeader(data, packHeaderPos + 4);
        while (nextPos != -1) {
            int frameLen = nextPos - packHeaderPos;
            byte[] frameData = new byte[frameLen];
            System.arraycopy(data, packHeaderPos, frameData, 0, frameLen);
            connection.frameQueue.offer(frameData);
            packHeaderPos = nextPos;
            nextPos = findPackHeader(data, packHeaderPos + 4);
        }
        
        connection.currentFrameBuffer.write(data, packHeaderPos, data.length - packHeaderPos);
    }

    /**
     * 查找帧头 0x00 0x00 0x01 0xBA
     */
    private int findPackHeader(byte[] data, int start) {
        for (int i = start; i < data.length - 3; i++) {
            if (data[i] == 0x00 && data[i + 1] == 0x00 && 
                data[i + 2] == 0x01 && data[i + 3] == (byte) 0xBA) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 发送循环
     */
    private void sendLoop(RtpConnection connection) {
        long frameInterval = 1000L / FPS; // 每帧间隔（毫秒）
        
        while (connection.running) {
            try {
                byte[] frameData = connection.frameQueue.poll();
                if (frameData != null && frameData.length > 0) {
                    sendFrame(connection, frameData);
                    connection.timestamp += TIMESTAMP_INCREMENT;
                }
                Thread.sleep(frameInterval);
            } catch (InterruptedException e) {
                break;
            } catch (Exception e) {
                log.error("[海康ISUP] 发送帧数据异常", e);
            }
        }
    }

    /**
     * 发送一帧数据
     */
    private void sendFrame(RtpConnection connection, byte[] frameData) throws IOException {
        int offset = 0;
        
        while (offset < frameData.length) {
            int payloadSize = Math.min(MAX_PAYLOAD_SIZE, frameData.length - offset);
            boolean isLastPacket = (offset + payloadSize >= frameData.length);
            
            // 构建RTP包
            byte[] rtpPacket = buildRtpPacket(connection, ++connection.seqNum, connection.timestamp, isLastPacket, frameData, offset, payloadSize);
            
            // 发送UDP包
            DatagramPacket packet = new DatagramPacket(rtpPacket, rtpPacket.length, connection.targetAddress, connection.rtpPort);
            connection.udpSocket.send(packet);
            
            offset += payloadSize;
        }
    }

    /**
     * 构建RTP包
     */
    private byte[] buildRtpPacket(RtpConnection connection, int seq, int ts, boolean marker, byte[] frameData, int dataOffset, int payloadSize) {
        int rtpLength = 12 + payloadSize;
        byte[] packet = new byte[rtpLength];
        
        // 构建RTP头部
        byte[] rtpHeader = buildRtpHeader(connection, seq, ts, marker);
        System.arraycopy(rtpHeader, 0, packet, 0, 12);
        
        // 复制数据到RTP包
        System.arraycopy(frameData, dataOffset, packet, 12, payloadSize);
        
        return packet;
    }

    /**
     * 构建RTP头部
     */
    private byte[] buildRtpHeader(RtpConnection connection, int seq, int ts, boolean marker) {
        byte markerBit = marker ? (byte) 0x80 : (byte) 0x00;
        byte[] ssrcBytes = intToBytes(Integer.parseInt(connection.ssrc));
        
        return new byte[]{
            (byte) 0x80,
            (byte) (markerBit | 0x60), // 0x60 = 96 (H.264 Payload Type)
            (byte) (seq >> 8),
            (byte) (seq & 0xFF),
            (byte) (ts >> 24),
            (byte) (ts >> 16),
            (byte) (ts >> 8),
            (byte) (ts & 0xFF),
            ssrcBytes[0],
            ssrcBytes[1],
            ssrcBytes[2],
            ssrcBytes[3]
        };
    }

    /**
     * 关闭指定句柄的RTP连接
     *
     * @param iPlayBackLinkHandle 回放句柄
     */
    public void close(int iPlayBackLinkHandle) {
        RtpConnection connection = connectionMap.remove(iPlayBackLinkHandle);
        if (connection != null) {
            // 停止发送线程
            connection.running = false;
            if (connection.sendThread != null) {
                connection.sendThread.interrupt();
                try {
                    connection.sendThread.join(1000);
                } catch (InterruptedException e) {
                    // 忽略
                }
            }
            
            // 关闭Socket
            if (connection.udpSocket != null && !connection.udpSocket.isClosed()) {
                try {
                    connection.udpSocket.close();
                    log.info("[海康ISUP] 关闭RTP连接成功，句柄: {}", iPlayBackLinkHandle);
                } catch (Exception e) {
                    log.error("[海康ISUP] 关闭RTP连接失败，句柄: {}", iPlayBackLinkHandle, e);
                }
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
}