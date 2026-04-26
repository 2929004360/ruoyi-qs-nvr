package com.ruoyi.qs.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeviceStats implements Serializable {
    private Integer totalDeviceCount;
    private Integer totalOnlineCount;
    private Integer totalOfflineCount;
    private Integer enableCount;
    private Integer deactivateCount;
    private Integer rtspCount;
    private Integer rtmpCount;
    private Integer flvCount;
    private Integer hlsCount;
    private Integer onvifCount;
    private Integer fileCount;
    private Integer hikSdkCount;
    private Integer hikIsupCount;
    private Integer dahuaSdkCount;
    private Integer gb28181Count;
    private Integer jt1078Count;
    private Integer pushCount;
}