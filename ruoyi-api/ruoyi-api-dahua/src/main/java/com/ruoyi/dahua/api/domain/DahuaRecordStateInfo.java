package com.ruoyi.dahua.api.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 大华设备录像状态信息
 */
public class DahuaRecordStateInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 通道录像状态
     */
    public static class ChannelRecordState implements Serializable {
        private static final long serialVersionUID = 1L;

        private Integer channelId;
        private Boolean mainStreamRecording;
        private Boolean extraStream1Recording;
        private Boolean extraStream2Recording;
        private Boolean extraStream3Recording;

        public Integer getChannelId() { return channelId; }
        public void setChannelId(Integer channelId) { this.channelId = channelId; }

        public Boolean getMainStreamRecording() { return mainStreamRecording; }
        public void setMainStreamRecording(Boolean mainStreamRecording) { this.mainStreamRecording = mainStreamRecording; }

        public Boolean getExtraStream1Recording() { return extraStream1Recording; }
        public void setExtraStream1Recording(Boolean extraStream1Recording) { this.extraStream1Recording = extraStream1Recording; }

        public Boolean getExtraStream2Recording() { return extraStream2Recording; }
        public void setExtraStream2Recording(Boolean extraStream2Recording) { this.extraStream2Recording = extraStream2Recording; }

        public Boolean getExtraStream3Recording() { return extraStream3Recording; }
        public void setExtraStream3Recording(Boolean extraStream3Recording) { this.extraStream3Recording = extraStream3Recording; }
    }

    /**
     * 整体录像状态
     */
    private Boolean wholeRecording;

    /**
     * 整体编码状态
     */
    private Boolean wholeEncoding;

    /**
     * 通道录像状态列表
     */
    private List<ChannelRecordState> channelStates = new ArrayList<>();

    /**
     * 是否获取成功
     */
    private Boolean success;

    /**
     * 错误信息
     */
    private String errorMessage;

    public Boolean getWholeRecording() { return wholeRecording; }
    public void setWholeRecording(Boolean wholeRecording) { this.wholeRecording = wholeRecording; }

    public Boolean getWholeEncoding() { return wholeEncoding; }
    public void setWholeEncoding(Boolean wholeEncoding) { this.wholeEncoding = wholeEncoding; }

    public List<ChannelRecordState> getChannelStates() { return channelStates; }
    public void setChannelStates(List<ChannelRecordState> channelStates) { this.channelStates = channelStates; }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
