package com.ruoyi.job.task;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.zlm.api.RemoteZlmCloudRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 云端录像文件定时删除
 *
 * @FileName ZlmCloudRecordTask
 * @Description
 * @Author fengcheng
 * @date 2026-04-11
 **/
@Component("cloudRecordTask")
public class CloudRecordTask {

    @Autowired
    private RemoteZlmCloudRecordService remoteZlmCloudRecordService;

    public void task() {
        remoteZlmCloudRecordService.task(SecurityConstants.INNER);
    }
}
