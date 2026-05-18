package com.ruoyi.dahua.api.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 大华设备报警布撤防信息
 */
public class DahuaAlarmArmInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 报警通道状态
     */
    public static class AlarmChannelState implements Serializable {
        private static final long serialVersionUID = 1L;

        private Integer channelId;
        private String channelName;
        private Boolean armed;
        private Integer armType;
        private String armTypeDesc;

        public Integer getChannelId() { return channelId; }
        public void setChannelId(Integer channelId) { this.channelId = channelId; }

        public String getChannelName() { return channelName; }
        public void setChannelName(String channelName) { this.channelName = channelName; }

        public Boolean getArmed() { return armed; }
        public void setArmed(Boolean armed) { this.armed = armed; }

        public Integer getArmType() { return armType; }
        public void setArmType(Integer armType) { this.armType = armType; }

        public String getArmTypeDesc() { return armTypeDesc; }
        public void setArmTypeDesc(String armTypeDesc) { this.armTypeDesc = armTypeDesc; }
    }

    /**
     * 报警通道数量
     */
    private Integer channelCount;

    /**
     * 报警通道状态列表
     */
    private List<AlarmChannelState> channelStates = new ArrayList<>();

    /**
     * 是否获取成功
     */
    private Boolean success;

    /**
     * 错误信息
     */
    private String errorMessage;

    public Integer getChannelCount() { return channelCount; }
    public void setChannelCount(Integer channelCount) { this.channelCount = channelCount; }

    public List<AlarmChannelState> getChannelStates() { return channelStates; }
    public void setChannelStates(List<AlarmChannelState> channelStates) { this.channelStates = channelStates; }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
