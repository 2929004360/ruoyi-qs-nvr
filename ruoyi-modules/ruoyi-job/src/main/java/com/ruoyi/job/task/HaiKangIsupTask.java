package com.ruoyi.job.task;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.haikang.isup.api.RemoteHaiKangIsupService;
import com.ruoyi.haikang.isup.api.domain.HaiKangIsupDeviceInfo;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import org.springframework.stereotype.Component;

/**
 * 海康ISUP设备状态任务
 *
 * @FileName HaiKangIsupTask
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
@Component("haiKangIsupTask")
public class HaiKangIsupTask extends AbstractDeviceStatusTask {

    private final RemoteHaiKangIsupService remoteHaiKangIsupService;

    public HaiKangIsupTask(RemoteQsDeviceService remoteQsDeviceService, RemoteHaiKangIsupService remoteHaiKangIsupService) {
        super(remoteQsDeviceService);
        this.remoteHaiKangIsupService = remoteHaiKangIsupService;
    }

    @Override
    protected LiveStreamType getDeviceType() {
        return LiveStreamType.HIK_ISUP;
    }

    @Override
    protected boolean checkDeviceOnline(QsDevice device) {
        R<Integer> userIdr = remoteHaiKangIsupService.getUserId(device.getIpAddress(), SecurityConstants.INNER);
        if (userIdr.getCode() != Constants.SUCCESS || userIdr.getData() == null) {
            return false;
        }

        R<HaiKangIsupDeviceInfo> deviceInfor = remoteHaiKangIsupService.getDevInfo(device.getIpAddress(), SecurityConstants.INNER);
        return deviceInfor.getCode() == Constants.SUCCESS;
    }
}
