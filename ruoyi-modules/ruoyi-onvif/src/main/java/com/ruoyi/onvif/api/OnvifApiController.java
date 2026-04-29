package com.ruoyi.onvif.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.onvif.api.domain.OnvifDevice;
import com.ruoyi.onvif.api.domain.WSOnvifDevice;
import com.ruoyi.onvif.service.IOnvifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * onvif 设备
 *
 * @FileName OnvifController
 * @Description
 * @Author fengcheng
 * @date 2026-04-09
 **/
@RestController
@RequestMapping("/api/onvif/")
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

    /**
     * 开始云台控制
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param direction 方向
     * @param speed 速度
     * @return
     */
    @InnerAuth
    @GetMapping("/startPtzControl/{deviceIp}")
    public R<Void> startPtzControl(@PathVariable("deviceIp") String deviceIp,
                                     @RequestParam("username") String username,
                                     @RequestParam("password") String password,
                                     @RequestParam("direction") String direction,
                                     @RequestParam(value = "speed", defaultValue = "50") Integer speed) {
        onvifService.startPtzControl(deviceIp, username, password, direction, speed);
        return R.ok();
    }

    /**
     * 停止云台控制
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @InnerAuth
    @GetMapping("/stopPtzControl/{deviceIp}")
    public R<Void> stopPtzControl(@PathVariable("deviceIp") String deviceIp,
                                    @RequestParam("username") String username,
                                    @RequestParam("password") String password) {
        onvifService.stopPtzControl(deviceIp, username, password);
        return R.ok();
    }

    /**
     * 获取预置点列表
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @InnerAuth
    @GetMapping("/getPresets/{deviceIp}")
    public R<List<Map<String, Object>>> getPresets(@PathVariable("deviceIp") String deviceIp,
                                                      @RequestParam("username") String username,
                                                      @RequestParam("password") String password) {
        List<Map<String, Object>> presets = onvifService.getPresets(deviceIp, username, password);
        return R.ok(presets);
    }

    /**
     * 设置预置点
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param presetIndex 预置点索引
     * @param presetName 预置点名称
     * @return
     */
    @InnerAuth
    @GetMapping("/setPreset/{deviceIp}")
    public R<Void> setPreset(@PathVariable("deviceIp") String deviceIp,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam(value = "presetIndex", required = false) Integer presetIndex,
                               @RequestParam(value = "presetName", required = false) String presetName) {
        onvifService.setPreset(deviceIp, username, password, presetIndex, presetName);
        return R.ok();
    }

    /**
     * 调用预置点
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param presetIndex 预置点索引
     * @param speed 速度
     * @return
     */
    @InnerAuth
    @GetMapping("/gotoPreset/{deviceIp}")
    public R<Void> gotoPreset(@PathVariable("deviceIp") String deviceIp,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("presetIndex") Integer presetIndex,
                               @RequestParam(value = "speed", defaultValue = "50") Integer speed) {
        onvifService.gotoPreset(deviceIp, username, password, presetIndex, speed);
        return R.ok();
    }

    /**
     * 删除预置点
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param presetIndex 预置点索引
     * @return
     */
    @InnerAuth
    @GetMapping("/removePreset/{deviceIp}")
    public R<Void> removePreset(@PathVariable("deviceIp") String deviceIp,
                                  @RequestParam("username") String username,
                                  @RequestParam("password") String password,
                                  @RequestParam("presetIndex") Integer presetIndex) {
        onvifService.removePreset(deviceIp, username, password, presetIndex);
        return R.ok();
    }
}
