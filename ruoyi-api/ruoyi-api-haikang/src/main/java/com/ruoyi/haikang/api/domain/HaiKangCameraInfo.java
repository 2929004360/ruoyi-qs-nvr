package com.ruoyi.haikang.api.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 海康设备摄像头属性信息
 */
public class HaiKangCameraInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 摄像头信息
     */
    public static class CameraInfo implements Serializable {
        private static final long serialVersionUID = 1L;

        private Integer channelId;
        private String cameraName;
        private String cameraType;
        private Boolean online;
        private Integer status;
        private String statusDesc;
        private String manufacturer;
        private String model;
        private String serialNumber;
        private String firmwareVersion;
        private String ipAddress;
        private Integer port;

        public Integer getChannelId() { return channelId; }
        public void setChannelId(Integer channelId) { this.channelId = channelId; }

        public String getCameraName() { return cameraName; }
        public void setCameraName(String cameraName) { this.cameraName = cameraName; }

        public String getCameraType() { return cameraType; }
        public void setCameraType(String cameraType) { this.cameraType = cameraType; }

        public Boolean getOnline() { return online; }
        public void setOnline(Boolean online) { this.online = online; }

        public Integer getStatus() { return status; }
        public void setStatus(Integer status) { this.status = status; }

        public String getStatusDesc() { return statusDesc; }
        public void setStatusDesc(String statusDesc) { this.statusDesc = statusDesc; }

        public String getManufacturer() { return manufacturer; }
        public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }

        public String getSerialNumber() { return serialNumber; }
        public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

        public String getFirmwareVersion() { return firmwareVersion; }
        public void setFirmwareVersion(String firmwareVersion) { this.firmwareVersion = firmwareVersion; }

        public String getIpAddress() { return ipAddress; }
        public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

        public Integer getPort() { return port; }
        public void setPort(Integer port) { this.port = port; }
    }

    /**
     * 摄像头数量
     */
    private Integer cameraCount;

    /**
     * 摄像头信息列表
     */
    private List<CameraInfo> cameraList = new ArrayList<>();

    /**
     * 是否获取成功
     */
    private Boolean success;

    /**
     * 错误信息
     */
    private String errorMessage;

    public Integer getCameraCount() { return cameraCount; }
    public void setCameraCount(Integer cameraCount) { this.cameraCount = cameraCount; }

    public List<CameraInfo> getCameraList() { return cameraList; }
    public void setCameraList(List<CameraInfo> cameraList) { this.cameraList = cameraList; }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
