package com.ruoyi.job.task;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.onvif.api.RemoteOnvifService;
import com.ruoyi.onvif.api.domain.OnvifDevice;
import com.ruoyi.onvif.api.domain.WSOnvifDevice;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * onvif 任务
 *
 * @FileName onvifTask
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@Component("onvifTask")
public class OnvifTask {

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private RemoteOnvifService remoteOnvifService;

    public void task() {
        QsDevice qsDevice = new QsDevice();
        qsDevice.setType(LiveStreamType.ONVIF.getCode());
        R<List<QsDevice>> r = remoteQsDeviceService.list(qsDevice, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new SecurityException(r.getMsg());
        }

        List<QsDevice> deviceList = r.getData();

        Set<Long> onlineDeviceSet = new HashSet<>();
        Set<Long> offlineDeviceSet = new HashSet<>();

        for (QsDevice device : deviceList) {
            WSOnvifDevice onvifDevice = new WSOnvifDevice();
            onvifDevice.setAuth(device.getOnvifAuth());
            onvifDevice.setIp(device.getIpAddress());
            onvifDevice.setHostName(device.getOnvifHostName());
            onvifDevice.setUsername(device.getUserName());
            onvifDevice.setPassword(device.getPassword());
            R<OnvifDevice> login = remoteOnvifService.login(onvifDevice, SecurityConstants.INNER);
            if (login.getCode() == Constants.SUCCESS && login.getData() != null)  {
                onlineDeviceSet.add(device.getId());
            }else {
                offlineDeviceSet.add(device.getId());
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
