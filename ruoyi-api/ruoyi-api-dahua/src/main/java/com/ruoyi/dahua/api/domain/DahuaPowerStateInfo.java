package com.ruoyi.dahua.api.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 大华设备电源状态信息
 */
public class DahuaPowerStateInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 电源状态
     */
    public static class PowerState implements Serializable {
        private static final long serialVersionUID = 1L;

        private Integer powerId;
        private String powerName;
        private Integer status;
        private String statusDesc;
        private Boolean online;

        public Integer getPowerId() { return powerId; }
        public void setPowerId(Integer powerId) { this.powerId = powerId; }

        public String getPowerName() { return powerName; }
        public void setPowerName(String powerName) { this.powerName = powerName; }

        public Integer getStatus() { return status; }
        public void setStatus(Integer status) { this.status = status; }

        public String getStatusDesc() { return statusDesc; }
        public void setStatusDesc(String statusDesc) { this.statusDesc = statusDesc; }

        public Boolean getOnline() { return online; }
        public void setOnline(Boolean online) { this.online = online; }
    }

    /**
     * 电源数量
     */
    private Integer powerCount;

    /**
     * 电源状态列表
     */
    private List<PowerState> powerStates = new ArrayList<>();

    /**
     * 是否获取成功
     */
    private Boolean success;

    /**
     * 错误信息
     */
    private String errorMessage;

    public Integer getPowerCount() { return powerCount; }
    public void setPowerCount(Integer powerCount) { this.powerCount = powerCount; }

    public List<PowerState> getPowerStates() { return powerStates; }
    public void setPowerStates(List<PowerState> powerStates) { this.powerStates = powerStates; }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
