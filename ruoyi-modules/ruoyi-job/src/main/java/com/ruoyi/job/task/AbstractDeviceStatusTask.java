package com.ruoyi.job.task;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractDeviceStatusTask {

    protected static final Logger log = LoggerFactory.getLogger(AbstractDeviceStatusTask.class);

    protected static final String STATUS_ONLINE = "ON";
    protected static final String STATUS_OFFLINE = "OFFLINE";

    protected final RemoteQsDeviceService remoteQsDeviceService;

    protected AbstractDeviceStatusTask(RemoteQsDeviceService remoteQsDeviceService) {
        this.remoteQsDeviceService = remoteQsDeviceService;
    }

    protected abstract LiveStreamType getDeviceType();

    protected abstract boolean checkDeviceOnline(QsDevice device);

    public void task() {
        try {
            QsDevice qsDevice = new QsDevice();
            qsDevice.setType(getDeviceType().getCode());
            R<List<QsDevice>> r = remoteQsDeviceService.list(qsDevice, SecurityConstants.INNER);
            if (r.getCode() != Constants.SUCCESS) {
                log.error("获取{}设备列表失败: {}", getDeviceType().getDesc(), r.getMsg());
                return;
            }

            List<QsDevice> deviceList = r.getData();
            if (deviceList == null || deviceList.isEmpty()) {
                log.debug("没有{}设备需要检查", getDeviceType().getDesc());
                return;
            }

            Set<Long> onlineDeviceSet = new HashSet<>();
            Set<Long> offlineDeviceSet = new HashSet<>();

            for (QsDevice device : deviceList) {
                try {
                    if (checkDeviceOnline(device)) {
                        onlineDeviceSet.add(device.getId());
                    } else {
                        offlineDeviceSet.add(device.getId());
                    }
                } catch (Exception e) {
                    log.error("检查设备{}状态异常", device.getId(), e);
                    offlineDeviceSet.add(device.getId());
                }
            }

            updateDeviceStatus(onlineDeviceSet, STATUS_ONLINE);
            updateDeviceStatus(offlineDeviceSet, STATUS_OFFLINE);

            log.info("{}设备状态检查完成，在线{}台，离线{}台", getDeviceType().getDesc(), onlineDeviceSet.size(), offlineDeviceSet.size());
        } catch (Exception e) {
            log.error("{}设备状态检查任务异常", getDeviceType().getDesc(), e);
        }
    }

    private void updateDeviceStatus(Set<Long> deviceIds, String status) {
        if (deviceIds.isEmpty()) {
            return;
        }
        try {
            remoteQsDeviceService.updateQsDeviceStatusList(deviceIds, status, SecurityConstants.INNER);
        } catch (Exception e) {
            log.error("批量更新设备状态失败，状态: {}, 设备数量: {}", status, deviceIds.size(), e);
        }
    }
}
