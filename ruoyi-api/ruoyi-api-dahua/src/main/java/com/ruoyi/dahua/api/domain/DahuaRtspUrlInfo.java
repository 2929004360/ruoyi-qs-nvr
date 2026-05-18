package com.ruoyi.dahua.api.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 大华设备RTSP URL信息
 */
public class DahuaRtspUrlInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * RTSP URL信息
     */
    public static class RtspUrl implements Serializable {
        private static final long serialVersionUID = 1L;

        private Integer channelId;
        private String streamType;
        private String url;
        private String description;

        public Integer getChannelId() { return channelId; }
        public void setChannelId(Integer channelId) { this.channelId = channelId; }

        public String getStreamType() { return streamType; }
        public void setStreamType(String streamType) { this.streamType = streamType; }

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    /**
     * URL数量
     */
    private Integer urlCount;

    /**
     * RTSP URL列表
     */
    private List<RtspUrl> urlList = new ArrayList<>();

    /**
     * 是否获取成功
     */
    private Boolean success;

    /**
     * 错误信息
     */
    private String errorMessage;

    public Integer getUrlCount() { return urlCount; }
    public void setUrlCount(Integer urlCount) { this.urlCount = urlCount; }

    public List<RtspUrl> getUrlList() { return urlList; }
    public void setUrlList(List<RtspUrl> urlList) { this.urlList = urlList; }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
