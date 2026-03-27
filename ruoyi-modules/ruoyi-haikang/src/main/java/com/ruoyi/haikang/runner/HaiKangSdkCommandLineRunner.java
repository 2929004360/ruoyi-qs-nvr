package com.ruoyi.haikang.runner;

import com.ruoyi.haikang.net.Client;
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
public class HaiKangSdkCommandLineRunner implements CommandLineRunner, DisposableBean {

    @Autowired
    private Client client;

    @Override
    public void run(String... args) throws Exception {
        log.info("=========================  开启海康sdk服务  =========================");
        client.start();

    }

    @Override
    public void destroy() throws Exception {
        log.info("=========================  停止海康sdk服务  =========================");

        client.hCNetSDK.NET_DVR_Cleanup();
    }
}
