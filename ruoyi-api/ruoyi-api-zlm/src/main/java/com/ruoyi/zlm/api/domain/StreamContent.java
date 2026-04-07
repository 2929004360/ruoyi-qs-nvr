package com.ruoyi.zlm.api.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 流信息
 */
@Data
public class StreamContent implements Serializable {

    /**应用名**/
    private String app;

    /**流ID**/
    private String stream;

    /**IP**/
    private String ip;

    /**HTTP-FLV流地址**/
    private String flv;

    /**HTTPS-FLV流地址**/
    private String https_flv;

    /**Websocket-FLV流地址**/
    private String ws_flv;

    /**Websockets-FLV流地址**/
    private String wss_flv;

    /**HTTP-FMP4流地址**/
    private String fmp4;

    /**HTTPS-FMP4流地址**/
    private String https_fmp4;

    /**Websocket-FMP4流地址**/
    private String ws_fmp4;

    /**Websockets-FMP4流地址**/
    private String wss_fmp4;

    /**HLS流地址**/
    private String hls;

    /**HTTPS-HLS流地址**/
    private String https_hls;

    /**Websocket-HLS流地址**/
    private String ws_hls;

    /**Websockets-HLS流地址**/
    private String wss_hls;

    /**HTTP-TS流地址**/
    private String ts;

    /**HTTPS-TS流地址**/
    private String https_ts;

    /**Websocket-TS流地址**/
    private String ws_ts;

    /**Websockets-TS流地址**/
    private String wss_ts;

    /**RTMP流地址**/
    private String rtmp;

    /**RTMPS流地址**/
    private String rtmps;

    /**RTSP流地址**/
    private String rtsp;

    /**RTSPS流地址**/
    private String rtsps;

    /**RTC流地址**/
    private String rtc;

    /**RTCS流地址**/
    private String rtcs;

    /**流媒体ID**/
    private String mediaServerId;

    /**流编码信息**/
    private MediaInfo mediaInfo;

    /**开始时间**/
    private String startTime;

    /**结束时间**/
    private String endTime;

    /**时长(回放时使用)**/
    private Double duration;

    /**文件下载地址（录像下载使用）**/
    private DownloadFileInfo downLoadFilePath;

    /**转码后的视频流**/
    private StreamContent transcodeStream;

    private double progress;

    /**拉流代理返回的KEY**/
    private String key;

    /**使用的WVP ID**/
    private String serverId;

    public StreamContent(StreamInfo streamInfo) {
        if (streamInfo == null) {
            return;
        }
        this.app = streamInfo.getApp();
        this.stream = streamInfo.getStream();
        if (streamInfo.getFlv() != null) {
            this.flv = streamInfo.getFlv().getUrl();
        }
        if (streamInfo.getHttps_flv() != null) {
            this.https_flv = streamInfo.getHttps_flv().getUrl();
        }
        if (streamInfo.getWs_flv() != null) {
            this.ws_flv = streamInfo.getWs_flv().getUrl();
        }
        if (streamInfo.getWss_flv() != null) {
            this.wss_flv = streamInfo.getWss_flv().getUrl();
        }
        if (streamInfo.getFmp4() != null) {
            this.fmp4 = streamInfo.getFmp4().getUrl();
        }
        if (streamInfo.getHttps_fmp4() != null) {
            this.https_fmp4 = streamInfo.getHttps_fmp4().getUrl();
        }
        if (streamInfo.getWs_fmp4() != null) {
            this.ws_fmp4 = streamInfo.getWs_fmp4().getUrl();
        }
        if (streamInfo.getWss_fmp4() != null) {
            this.wss_fmp4 = streamInfo.getWss_fmp4().getUrl();
        }
        if (streamInfo.getHls() != null) {
            this.hls = streamInfo.getHls().getUrl();
        }
        if (streamInfo.getHttps_hls() != null) {
            this.https_hls = streamInfo.getHttps_hls().getUrl();
        }
        if (streamInfo.getWs_hls() != null) {
            this.ws_hls = streamInfo.getWs_hls().getUrl();
        }
        if (streamInfo.getWss_hls() != null) {
            this.wss_hls = streamInfo.getWss_hls().getUrl();
        }
        if (streamInfo.getTs() != null) {
            this.ts = streamInfo.getTs().getUrl();
        }
        if (streamInfo.getHttps_ts() != null) {
            this.https_ts = streamInfo.getHttps_ts().getUrl();
        }
        if (streamInfo.getWs_ts() != null) {
            this.ws_ts = streamInfo.getWs_ts().getUrl();
        }
        if (streamInfo.getRtmp() != null) {
            this.rtmp = streamInfo.getRtmp().getUrl();
        }
        if (streamInfo.getRtmps() != null) {
            this.rtmps = streamInfo.getRtmps().getUrl();
        }
        if (streamInfo.getRtsp() != null) {
            this.rtsp = streamInfo.getRtsp().getUrl();
        }
        if (streamInfo.getRtsps() != null) {
            this.rtsps = streamInfo.getRtsps().getUrl();
        }
        if (streamInfo.getRtc() != null) {
            this.rtc = streamInfo.getRtc().getUrl();
        }
        if (streamInfo.getRtcs() != null) {
            this.rtcs = streamInfo.getRtcs().getUrl();
        }
        if (streamInfo.getMediaServer() != null) {
            this.mediaServerId = streamInfo.getMediaServer().getId();
        }

        this.mediaInfo = streamInfo.getMediaInfo();
        this.startTime = streamInfo.getStartTime();
        this.endTime = streamInfo.getEndTime();
        this.progress = streamInfo.getProgress();
        this.duration = streamInfo.getDuration();
        this.key = streamInfo.getKey();
        this.serverId = streamInfo.getServerId();

        if (streamInfo.getDownLoadFilePath() != null) {
            this.downLoadFilePath = streamInfo.getDownLoadFilePath();
        }
        if (streamInfo.getTranscodeStream() != null) {
            this.transcodeStream = new StreamContent(streamInfo.getTranscodeStream());
        }
    }

}
