package com.ruoyi.haikang.isup.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 海康ISUP设备摄像头属性信息
 */
@Data
public class HaiKangIsupCameraInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 摄像头信息
     */
    @Data
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
}
