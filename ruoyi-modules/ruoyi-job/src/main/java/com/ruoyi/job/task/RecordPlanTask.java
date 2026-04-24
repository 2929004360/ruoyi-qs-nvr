package com.ruoyi.job.task;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.zlm.api.RemoteZlmRecordPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 录像计划任务
 *
 * @FileName RecordPlanTask
 * @Description
 * @Author fengcheng
 * @date 2026-04-12
 **/
@Component("recordPlanTask")
public class RecordPlanTask {

    private static final Logger log = LoggerFactory.getLogger(RecordPlanTask.class);

    private final RemoteZlmRecordPlanService remoteZlmRecordPlanService;

    public RecordPlanTask(RemoteZlmRecordPlanService remoteZlmRecordPlanService) {
        this.remoteZlmRecordPlanService = remoteZlmRecordPlanService;
    }

    public void task() {
        try {
            log.info("开始执行录像计划任务");
            remoteZlmRecordPlanService.task(SecurityConstants.INNER);
            log.info("录像计划任务执行完成");
        } catch (Exception e) {
            log.error("录像计划任务执行异常", e);
        }
    }
}
