package com.ruoyi.job.task;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.jt1078.api.RemoteJt1078Service;
import com.ruoyi.jt1078.api.domain.Jt1078Device;
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
 * JT1078 设备状态同步任务
 *
 * @author ruoyi
 */
@Slf4j
@Component("jt1078Task")
public class Jt1078DeviceStatusTask {

    @Autowired
    private RemoteJt1078Service remoteJt1078Service;

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    private static final String STATUS_ONLINE = "ON";
    private static final String STATUS_OFFLINE = "OFFLINE";

    /**
     * 执行任务
     */
    public void task() {
        log.info("[JT1078 设备状态同步] 开始执行");
        try {
            // 1. 从 QS 模块获取所有 JT1078 类型的设备
            QsDevice qsDeviceQuery = new QsDevice();
            qsDeviceQuery.setType(LiveStreamType.JT1078.getCode());
            R<List<QsDevice>> qsDeviceListR = remoteQsDeviceService.list(qsDeviceQuery, SecurityConstants.INNER);
            if (qsDeviceListR.getCode() != Constants.SUCCESS) {
                log.error("[JT1078 设备状态同步] 获取 QS 设备列表失败: {}", qsDeviceListR.getMsg());
                return;
            }

            List<QsDevice> qsDeviceList = qsDeviceListR.getData();
            if (qsDeviceList == null || qsDeviceList.isEmpty()) {
                log.debug("[JT1078 设备状态同步] 没有需要同步的设备");
                return;
            }

            // 2. 从 JT1078 模块获取所有在线设备
            R<List<Jt1078Device>> jtDeviceListR = remoteJt1078Service.getAllDevices(SecurityConstants.INNER);
            if (jtDeviceListR.getCode() != Constants.SUCCESS) {
                log.error("[JT1078 设备状态同步] 获取 JT1078 设备列表失败: {}", jtDeviceListR.getMsg());
                return;
            }

            List<Jt1078Device> jtDeviceList = jtDeviceListR.getData();
            Set<String> onlineJtMobileNos = new HashSet<>();
            if (jtDeviceList != null && !jtDeviceList.isEmpty()) {
                onlineJtMobileNos = jtDeviceList.stream()
                        .filter(Jt1078Device::getOnline)
                        .map(Jt1078Device::getMobileNo)
                        .collect(Collectors.toSet());
            }

            // 3. 同步设备状态
            Set<Long> onlineDeviceIds = new HashSet<>();
            Set<Long> offlineDeviceIds = new HashSet<>();

            for (QsDevice qsDevice : qsDeviceList) {
                String jtMobileNo = qsDevice.getJtMobileNo();
                if (jtMobileNo == null || jtMobileNo.trim().isEmpty()) {
                    continue;
                }

                if (onlineJtMobileNos.contains(jtMobileNo)) {
                    onlineDeviceIds.add(qsDevice.getId());
                } else {
                    offlineDeviceIds.add(qsDevice.getId());
                }
            }

            // 4. 批量更新状态
            if (!onlineDeviceIds.isEmpty()) {
                remoteQsDeviceService.updateQsDeviceStatusList(onlineDeviceIds, STATUS_ONLINE, SecurityConstants.INNER);
                log.info("[JT1078 设备状态同步] 在线设备更新成功: {} 台", onlineDeviceIds.size());
            }

            if (!offlineDeviceIds.isEmpty()) {
                remoteQsDeviceService.updateQsDeviceStatusList(offlineDeviceIds, STATUS_OFFLINE, SecurityConstants.INNER);
                log.info("[JT1078 设备状态同步] 离线设备更新成功: {} 台", offlineDeviceIds.size());
            }

            log.info("[JT1078 设备状态同步] 执行完成，在线 {} 台，离线 {} 台", onlineDeviceIds.size(), offlineDeviceIds.size());
        } catch (Exception e) {
            log.error("[JT1078 设备状态同步] 执行异常", e);
        }
    }
}
