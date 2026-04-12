package com.ruoyi.job.task;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.zlm.api.RemoteZlmRecordPlanService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RemoteZlmRecordPlanService remoteZlmRecordPlanService;

    public void task() {
        remoteZlmRecordPlanService.task(SecurityConstants.INNER);
    }
}
