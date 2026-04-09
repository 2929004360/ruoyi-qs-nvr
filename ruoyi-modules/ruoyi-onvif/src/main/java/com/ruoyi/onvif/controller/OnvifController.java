package com.ruoyi.onvif.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.onvif.domain.OnvifDevice;
import com.ruoyi.onvif.domain.WSOnvifDevice;
import com.ruoyi.onvif.service.IOnvifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * onvif 设备
 *
 * @FileName OnvifController
 * @Description
 * @Author fengcheng
 * @date 2026-04-09
 **/
@RestController
@RequestMapping("/device")
public class OnvifController {

    @Autowired
    private IOnvifService onvifService;

    /**
     * 获取onvif设备列表
     *
     * @return
     */
    @GetMapping("/getOnvifDeviceList")
    public AjaxResult getOnvifDeviceList() {
        return AjaxResult.success(onvifService.getOnvifDeviceList());
    }

    /**
     * 验证登录onvif设备
     *
     * @param onvifDevice
     * @return
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody WSOnvifDevice onvifDevice) {
        OnvifDevice device = onvifService.verifyOnvifDeviceLogin(onvifDevice);
        return AjaxResult.success((device));
    }
}
