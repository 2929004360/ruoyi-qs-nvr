package com.ruoyi.job.task;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 设备状态任务
 *
 * @FileName QsDeviceTask
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@Component("qsDeviceTask")
public class QsDeviceTask {

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    public void task() {
        remoteQsDeviceService.task(SecurityConstants.INNER);
    }
}
