package com.ruoyi.haikang.isup.runner;

import com.ruoyi.haikang.isup.config.HaikangIsupConfig;
import com.ruoyi.haikang.isup.service.haikang.alarm.AlarmService;
import com.ruoyi.haikang.isup.service.haikang.cms.CmsService;
import com.ruoyi.haikang.isup.service.haikang.ss.SsService;
import com.ruoyi.haikang.isup.service.haikang.stream.StreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 海康isup 启动服务
 *
 * @FileName IsupCommandLineRunner
 * @Description
 * @Author fengcheng
 * @date 2025-12-22
 **/
@Component
@Slf4j
@RequiredArgsConstructor
public class HaikangIsupCommandLineRunner implements CommandLineRunner, DisposableBean {

    private final CmsService cmsService;

    private final SsService ssService;

    private final StreamService streamService;
    
    private final AlarmService alarmService;

    private final HaikangIsupConfig haikangIsupConfig;

    @Override
    public void run(String... args) throws Exception {
        log.info("=========================  开启海康isup服务监听  =========================");
        ssService.eSS_Init();
        ssService.startSsListen();

        streamService.eStream_Init();

        cmsService.cMS_Init();
        cmsService.startCmsListen();
        
        Boolean alarmStorage = haikangIsupConfig.getAlarmServer().getAlarmStorage();
        if (alarmStorage != null && alarmStorage) {
            log.info("海康ISUP报警存储功能已开启，启动报警监听");
            alarmService.eAlarm_Init();
            alarmService.startAlarmListen();
        } else {
            log.info("海康ISUP报警存储功能未开启，跳过报警监听启动");
        }
    }

    @Override
    public void destroy() {
        log.info("=========================  关闭海康ISUP服务监听  =========================");
        try {
            Boolean alarmStorage = haikangIsupConfig.getAlarmServer().getAlarmStorage();
            if (alarmStorage != null && alarmStorage) {
                alarmService.stopAlarmListen();
                if (AlarmService.hcEHomeAlarm != null) {
                    AlarmService.hcEHomeAlarm.NET_EALARM_Fini();
                }
            }
            if (CmsService.hCEhomeCMS != null) {
                CmsService.hCEhomeCMS.NET_ECMS_Fini();
            }
            if (SsService.hCEhomeSS != null) {
                SsService.hCEhomeSS.NET_ESS_Fini();
            }
        } catch (Exception e) {
            log.error("关闭海康ISUP服务失败", e);
        }
    }
}
