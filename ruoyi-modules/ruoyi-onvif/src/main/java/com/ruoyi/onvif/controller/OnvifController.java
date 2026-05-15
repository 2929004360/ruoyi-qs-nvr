package com.ruoyi.onvif.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.onvif.api.domain.OnvifDevice;
import com.ruoyi.onvif.api.domain.WSOnvifDevice;
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

    /**
     * 查询录像文件
     *
     * @param deviceIp  设备IP
     * @param username  用户名
     * @param password  密码
     * @param startTime 开始时间，格式：yyyy-MM-dd HH:mm:ss
     * @param endTime   结束时间，格式：yyyy-MM-dd HH:mm:ss
     * @return 录像文件列表
     */
    @GetMapping("/queryRecord")
    public R<Object> queryRecord(@RequestParam String deviceIp,
                                   @RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam String startTime,
                                   @RequestParam String endTime) {
        return R.ok(onvifService.queryRecord(deviceIp, username, password, startTime, endTime));
    }

    /**
     * 获取回放地址
     *
     * @param deviceIp  设备IP
     * @param username  用户名
     * @param password  密码
     * @param recordingToken 录制令牌
     * @param trackToken 轨道令牌
     * @return 回放地址
     */
    @GetMapping("/getReplayUri")
    public R<String> getReplayUri(@RequestParam String deviceIp,
                                    @RequestParam String username,
                                    @RequestParam String password,
                                    @RequestParam String recordingToken,
                                    @RequestParam(required = false) String trackToken) {
        return R.ok(onvifService.getReplayUri(deviceIp, username, password, recordingToken, trackToken));
    }
}
