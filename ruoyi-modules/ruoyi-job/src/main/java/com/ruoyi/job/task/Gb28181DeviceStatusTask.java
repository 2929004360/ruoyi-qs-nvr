package com.ruoyi.job.task;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.gb28181.api.RemoteGb28181Service;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * GB28181 设备状态同步任务
 *
 * @author ruoyi
 */
@Slf4j
@Component("gb28181Task")
public class Gb28181DeviceStatusTask {

    @Autowired
    private RemoteGb28181Service remoteGb28181Service;

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    private static final String STATUS_ONLINE = "ON";
    private static final String STATUS_OFFLINE = "OFFLINE";

    /**
     * 执行任务
     */
    public void task() {
        log.info("[GB28181 设备状态同步] 开始执行");
        try {
            // 1. 从 QS 模块获取所有 GB28181 类型的设备
            QsDevice qsDeviceQuery = new QsDevice();
            qsDeviceQuery.setType(LiveStreamType.GB28181.getCode());
            R<List<QsDevice>> qsDeviceListR = remoteQsDeviceService.list(qsDeviceQuery, SecurityConstants.INNER);
            if (qsDeviceListR.getCode() != Constants.SUCCESS) {
                log.error("[GB28181 设备状态同步] 获取 QS 设备列表失败: {}", qsDeviceListR.getMsg());
                return;
            }

            List<QsDevice> qsDeviceList = qsDeviceListR.getData();
            if (qsDeviceList == null || qsDeviceList.isEmpty()) {
                log.debug("[GB28181 设备状态同步] 没有需要同步的设备");
                return;
            }

            // 2. 从 GB28181 模块获取所有在线设备
            R<List<Device>> gbDeviceListR = remoteGb28181Service.getAllDevices(SecurityConstants.INNER);
            if (gbDeviceListR.getCode() != Constants.SUCCESS) {
                log.error("[GB28181 设备状态同步] 获取 GB28181 设备列表失败: {}", gbDeviceListR.getMsg());
                return;
            }

            List<Device> gbDeviceList = gbDeviceListR.getData();
            Set<String> onlineGbDeviceIds = new HashSet<>();
            if (gbDeviceList != null && !gbDeviceList.isEmpty()) {
                onlineGbDeviceIds = gbDeviceList.stream()
                        .filter(Device::isOnLine)
                        .map(Device::getDeviceId)
                        .collect(Collectors.toSet());
            }

            // 3. 同步设备状态
            Set<Long> onlineDeviceIds = new HashSet<>();
            Set<Long> offlineDeviceIds = new HashSet<>();

            for (QsDevice qsDevice : qsDeviceList) {
                String gbDeviceId = qsDevice.getGbDeviceId();
                if (gbDeviceId == null || gbDeviceId.trim().isEmpty()) {
                    continue;
                }

                if (onlineGbDeviceIds.contains(gbDeviceId)) {
                    onlineDeviceIds.add(qsDevice.getId());
                } else {
                    offlineDeviceIds.add(qsDevice.getId());
                }
            }

            // 4. 批量更新状态
            if (!onlineDeviceIds.isEmpty()) {
                remoteQsDeviceService.updateQsDeviceStatusList(onlineDeviceIds, STATUS_ONLINE, SecurityConstants.INNER);
                log.info("[GB28181 设备状态同步] 在线设备更新成功: {} 台", onlineDeviceIds.size());
            }

            if (!offlineDeviceIds.isEmpty()) {
                remoteQsDeviceService.updateQsDeviceStatusList(offlineDeviceIds, STATUS_OFFLINE, SecurityConstants.INNER);
                log.info("[GB28181 设备状态同步] 离线设备更新成功: {} 台", offlineDeviceIds.size());
            }

            log.info("[GB28181 设备状态同步] 执行完成，在线 {} 台，离线 {} 台", onlineDeviceIds.size(), offlineDeviceIds.size());
        } catch (Exception e) {
            log.error("[GB28181 设备状态同步] 执行异常", e);
        }
    }
}
