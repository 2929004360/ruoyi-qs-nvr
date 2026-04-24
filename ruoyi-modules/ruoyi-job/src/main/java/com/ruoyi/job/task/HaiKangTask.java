package com.ruoyi.job.task;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.haikang.api.RemoteHaiKangService;
import com.ruoyi.haikang.api.domain.HaikangDeviceInfo;
import com.ruoyi.haikang.api.domain.LoginDevice;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import org.springframework.stereotype.Component;

/**
 * 海康SDK设备状态任务
 *
 * @FileName HaiKangTask
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@Component("haiKangTask")
public class HaiKangTask extends AbstractDeviceStatusTask {

    private final RemoteHaiKangService remoteHaiKangService;

    public HaiKangTask(RemoteQsDeviceService remoteQsDeviceService, RemoteHaiKangService remoteHaiKangService) {
        super(remoteQsDeviceService);
        this.remoteHaiKangService = remoteHaiKangService;
    }

    @Override
    protected LiveStreamType getDeviceType() {
        return LiveStreamType.HIK_SDK;
    }

    @Override
    protected boolean checkDeviceOnline(QsDevice device) {
        R<Integer> userIdr = remoteHaiKangService.getUserId(device.getIpAddress(), SecurityConstants.INNER);
        if (userIdr.getCode() != Constants.SUCCESS) {
            return false;
        }

        if (userIdr.getData() != null) {
            R<HaikangDeviceInfo> deviceInfor = remoteHaiKangService.getDeviceInfo(device.getIpAddress(), SecurityConstants.INNER);
            return deviceInfor.getCode() == Constants.SUCCESS;
        } else {
            LoginDevice loginDevice = new LoginDevice();
            loginDevice.setIpAddress(device.getIpAddress());
            loginDevice.setPort(Short.parseShort(String.valueOf(device.getPort())));
            loginDevice.setUserName(device.getUserName());
            loginDevice.setPassword(device.getPassword());
            R<Integer> loginDeviceR = remoteHaiKangService.loginDevice(loginDevice, SecurityConstants.INNER);
            return loginDeviceR.getCode() == Constants.SUCCESS;
        }
    }
}
