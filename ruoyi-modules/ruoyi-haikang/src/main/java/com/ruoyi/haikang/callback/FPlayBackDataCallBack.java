package com.ruoyi.haikang.callback;

import com.ruoyi.haikang.net.HCNetSDK;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 海康设备回放数据回调类
 */
@Slf4j
public class FPlayBackDataCallBack implements HCNetSDK.FPlayDataCallBack {
    private final String host;
    private final String ssrc;
    private final int rtpPort;
    private DatagramSocket udpSocket;
    private InetAddress targetAddress;
    
    // 帧队列相关
    private ByteArrayOutputStream currentFrameBuffer = new ByteArrayOutputStream();
    private LinkedBlockingQueue<byte[]> frameQueue = new LinkedBlockingQueue<>();
    
    // 序列号和时间戳
    private int seqNum = 0;
    private int timestamp = 0;
    
    // 发送线程
    private Thread sendThread;
    private volatile boolean running = true;
    
    // 常量
    private static final int CLOCK_RATE = 90000;
    private static final int FPS = 25;
    private static final int TIMESTAMP_INCREMENT = CLOCK_RATE / FPS; // 3600
    private static final int MAX_PAYLOAD_SIZE = 1400 - 12; // RTP包大小减去头部

    public FPlayBackDataCallBack(String host, Integer rtpPort, String ssrc) {
        this.host = host;
        this.rtpPort = rtpPort;
        this.ssrc = ssrc;
        try {
            this.targetAddress = InetAddress.getByName(host);
            this.udpSocket = new DatagramSocket();
            
            // 启动发送线程
            sendThread = new Thread(this::sendLoop, "RtpSender");
            sendThread.start();
            
            log.info("[海康设备] 初始化回放 RTP 回调成功, host:{}, port:{}, ssrc:{}", host, rtpPort, ssrc);
        } catch (Exception e) {
            log.error("[海康设备] 初始化回放 RTP 回调失败", e);
            close();
        }
    }

    public static byte[] intToBytes(int value) {
        return new byte[]{(byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value};
    }

    public static byte[] shortToBytes(int value) {
        return new byte[]{(byte) (value >>> 8), (byte) value};
    }

    public void close() {
        // 停止发送线程
        running = false;
        if (sendThread != null) {
            sendThread.interrupt();
            try {
                sendThread.join(1000);
            } catch (InterruptedException e) {
                // 忽略
            }
        }
        
        // 关闭Socket
        if (udpSocket != null && !udpSocket.isClosed()) {
            try {
                udpSocket.close();
                log.debug("[海康设备] 回放 UDP Socket 已关闭");
            } catch (Exception e) {
                log.error("[海康设备] 关闭回放 UDP Socket 失败", e);
            }
        }
    }

    @Override
    public void invoke(int lPlayHandle, int dwDataType, Pointer pBuffer, int dwBufSize, int dwUser) {
        // 只处理流数据
        if (dwDataType != HCNetSDK.NET_DVR_STREAMDATA) {
            return;
        }
        
        try {
            byte[] data = pBuffer.getByteArray(0, dwBufSize);
            processFrameData(data);
        } catch (Exception e) {
            log.error("[海康设备] 处理回调数据异常", e);
        }
    }

    /**
     * 处理帧数据
     */
    private void processFrameData(byte[] data) throws IOException {
        int packHeaderPos = findPackHeader(data, 0);
        
        if (packHeaderPos == -1) {
            currentFrameBuffer.write(data);
            return;
        }
        
        if (packHeaderPos > 0) {
            currentFrameBuffer.write(data, 0, packHeaderPos);
            if (currentFrameBuffer.size() > 0) {
                frameQueue.offer(currentFrameBuffer.toByteArray());
                currentFrameBuffer = new ByteArrayOutputStream();
            }
        }
        
        int nextPos = findPackHeader(data, packHeaderPos + 4);
        while (nextPos != -1) {
            int frameLen = nextPos - packHeaderPos;
            byte[] frameData = new byte[frameLen];
            System.arraycopy(data, packHeaderPos, frameData, 0, frameLen);
            frameQueue.offer(frameData);
            packHeaderPos = nextPos;
            nextPos = findPackHeader(data, packHeaderPos + 4);
        }
        
        currentFrameBuffer.write(data, packHeaderPos, data.length - packHeaderPos);
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
    private void sendLoop() {
        long frameInterval = 1000L / FPS; // 每帧间隔（毫秒）
        
        while (running) {
            try {
                byte[] frameData = frameQueue.poll();
                if (frameData != null && frameData.length > 0) {
                    sendFrame(frameData);
                    timestamp += TIMESTAMP_INCREMENT;
                }
                Thread.sleep(frameInterval);
            } catch (InterruptedException e) {
                break;
            } catch (Exception e) {
                log.error("[海康设备] 发送帧数据异常", e);
            }
        }
    }

    /**
     * 发送一帧数据
     */
    private void sendFrame(byte[] frameData) throws IOException {
        int offset = 0;
        
        while (offset < frameData.length) {
            int payloadSize = Math.min(MAX_PAYLOAD_SIZE, frameData.length - offset);
            boolean isLastPacket = (offset + payloadSize >= frameData.length);
            
            // 构建RTP包
            byte[] rtpPacket = buildRtpPacket(++seqNum, timestamp, isLastPacket, frameData, offset, payloadSize);
            
            // 发送UDP包
            DatagramPacket packet = new DatagramPacket(rtpPacket, rtpPacket.length, targetAddress, rtpPort);
            udpSocket.send(packet);
            
            offset += payloadSize;
        }
    }

    /**
     * 构建RTP包
     */
    private byte[] buildRtpPacket(int seq, int ts, boolean marker, byte[] frameData, int dataOffset, int payloadSize) {
        int rtpLength = 12 + payloadSize;
        byte[] packet = new byte[rtpLength];
        
        // 构建RTP头部
        byte[] rtpHeader = buildRtpHeader(seq, ts, marker);
        System.arraycopy(rtpHeader, 0, packet, 0, 12);
        
        // 复制数据到RTP包
        System.arraycopy(frameData, dataOffset, packet, 12, payloadSize);
        
        return packet;
    }

    /**
     * 构建RTP头部
     */
    private byte[] buildRtpHeader(int seq, int ts, boolean marker) {
        byte markerBit = marker ? (byte) 0x80 : (byte) 0x00;
        byte[] ssrcBytes = intToBytes(Integer.parseInt(this.ssrc));
        
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
}
