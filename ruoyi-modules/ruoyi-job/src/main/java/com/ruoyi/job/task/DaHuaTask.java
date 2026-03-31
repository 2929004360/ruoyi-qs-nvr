package com.ruoyi.job.task;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.dahua.api.RemoteDaHuaService;
import com.ruoyi.dahua.api.domain.DahuaDevice;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 海康sdk任务
 *
 * @FileName HaiKangTask
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@Component("daHuaTask")
public class DaHuaTask {

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private RemoteDaHuaService remoteDaHuaService;

    public void task() {
        QsDevice qsDevice = new QsDevice();
        qsDevice.setType(LiveStreamType.DAHUA_SDK.getCode());
        R<List<QsDevice>> r = remoteQsDeviceService.list(qsDevice, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new SecurityException(r.getMsg());
        }

        List<QsDevice> deviceList = r.getData();

        Set<Long> onlineDeviceSet = new HashSet<>();
        Set<Long> offlineDeviceSet = new HashSet<>();

        for (QsDevice device : deviceList) {
            R<Boolean> userIdr = remoteDaHuaService.isUserId(device.getIpAddress(), SecurityConstants.INNER);
            if (userIdr.getCode() == Constants.SUCCESS) {
                if (userIdr.getData()) {
                    R<String> deviceInfor = remoteDaHuaService.getTime(device.getIpAddress(), SecurityConstants.INNER);
                    if (deviceInfor.getCode() == Constants.SUCCESS) {
                        onlineDeviceSet.add(device.getId());
                    } else {
                        offlineDeviceSet.add(device.getId());
                    }
                } else {
                    com.ruoyi.dahua.api.domain.LoginDevice loginDevice = new com.ruoyi.dahua.api.domain.LoginDevice();

                    // 1=主动添加
                    if ("1".equals(device.getOnlineType())) {
                        loginDevice.setIpAddress(device.getIpAddress());
                        loginDevice.setPort(device.getPort());
                        loginDevice.setUserName(device.getUserName());
                        loginDevice.setPassword(device.getPassword());

                        R<Void> loginDevicer = remoteDaHuaService.loginDevice(loginDevice, SecurityConstants.INNER);
                        if (loginDevicer.getCode() == Constants.SUCCESS) {
                            onlineDeviceSet.add(device.getId());
                        } else {
                            offlineDeviceSet.add(device.getId());
                        }
                    }

                    // 2=主动注册
                    if ("2".equals(device.getOnlineType())) {
                        R<DahuaDevice> dahuaDevicer = remoteDaHuaService.getDahuaDevice(device.getIpAddress(), SecurityConstants.INNER);

                        if (dahuaDevicer.getCode() == Constants.SUCCESS) {
                            loginDevice.setIpAddress(device.getIpAddress());
                            loginDevice.setPort(Integer.valueOf(dahuaDevicer.getData().getPort()));
                            loginDevice.setDeviceId(dahuaDevicer.getData().getDeviceId());
                            loginDevice.setUserName(device.getUserName());
                            loginDevice.setPassword(device.getPassword());
                            loginDevice.setOnlineType(device.getOnlineType());
                            R<Void> loginDevicer = remoteDaHuaService.loginDevice(loginDevice, SecurityConstants.INNER);
                            if (loginDevicer.getCode() == Constants.SUCCESS) {
                                onlineDeviceSet.add(device.getId());
                            } else {
                                offlineDeviceSet.add(device.getId());
                            }
                        } else {
                            offlineDeviceSet.add(device.getId());
                        }


                    }
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
