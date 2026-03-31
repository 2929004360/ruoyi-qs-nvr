package com.ruoyi.haikang.runner;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.haikang.net.Client;
import com.ruoyi.haikang.service.IHaiKangService;
import com.ruoyi.haikang.service.impl.HaiKangServiceImpl;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private IHaiKangService haiKangService;

    @Override
    public void run(String... args) {
        log.info("=========================  开启海康sdk服务  =========================");
        client.start();

        QsDevice qsDevice = new QsDevice();
        qsDevice.setType(LiveStreamType.HIK_SDK.getCode());
        R<List<QsDevice>> r = remoteQsDeviceService.list(qsDevice, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new SecurityException(r.getMsg());
        }

        List<QsDevice> deviceList = r.getData();
        for (QsDevice device : deviceList) {
            Integer userId = haiKangService.getUserId(device.getIpAddress());
            if (userId == null) {
                haiKangService.loginDevice(device.getIpAddress(),
                        Short.valueOf(String.valueOf(device.getPort())),
                        device.getUserName(),
                        device.getPassword());
            }
        }
    }

    @Override
    public void destroy() {
        log.info("=========================  停止海康sdk服务  =========================");

        HaiKangServiceImpl.userIdMap.forEach((k, v) -> {
            haiKangService.logoutDevice(k);
        });
        client.hCNetSDK.NET_DVR_Cleanup();
    }
}
