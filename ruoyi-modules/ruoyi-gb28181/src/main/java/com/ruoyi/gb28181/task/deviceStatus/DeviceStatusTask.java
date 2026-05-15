package com.ruoyi.gb28181.task.deviceStatus;

import com.ruoyi.gb28181.api.bean.SipTransactionInfo;
import com.ruoyi.gb28181.api.common.DeviceStatusCallback;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Slf4j
@Data
public class DeviceStatusTask implements Delayed {

    private String deviceId;

    private SipTransactionInfo transactionInfo;

    /**
     * 超时时间(单位： 毫秒)
     */
    private long delayTime;

    private DeviceStatusCallback callback;

    public static DeviceStatusTask getInstance(String deviceId, SipTransactionInfo transactionInfo, long delayTime, DeviceStatusCallback callback) {
        DeviceStatusTask deviceStatusTask = new DeviceStatusTask();
        deviceStatusTask.setDeviceId(deviceId);
        deviceStatusTask.setTransactionInfo(transactionInfo);
        deviceStatusTask.setDelayTime(delayTime);
        deviceStatusTask.setCallback(callback);
        return deviceStatusTask;
    }

    public void expired() {
        if (callback == null) {
            log.info("[设备离线] 未找到过期处理回调， {}", deviceId);
            return;
        }
        callback.run(deviceId, transactionInfo);
    }

    @Override
    public long getDelay(@NotNull TimeUnit unit) {
        return unit.convert(delayTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(@NotNull Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceStatusTask that = (DeviceStatusTask) o;
        return Objects.equals(deviceId, that.deviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId);
    }

    public DeviceStatusTaskInfo getInfo() {
        DeviceStatusTaskInfo taskInfo = new DeviceStatusTaskInfo();
        taskInfo.setTransactionInfo(transactionInfo);
        taskInfo.setDeviceId(deviceId);
        return taskInfo;
    }
}
