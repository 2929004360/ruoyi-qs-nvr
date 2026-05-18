package com.ruoyi.dahua.api.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 大华设备通道码流信息
 */
public class DahuaBitrateInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 通道码流列表
     */
    private List<ChannelBitrate> channelBitrates;

    /**
     * 是否获取成功
     */
    private Boolean success;

    /**
     * 错误信息
     */
    private String errorMessage;

    public List<ChannelBitrate> getChannelBitrates() { return channelBitrates; }
    public void setChannelBitrates(List<ChannelBitrate> channelBitrates) { this.channelBitrates = channelBitrates; }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    /**
     * 单个通道码流信息
     */
    public static class ChannelBitrate implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 通道号
         */
        private Integer channelId;

        /**
         * 通道名称
         */
        private String channelName;

        /**
         * 码流类型: 0-主码流, 1-辅码流1, 2-辅码流2
         */
        private Integer streamType;

        /**
         * 码流类型描述
         */
        private String streamTypeDesc;

        /**
         * 实时码流(kbps)
         */
        private Integer bitrate;

        /**
         * 是否正在录像
         */
        private Boolean recording;

        public Integer getChannelId() { return channelId; }
        public void setChannelId(Integer channelId) { this.channelId = channelId; }

        public String getChannelName() { return channelName; }
        public void setChannelName(String channelName) { this.channelName = channelName; }

        public Integer getStreamType() { return streamType; }
        public void setStreamType(Integer streamType) { this.streamType = streamType; }

        public String getStreamTypeDesc() { return streamTypeDesc; }
        public void setStreamTypeDesc(String streamTypeDesc) { this.streamTypeDesc = streamTypeDesc; }

        public Integer getBitrate() { return bitrate; }
        public void setBitrate(Integer bitrate) { this.bitrate = bitrate; }

        public Boolean getRecording() { return recording; }
        public void setRecording(Boolean recording) { this.recording = recording; }
    }
}
