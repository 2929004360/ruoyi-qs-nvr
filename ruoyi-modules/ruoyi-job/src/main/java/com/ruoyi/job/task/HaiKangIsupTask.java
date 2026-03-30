package com.ruoyi.job.task;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.haikang.isup.api.RemoteHaiKangIsupService;
import com.ruoyi.haikang.isup.api.domain.HaiKangIsupDeviceInfo;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 海康isup任务
 *
 * @FileName HaiKangIsupTask
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
@Component("haiKangIsupTask")
public class HaiKangIsupTask {

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private RemoteHaiKangIsupService remoteHaiKangIsupService;

    public void task() {
        QsDevice qsDevice = new QsDevice();
        qsDevice.setType(LiveStreamType.HIK_ISUP.getCode());
        R<List<QsDevice>> r = remoteQsDeviceService.list(qsDevice, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new SecurityException(r.getMsg());
        }

        List<QsDevice> deviceList = r.getData();

        Set<Long> onlineDeviceSet = new HashSet<>();
        Set<Long> offlineDeviceSet = new HashSet<>();

        for (QsDevice device : deviceList) {
            R<Integer> userIdr = remoteHaiKangIsupService.getUserId(device.getIpAddress(), SecurityConstants.INNER);
            if (userIdr.getCode() == Constants.SUCCESS) {
                if (userIdr.getData() != null) {
                    R<HaiKangIsupDeviceInfo> deviceInfor = remoteHaiKangIsupService.getDevInfo(device.getIpAddress(), SecurityConstants.INNER);
                    if (deviceInfor.getCode() == Constants.SUCCESS) {
                        onlineDeviceSet.add(device.getId());
                    } else {
                        offlineDeviceSet.add(device.getId());
                    }
                } else {
                    offlineDeviceSet.add(device.getId());
                }
            }
        }

        if (onlineDeviceSet.size() > 0) {
            remoteQsDeviceService.updateQsDeviceStatusList(onlineDeviceSet, "ON", SecurityConstants.INNER);
        }

        if (offlineDeviceSet.size() > 0) {
            remoteQsDeviceService.updateQsDeviceStatusList(offlineDeviceSet, "OFFLINE", SecurityConstants.INNER);
        }
    }
}
