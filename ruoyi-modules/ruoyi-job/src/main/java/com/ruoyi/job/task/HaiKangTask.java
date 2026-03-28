package com.ruoyi.job.task;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.haikang.api.RemoteHaiKangService;
import com.ruoyi.haikang.api.domain.LoginDevice;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 海康sdk任务
 *
 * @FileName HaiKangTask
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@Component("haiKangTask")
public class HaiKangTask {

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private RemoteHaiKangService remoteHaiKangService;

    public void task() {
        QsDevice qsDevice = new QsDevice();
        qsDevice.setType(LiveStreamType.HIK_SDK.getCode());
        R<List<QsDevice>> r = remoteQsDeviceService.list(qsDevice, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new SecurityException(r.getMsg());
        }

        List<QsDevice> deviceList = r.getData();
        for (QsDevice device : deviceList) {
            R<Integer> userIdr = remoteHaiKangService.getUserId(device.getIpAddress(), SecurityConstants.INNER);
            if (userIdr.getCode() == Constants.SUCCESS && userIdr.getData() == null) {
                LoginDevice loginDevice = new LoginDevice();
                loginDevice.setIpAddress(device.getIpAddress());
                loginDevice.setPort(device.getPort());
                loginDevice.setUserName(device.getUserName());
                loginDevice.setPassword(device.getPassword());
                remoteHaiKangService.loginDevice(loginDevice, SecurityConstants.INNER);
            }
        }
    }
}
