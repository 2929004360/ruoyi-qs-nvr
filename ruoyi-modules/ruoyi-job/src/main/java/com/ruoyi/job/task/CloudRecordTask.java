package com.ruoyi.job.task;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.zlm.api.RemoteZlmCloudRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 云端录像文件定时删除
 *
 * @FileName CloudRecordTask
 * @Description
 * @Author fengcheng
 * @date 2026-04-11
 **/
@Component("cloudRecordTask")
public class CloudRecordTask {

    private static final Logger log = LoggerFactory.getLogger(CloudRecordTask.class);

    private final RemoteZlmCloudRecordService remoteZlmCloudRecordService;

    public CloudRecordTask(RemoteZlmCloudRecordService remoteZlmCloudRecordService) {
        this.remoteZlmCloudRecordService = remoteZlmCloudRecordService;
    }

    public void task() {
        try {
            log.info("开始执行云端录像文件清理任务");
            remoteZlmCloudRecordService.task(SecurityConstants.INNER);
            log.info("云端录像文件清理任务执行完成");
        } catch (Exception e) {
            log.error("云端录像文件清理任务执行异常", e);
        }
    }
}
