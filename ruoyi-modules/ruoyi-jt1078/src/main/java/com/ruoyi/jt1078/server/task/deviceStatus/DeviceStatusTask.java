package com.ruoyi.jt1078.server.task.deviceStatus;

import com.ruoyi.jt1078.commons.callback.DeviceStatusCallback;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Slf4j
@Data
public class DeviceStatusTask implements Delayed {

    private String deviceId;

    /**
     * 超时时间(单位： 毫秒)
     */
    private long delayTime;

    private DeviceStatusCallback callback;

    public static DeviceStatusTask getInstance(String deviceId, long delayTime, DeviceStatusCallback callback) {
        DeviceStatusTask deviceStatusTask = new DeviceStatusTask();
        deviceStatusTask.setDeviceId(deviceId);
        deviceStatusTask.setDelayTime(delayTime);
        deviceStatusTask.setCallback(callback);
        return deviceStatusTask;
    }

    public void expired() {
        if (callback == null) {
            log.info("[设备离线] 未找到过期处理回调， {}", deviceId);
            return;
        }
        callback.run(deviceId);
    }

    @Override
    public long getDelay(@NotNull TimeUnit unit) {
        return unit.convert(delayTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(@NotNull Delayed o) {
        long diff = this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
        if (diff > 0) {
            return 1;
        } else if (diff < 0) {
            return -1;
        }
        return 0;
    }

    public DeviceStatusTaskInfo getInfo() {
        DeviceStatusTaskInfo taskInfo = new DeviceStatusTaskInfo();
        taskInfo.setDeviceId(deviceId);
        return taskInfo;
    }
}
