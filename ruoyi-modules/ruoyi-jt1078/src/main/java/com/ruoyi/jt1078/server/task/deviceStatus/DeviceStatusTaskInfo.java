package com.ruoyi.jt1078.server.task.deviceStatus;

import lombok.Data;

@Data
public class DeviceStatusTaskInfo {

    private String deviceId;

    /**
     * 过期时间,单位毫秒
     */
    private long expireTime;
}
