package com.ruoyi.zlm.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 流信息
 */
@Data
public class StreamInfo implements Serializable, Cloneable {

    /**应用名**/
    private String app;
    /**流ID**/
    private String stream;
    /**设备编号**/
    private String deviceId;
    /**通道ID**/
    private Integer channelId;

    /**IP**/
    private String ip;

    /**HTTP-FLV流地址**/
    private StreamURL flv;

    /**HTTPS-FLV流地址**/
    private StreamURL https_flv;
    /**Websocket-FLV流地址**/
    private StreamURL ws_flv;
    /**Websockets-FLV流地址**/
    private StreamURL wss_flv;
    /**HTTP-FMP4流地址**/
    private StreamURL fmp4;
    /**HTTPS-FMP4流地址**/
    private StreamURL https_fmp4;
    /**Websocket-FMP4流地址**/
    private StreamURL ws_fmp4;
    /**Websockets-FMP4流地址**/
    private StreamURL wss_fmp4;
    /**HLS流地址**/
    private StreamURL hls;
    /**HTTPS-HLS流地址**/
    private StreamURL https_hls;
    /**Websocket-HLS流地址**/
    private StreamURL ws_hls;
    /**Websockets-HLS流地址**/
    private StreamURL wss_hls;
    /**HTTP-TS流地址**/
    private StreamURL ts;
    /**HTTPS-TS流地址**/
    private StreamURL https_ts;
    /**Websocket-TS流地址**/
    private StreamURL ws_ts;
    /**Websockets-TS流地址**/
    private StreamURL wss_ts;
    /**RTMP流地址**/
    private StreamURL rtmp;
    /**RTMPS流地址**/
    private StreamURL rtmps;
    /**RTSP流地址**/
    private StreamURL rtsp;
    /**RTSPS流地址**/
    private StreamURL rtsps;
    /**RTC流地址**/
    private StreamURL rtc;

    /**RTCS流地址**/
    private StreamURL rtcs;
    /**流媒体节点**/
    private ZlmMediaServer mediaServer;
    /**流编码信息**/
    private MediaInfo mediaInfo;
    /**开始时间**/
    private String startTime;
    /**结束时间**/
    private String endTime;
    /**时长(回放时使用)**/
    private Double duration;
    /**进度（录像下载使用）**/
    private double progress;
    /**文件下载地址（录像下载使用）**/
    private DownloadFileInfo downLoadFilePath;
    /**点播请求的callId**/
    private String callId;

    /**是否暂停（录像回放使用）**/
    private boolean pause;

    /**产生源类型，包括 unknown = 0,rtmp_push=1,rtsp_push=2,rtp_push=3,pull=4,ffmpeg_pull=5,mp4_vod=6,device_chn=7**/
    private int originType;

    /**originType的文本描述**/
    private String originTypeStr;

    /**转码后的视频流**/
    private StreamInfo transcodeStream;

    /**使用的WVP ID**/
    private String serverId;

    /**流绑定的流媒体操作key**/
    private String key;

    public void setRtmp(String host, Integer port, Integer sslPort, String app, String stream, String callIdParam) {
        String file = String.format("%s/%s%s", app, stream, callIdParam);
        if (port != null && port > 0) {
            this.rtmp = new StreamURL("rtmp", host, port, file);
        }
        if (sslPort != null && sslPort > 0) {
            this.rtmps = new StreamURL("rtmps", host, sslPort, file);
        }
    }

    public void setRtsp(String host, Integer port, Integer sslPort, String app, String stream, String callIdParam) {
        String file = String.format("%s/%s%s", app, stream, callIdParam);
        if (port != null && port > 0) {
            this.rtsp = new StreamURL("rtsp", host, port, file);
        }
        if (sslPort != null && sslPort > 0) {
            this.rtsps = new StreamURL("rtsps", host, sslPort, file);
        }
    }

    public void setFlv(String host, Integer port, Integer sslPort, String file) {
        if (port != null && port > 0) {
            this.flv = new StreamURL("http", host, port, file);
        }
        if (sslPort != null && sslPort > 0) {
            this.https_flv = new StreamURL("https", host, sslPort, file);
        }
    }

    public void setWsFlv(String host, Integer port, Integer sslPort, String file) {
        if (port != null && port > 0) {
            this.ws_flv = new StreamURL("ws", host, port, file);
        }
        if (sslPort != null && sslPort > 0) {
            this.wss_flv = new StreamURL("wss", host, sslPort, file);
        }
    }

    public void setFmp4(String host, Integer port, Integer sslPort, String file) {
        if (port != null && port > 0) {
            this.fmp4 = new StreamURL("http", host, port, file);
        }
        if (sslPort != null && sslPort > 0) {
            this.https_fmp4 = new StreamURL("https", host, sslPort, file);
        }
    }

    public void setWsMp4(String host, Integer port, Integer sslPort, String file) {
        if (port != null && port > 0) {
            this.ws_fmp4 = new StreamURL("ws", host, port, file);
        }
        if (sslPort != null && sslPort > 0) {
            this.wss_fmp4 = new StreamURL("wss", host, sslPort, file);
        }
    }

    public void setHls(String host, Integer port, Integer sslPort, String app, String stream, String callIdParam) {
        String file = String.format("%s/%s/hls.m3u8%s", app, stream, callIdParam);
        if (port != null && port > 0) {
            this.hls = new StreamURL("http", host, port, file);
        }
        if (sslPort != null && sslPort > 0) {
            this.https_hls = new StreamURL("https", host, sslPort, file);
        }
    }

    public void setWsHls(String host, Integer port, Integer sslPort, String app, String stream, String callIdParam) {
        String file = String.format("%s/%s/hls.m3u8%s", app, stream, callIdParam);
        if (port != null && port > 0) {
            this.ws_hls = new StreamURL("ws", host, port, file);
        }
        if (sslPort != null && sslPort > 0) {
            this.wss_hls = new StreamURL("wss", host, sslPort, file);
        }
    }

    public void setTs(String host, Integer port, Integer sslPort, String app, String stream, String callIdParam) {
        String file = String.format("%s/%s.live.ts%s", app, stream, callIdParam);

        if (port != null && port > 0) {
            this.ts = new StreamURL("http", host, port, file);
        }
        if (sslPort != null && sslPort > 0) {
            this.https_ts = new StreamURL("https", host, sslPort, file);
        }
    }

    public void setWsTs(String host, Integer port, Integer sslPort, String app, String stream, String callIdParam) {
        String file = String.format("%s/%s.live.ts%s", app, stream, callIdParam);

        if (port != null && port > 0) {
            this.ws_ts = new StreamURL("ws", host, port, file);
        }
        if (sslPort != null && sslPort > 0) {
            this.wss_ts = new StreamURL("wss", host, sslPort, file);
        }
    }

    public void setRtc(String host, Integer port, Integer sslPort, String app, String stream, String callIdParam, boolean isPlay) {
        if (callIdParam != null) {
            callIdParam = Objects.equals(callIdParam, "") ? callIdParam : callIdParam.replace("?", "&");
        }
//        String file = String.format("%s/%s?type=%s%s", app, stream, isPlay?"play":"push", callIdParam);
        String file = String.format("index/api/webrtc?app=%s&stream=%s&type=%s%s", app, stream, isPlay ? "play" : "push", callIdParam);
        if (port > 0) {
            this.rtc = new StreamURL("http", host, port, file);
        }
        if (sslPort > 0) {
            this.rtcs = new StreamURL("https", host, sslPort, file);
        }
    }

    public void changeStreamIp(String localAddr) {
        if (this.flv != null) {
            this.flv.setHost(localAddr);
        }
        if (this.ws_flv != null) {
            this.ws_flv.setHost(localAddr);
        }
        if (this.hls != null) {
            this.hls.setHost(localAddr);
        }
        if (this.ws_hls != null) {
            this.ws_hls.setHost(localAddr);
        }
        if (this.ts != null) {
            this.ts.setHost(localAddr);
        }
        if (this.ws_ts != null) {
            this.ws_ts.setHost(localAddr);
        }
        if (this.fmp4 != null) {
            this.fmp4.setHost(localAddr);
        }
        if (this.ws_fmp4 != null) {
            this.ws_fmp4.setHost(localAddr);
        }
        if (this.rtc != null) {
            this.rtc.setHost(localAddr);
        }
        if (this.https_flv != null) {
            this.https_flv.setHost(localAddr);
        }
        if (this.wss_flv != null) {
            this.wss_flv.setHost(localAddr);
        }
        if (this.https_hls != null) {
            this.https_hls.setHost(localAddr);
        }
        if (this.wss_hls != null) {
            this.wss_hls.setHost(localAddr);
        }
        if (this.wss_ts != null) {
            this.wss_ts.setHost(localAddr);
        }
        if (this.https_fmp4 != null) {
            this.https_fmp4.setHost(localAddr);
        }
        if (this.wss_fmp4 != null) {
            this.wss_fmp4.setHost(localAddr);
        }
        if (this.rtcs != null) {
            this.rtcs.setHost(localAddr);
        }
        if (this.rtsp != null) {
            this.rtsp.setHost(localAddr);
        }
        if (this.rtsps != null) {
            this.rtsps.setHost(localAddr);
        }
        if (this.rtmp != null) {
            this.rtmp.setHost(localAddr);
        }
        if (this.rtmps != null) {
            this.rtmps.setHost(localAddr);
        }
    }


    public static class TransactionInfo {
        public String callId;
        public String localTag;
        public String remoteTag;
        public String branch;
    }

    private TransactionInfo transactionInfo;


    @Override
    public StreamInfo clone() {
        StreamInfo instance = null;
        try {
            instance = (StreamInfo) super.clone();
            if (this.flv != null) {
                instance.flv = this.flv.clone();
            }
            if (this.ws_flv != null) {
                instance.ws_flv = this.ws_flv.clone();
            }
            if (this.hls != null) {
                instance.hls = this.hls.clone();
            }
            if (this.ws_hls != null) {
                instance.ws_hls = this.ws_hls.clone();
            }
            if (this.ts != null) {
                instance.ts = this.ts.clone();
            }
            if (this.ws_ts != null) {
                instance.ws_ts = this.ws_ts.clone();
            }
            if (this.fmp4 != null) {
                instance.fmp4 = this.fmp4.clone();
            }
            if (this.ws_fmp4 != null) {
                instance.ws_fmp4 = this.ws_fmp4.clone();
            }
            if (this.rtc != null) {
                instance.rtc = this.rtc.clone();
            }
            if (this.https_flv != null) {
                instance.https_flv = this.https_flv.clone();
            }
            if (this.wss_flv != null) {
                instance.wss_flv = this.wss_flv.clone();
            }
            if (this.https_hls != null) {
                instance.https_hls = this.https_hls.clone();
            }
            if (this.wss_hls != null) {
                instance.wss_hls = this.wss_hls.clone();
            }
            if (this.wss_ts != null) {
                instance.wss_ts = this.wss_ts.clone();
            }
            if (this.https_fmp4 != null) {
                instance.https_fmp4 = this.https_fmp4.clone();
            }
            if (this.wss_fmp4 != null) {
                instance.wss_fmp4 = this.wss_fmp4.clone();
            }
            if (this.rtcs != null) {
                instance.rtcs = this.rtcs.clone();
            }
            if (this.rtsp != null) {
                instance.rtsp = this.rtsp.clone();
            }
            if (this.rtsps != null) {
                instance.rtsps = this.rtsps.clone();
            }
            if (this.rtmp != null) {
                instance.rtmp = this.rtmp.clone();
            }
            if (this.rtmps != null) {
                instance.rtmps = this.rtmps.clone();
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return instance;
    }

}
