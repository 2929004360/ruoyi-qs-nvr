package com.ruoyi.dahua.callback;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 大华设备回放数据回调类
 */
@Slf4j
public class FPlayBackDataCallBack implements NetSDKLib.fDataCallBack {
    private final String host;
    private final String ssrc;
    private final int rtpPort;
    private DatagramSocket udpSocket;
    private InetAddress targetAddress;
    private int seqNum = 0;
    private long firstFrameTime = -1;
    private int startTimestamp = 0;
    private static final int CLOCK_RATE = 90000;

    public FPlayBackDataCallBack(String host, Integer rtpPort, String ssrc) {
        this.host = host;
        this.rtpPort = rtpPort;
        this.ssrc = ssrc;
        try {
            this.targetAddress = InetAddress.getByName(host);
            this.udpSocket = new DatagramSocket();
            log.info("[大华设备] 初始化回放 RTP 回调成功, host:{}, port:{}, ssrc:{}", host, rtpPort, ssrc);
        } catch (Exception e) {
            log.error("[大华设备] 初始化回放 RTP 回调失败", e);
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
        if (udpSocket != null && !udpSocket.isClosed()) {
            try {
                udpSocket.close();
                log.debug("[大华设备] 回放 UDP Socket 已关闭");
            } catch (Exception e) {
                log.error("[大华设备] 关闭回放 UDP Socket 失败", e);
            }
        }
    }

    @Override
    public int invoke(NetSDKLib.LLong lRealHandle, int dwDataType, Pointer pBuffer, int dwBufSize, Pointer dwUser) {
        if (udpSocket == null || targetAddress == null || udpSocket.isClosed()) {
            return 0;
        }

        try {
            if (firstFrameTime == -1) {
                log.info("[大华设备] 收到第一帧回放数据, dataType:{}, bufSize:{}", dwDataType, dwBufSize);
                firstFrameTime = System.currentTimeMillis();
            }

            long currentTime = System.currentTimeMillis();
            long elapsedMs = currentTime - firstFrameTime;
            int frameTimestamp = startTimestamp + (int) (elapsedMs * CLOCK_RATE / 1000L);

            byte pt = 96;

            ByteBuffer buffer = ByteBuffer.allocate(4);
            buffer.order(ByteOrder.BIG_ENDIAN);
            buffer.putInt(Integer.parseInt(this.ssrc));
            byte[] ssrcBytes = buffer.array();
            byte[] tsBytes = intToBytes(frameTimestamp);

            int maxPayloadSize = 1400 - 12;
            byte[] rtpPacket = new byte[1400];

            rtpPacket[0] = (byte) 0x80;
            rtpPacket[1] = (byte) (pt & 0x7F);

            int offset = 0;
            int dataSize = dwBufSize;

            while (dataSize > 0) {
                int chunkSize = Math.min(maxPayloadSize, dataSize);
                boolean isLastFragment = (dataSize <= maxPayloadSize);

                seqNum++;
                byte[] seqBytes = shortToBytes(seqNum);
                rtpPacket[2] = seqBytes[0];
                rtpPacket[3] = seqBytes[1];

                System.arraycopy(tsBytes, 0, rtpPacket, 4, 4);
                System.arraycopy(ssrcBytes, 0, rtpPacket, 8, 4);

                if (isLastFragment) {
                    rtpPacket[1] = (byte) (rtpPacket[1] | 0x80);
                } else {
                    rtpPacket[1] = (byte) (rtpPacket[1] & 0x7F);
                }

                pBuffer.read(offset, rtpPacket, 12, chunkSize);

                int packetLength = 12 + chunkSize;
                DatagramPacket packet = new DatagramPacket(rtpPacket, packetLength, targetAddress, rtpPort);
                udpSocket.send(packet);

                offset += chunkSize;
                dataSize -= chunkSize;
            }

        } catch (IOException e) {
            log.error("[大华设备] 发送回放 RTP 数据包失败", e);
        } catch (Exception e) {
            log.error("[大华设备] 处理回放数据异常", e);
        }

        return 0;
    }
}
