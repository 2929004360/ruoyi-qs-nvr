package com.ruoyi.qs.task;

import com.ruoyi.qs.service.IQsDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 设备状态定时任务调度
 *
 * @FileName QsTask
 * @Description
 * @Author fengcheng
 * @date 2026-04-08
 **/
@Component
public class QsTask {

    @Autowired
    private IQsDeviceService qsDeviceService;

    @Scheduled(cron = "0 * * * * ?")
    public void task() {
        qsDeviceService.task();
    }
}
