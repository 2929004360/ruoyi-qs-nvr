package com.ruoyi.gb28181.task.platform;

import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import lombok.Data;

/**
 * 平台级联任务信息
 */
@Data
public class PlatformCascadeTaskInfo {
    private Gb28181Platform platform;
    private boolean registered;
    private long lastExecuteTime;
    private long lastHeartbeatTime;
    private long lastRegisterTime;
    private int heartbeatInterval;
    private int registerExpires;
}
