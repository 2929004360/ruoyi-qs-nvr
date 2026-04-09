package com.ruoyi.onvif.task;

import com.ruoyi.onvif.service.IOnvifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * onvif 任务
 *
 * @FileName OnvifTask
 * @Description
 * @Author fengcheng
 * @date 2026-04-07
 **/
@Component
public class OnvifTask {

    @Autowired
    private IOnvifService onvifService;

    @Scheduled(cron = "0 */5 * * * ?")
    public void task() {
        onvifService.task();
    }
}
