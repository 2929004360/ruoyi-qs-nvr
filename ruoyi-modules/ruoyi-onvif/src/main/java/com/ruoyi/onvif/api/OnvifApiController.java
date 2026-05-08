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

    /**
     * 灯光控制
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param on true为开灯，false为关灯
     * @return
     */
    @InnerAuth
    @GetMapping("/controlLight/{deviceIp}")
    public R<Void> controlLight(@PathVariable("deviceIp") String deviceIp,
                                 @RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("on") boolean on) {
        onvifService.controlLight(deviceIp, username, password, on);
        return R.ok();
    }

    /**
     * 雨刷控制
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param on true为开雨刷，false为关雨刷
     * @return
     */
    @InnerAuth
    @GetMapping("/controlWiper/{deviceIp}")
    public R<Void> controlWiper(@PathVariable("deviceIp") String deviceIp,
                                 @RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("on") boolean on) {
        onvifService.controlWiper(deviceIp, username, password, on);
        return R.ok();
    }

    /**
     * 设备重启
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @InnerAuth
    @GetMapping("/restartDevice/{deviceIp}")
    public R<Void> restartDevice(@PathVariable("deviceIp") String deviceIp,
                                  @RequestParam("username") String username,
                                  @RequestParam("password") String password) {
        onvifService.restartDevice(deviceIp, username, password);
        return R.ok();
    }

    /**
     * 恢复出厂设置
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param factoryDefault 恢复模式："Full"为完全恢复，"Partial"为部分恢复
     * @return
     */
    @InnerAuth
    @GetMapping("/factoryReset/{deviceIp}")
    public R<Void> factoryReset(@PathVariable("deviceIp") String deviceIp,
                                 @RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("factoryDefault") String factoryDefault) {
        onvifService.factoryReset(deviceIp, username, password, factoryDefault);
        return R.ok();
    }

    /**
     * 获取设备时间
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @return 设备时间信息
     */
    @InnerAuth
    @GetMapping("/getDeviceTime/{deviceIp}")
    public R<Map<String, Object>> getDeviceTime(@PathVariable("deviceIp") String deviceIp,
                                                 @RequestParam("username") String username,
                                                 @RequestParam("password") String password) {
        Map<String, Object> timeInfo = onvifService.getDeviceTime(deviceIp, username, password);
        return R.ok(timeInfo);
    }

    /**
     * 设备校时
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param dateTime 要设置的时间，格式：yyyy-MM-dd'T'HH:mm:ss
     * @return
     */
    @InnerAuth
    @GetMapping("/syncDeviceTime/{deviceIp}")
    public R<Void> syncDeviceTime(@PathVariable("deviceIp") String deviceIp,
                                   @RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam("dateTime") String dateTime) {
        onvifService.syncDeviceTime(deviceIp, username, password, dateTime);
        return R.ok();
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
    @InnerAuth
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
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param recordingToken 录制令牌
     * @param trackToken 轨道令牌
     * @return 回放地址
     */
    @InnerAuth
    @GetMapping("/getReplayUri")
    public R<String> getReplayUri(@RequestParam String deviceIp,
                                    @RequestParam String username,
                                    @RequestParam String password,
                                    @RequestParam String recordingToken,
                                    @RequestParam String trackToken) {
        return R.ok(onvifService.getReplayUri(deviceIp, username, password, recordingToken, trackToken));
    }
}
