package com.ruoyi.job.task;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.dahua.api.RemoteDaHuaService;
import com.ruoyi.dahua.api.domain.DahuaDevice;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import org.springframework.stereotype.Component;

/**
 * 大华设备状态任务
 *
 * @FileName DaHuaTask
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@Component("daHuaTask")
public class DaHuaTask extends AbstractDeviceStatusTask {

    private final RemoteDaHuaService remoteDaHuaService;

    public DaHuaTask(RemoteQsDeviceService remoteQsDeviceService, RemoteDaHuaService remoteDaHuaService) {
        super(remoteQsDeviceService);
        this.remoteDaHuaService = remoteDaHuaService;
    }

    @Override
    protected LiveStreamType getDeviceType() {
        return LiveStreamType.DAHUA_SDK;
    }

    @Override
    protected boolean checkDeviceOnline(QsDevice device) {
        R<Boolean> userIdr = remoteDaHuaService.isUserId(device.getIpAddress(), SecurityConstants.INNER);
        if (userIdr.getCode() != Constants.SUCCESS) {
            return false;
        }

        if (userIdr.getData()) {
            R<String> deviceInfor = remoteDaHuaService.getTime(device.getIpAddress(), SecurityConstants.INNER);
            return deviceInfor.getCode() == Constants.SUCCESS;
        } else {
            com.ruoyi.dahua.api.domain.LoginDevice loginDevice = new com.ruoyi.dahua.api.domain.LoginDevice();

            // 1=主动添加
            if ("1".equals(device.getOnlineType())) {
                loginDevice.setIpAddress(device.getIpAddress());
                loginDevice.setPort(device.getPort());
                loginDevice.setUserName(device.getUserName());
                loginDevice.setPassword(device.getPassword());

                R<Void> loginDevicer = remoteDaHuaService.loginDevice(loginDevice, SecurityConstants.INNER);
                return loginDevicer.getCode() == Constants.SUCCESS;
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
                    return loginDevicer.getCode() == Constants.SUCCESS;
                }
            }
        }
        return false;
    }
}
