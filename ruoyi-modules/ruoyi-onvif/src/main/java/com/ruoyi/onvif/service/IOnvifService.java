package com.ruoyi.onvif.service;

import com.ruoyi.onvif.domain.OnvifDevice;
import com.ruoyi.onvif.domain.WSDiscoveryDevice;
import com.ruoyi.onvif.domain.WSOnvifDevice;

import java.util.ArrayList;

/**
 * @FileName IOnvifService
 * @Description
 * @Author fengcheng
 * @date 2026-04-09
 **/
public interface IOnvifService {

    /**
     * 定时任务获取内网onvif设备
     */
    void task();

    /**
     * 验证登录onvif设备
     *
     * @param onvifDevice
     */
    OnvifDevice verifyOnvifDeviceLogin(WSOnvifDevice onvifDevice);

    /**
     * 获取onvif设备列表
     */
    ArrayList<WSDiscoveryDevice> getOnvifDeviceList();
}
