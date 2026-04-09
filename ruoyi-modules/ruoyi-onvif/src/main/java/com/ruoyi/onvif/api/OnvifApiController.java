package com.ruoyi.onvif.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.onvif.domain.OnvifDevice;
import com.ruoyi.onvif.domain.WSOnvifDevice;
import com.ruoyi.onvif.service.IOnvifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * onvif 设备
 *
 * @FileName OnvifController
 * @Description
 * @Author fengcheng
 * @date 2026-04-09
 **/
@RestController
@RequestMapping("/onvif//device")
public class OnvifApiController {

    @Autowired
    private IOnvifService onvifService;

    /**
     * 验证登录onvif设备
     *
     * @param onvifDevice
     * @return
     */
    @InnerAuth
    @PostMapping("/login")
    public R<OnvifDevice> login(@RequestBody WSOnvifDevice onvifDevice) {
        OnvifDevice device = onvifService.verifyOnvifDeviceLogin(onvifDevice);
        return R.ok(device);
    }
}
