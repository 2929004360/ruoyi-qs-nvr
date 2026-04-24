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
import org.springframework.stereotype.Component;

/**
 * ONVIF设备状态任务
 *
 * @FileName OnvifTask
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@Component("onvifTask")
public class OnvifTask extends AbstractDeviceStatusTask {

    private final RemoteOnvifService remoteOnvifService;

    public OnvifTask(RemoteQsDeviceService remoteQsDeviceService, RemoteOnvifService remoteOnvifService) {
        super(remoteQsDeviceService);
        this.remoteOnvifService = remoteOnvifService;
    }

    @Override
    protected LiveStreamType getDeviceType() {
        return LiveStreamType.ONVIF;
    }

    @Override
    protected boolean checkDeviceOnline(QsDevice device) {
        WSOnvifDevice onvifDevice = new WSOnvifDevice();
        onvifDevice.setAuth(device.getOnvifAuth());
        onvifDevice.setIp(device.getIpAddress());
        onvifDevice.setHostName(device.getOnvifHostName());
        onvifDevice.setUsername(device.getUserName());
        onvifDevice.setPassword(device.getPassword());
        R<OnvifDevice> login = remoteOnvifService.login(onvifDevice, SecurityConstants.INNER);
        return login.getCode() == Constants.SUCCESS && login.getData() != null;
    }
}
