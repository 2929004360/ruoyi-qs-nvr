package com.ruoyi.qs.runner;

import com.ruoyi.qs.service.IQsDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 启动服务
 *
 * @FileName QsCommandLineRunner
 * @Description
 * @Author fengcheng
 * @date 2026-04-08
 **/
@Component
@Slf4j
public class QsCommandLineRunner implements CommandLineRunner, DisposableBean {

    @Autowired
    private IQsDeviceService qsDeviceService;


    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void run(String... args) throws Exception {
        log.info("启动服务==>修改所有设备播状态离线和设备状态离线");
        qsDeviceService.updateAllQsDevicesToOffline();

        qsDeviceService.task();
    }
}
