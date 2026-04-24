package com.ruoyi.dahua.callback;

import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.NetSDKLib.fRealDataCallBackEx;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 大华设备实时流处理类
 *
 * @author fengcheng
 */
@Slf4j
public class FRealDatarTPCallback implements fRealDataCallBackEx {
    private final String host;

    private String ssrc;
    private final int rtpPort;

    private DatagramSocket udpSocket;
    private InetAddress targetAddress;

    private int seqNum = 0;
    private int currentTimestamp = 0;
    private static final int CLOCK_RATE = 90000;
    private static final int FPS = 25;
    private static final int TIMESTAMP_INCREMENT = CLOCK_RATE / FPS;

    public FRealDatarTPCallback(String host, Integer rtpPort, String ssrc) {
        this.host = host;
        this.rtpPort = rtpPort;
        try {
            this.targetAddress = InetAddress.getByName(host);
            this.udpSocket = new DatagramSocket();
            this.ssrc = ssrc;
        } catch (Exception e) {
            log.error("[大华设备] 初始化 RTP 回调失败", e);
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
                log.debug("[大华设备] UDP Socket 已关闭");
            } catch (Exception e) {
                log.error("[大华设备] 关闭 UDP Socket 失败", e);
            }
        }
    }

    @Override
    public void invoke(LLong lRealHandle, int dwDataType, Pointer pBuffer, int dwBufSize, int param, Pointer dwUser) {
        if (udpSocket == null || targetAddress == null || udpSocket.isClosed()) {
            return;
        }

        try {
            if (dwDataType == 1001 || dwDataType == 3) {
                int frameTimestamp = currentTimestamp;
                currentTimestamp += TIMESTAMP_INCREMENT;

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
            }
        } catch (IOException e) {
            log.error("[大华设备] 发送 RTP 数据包失败", e);
        } catch (Exception e) {
            log.error("[大华设备] 处理实时流数据异常", e);
        }
    }
}
