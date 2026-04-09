package com.ruoyi.onvif.runner;

import com.ruoyi.onvif.service.IOnvifService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 启动海康sdk服务
 *
 * @FileName HaikangSdkCommandLineRunner
 * @Description
 * @Author fengcheng
 * @date 2025-12-02
 **/
@Component
@Slf4j
public class OnvifCommandLineRunner implements CommandLineRunner, DisposableBean {

    @Autowired
    private IOnvifService onvifService;

    @Override
    public void run(String... args) {
        onvifService.task();
    }

    @Override
    public void destroy() {

    }
}
