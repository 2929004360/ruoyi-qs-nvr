package com.ruoyi.haikang.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.haikang.api.domain.HaikangDeviceInfo;
import com.ruoyi.haikang.api.domain.LoginDevice;
import com.ruoyi.haikang.api.domain.PresetInfo;
import com.ruoyi.haikang.service.IHaiKangService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 海康sdk Controller
 *
 * @FileName HaiKangController
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@Validated
@RestController
@RequestMapping("/api/haikang")
public class HaiKangApiController {

    @Autowired
    private IHaiKangService haiKangService;

    /**
     * 登录设备，支持 V40 和 V30 版本，功能一致。
     *
     * @param loginDevice 海康设备登录信息
     * @return 登录成功返回用户ID，失败返回-1
     */
    @InnerAuth
    @PostMapping("/loginDevice")
    public R<Integer> loginDevice(@RequestBody LoginDevice loginDevice) {
        return R.ok(haiKangService.loginDevice(
                loginDevice.getIpAddress(),
                loginDevice.getPort(),
                loginDevice.getUserName(),
                loginDevice.getPassword()));
    }

    /**
     * 设备注销
     *
     * @param ip 设备ip
     */
    @InnerAuth
    @PostMapping("/logoutDevice/{ip}")
    public R<Void> logoutDevice(@PathVariable String ip) {
        haiKangService.logoutDevice(ip);
        return R.ok();
    }

    /**
     * 获取设备登录的用户ID
     *
     * @param ip 设备ip
     * @return
     */
    @InnerAuth
    @PostMapping("/getUserId/{ip}")
    public R<Integer> getUserId(@PathVariable String ip) {
        return R.ok(haiKangService.getUserId(ip));
    }

    /**
     * 获取设备的基本参数
     *
     * @param ip 设备ip
     * @return
     */
    @InnerAuth
    @PostMapping("/getDeviceInfo/{ip}")
    public R<HaikangDeviceInfo> getDeviceInfo(@PathVariable String ip) {
        return R.ok(haiKangService.getDeviceInfo(ip));
    }

    /**
     * 开始播放
     *
     * @return
     */
    @InnerAuth
    @PostMapping("/startPlay")
    public R<Void> startPlay(@RequestBody RtpServerParam rtpServerParam) {
        haiKangService.startPlay(rtpServerParam);
        return R.ok();
    }

    /**
     * 开始播放
     *
     * @return
     */
    @InnerAuth
    @GetMapping("/stopPlay/{id}")
    public R<Void> stopPlay(@PathVariable Long id) {
        haiKangService.stopPlay(id);
        return R.ok();
    }

    /**
     * 开始云台控制
     */
    @InnerAuth
    @GetMapping("/startPlayControl/{deviceId}/{channelId}")
    public R<Void> startPlayControl(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, String direction) {
        haiKangService.startPlayControl(deviceId, channelId, direction);
        return R.ok();
    }

    /**
     * 结束云台控制
     */
    @InnerAuth
    @GetMapping("/endPlayControl/{deviceId}/{channelId}")
    public R<Void> endPlayControl(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, String direction) {
        haiKangService.endPlayControl(deviceId, channelId, direction);
        return R.ok();
    }

    /**
     * 重启设备
     */
    @GetMapping("/restartDevice/{deviceId}")
    public R<Void> restartDevice(@PathVariable("deviceId") Long deviceId) {
        haiKangService.restartDevice(deviceId);
        return R.ok();
    }

    /**
     * 关机
     */
    @GetMapping("/shutDown/{deviceId}")
    public R<Void> shutDown(@PathVariable("deviceId") Long deviceId) {
        haiKangService.shutDown(deviceId);
        return R.ok();
    }

    /**
     * 获取设备音频编码参数，确定转发音频参数
     *
     * @param deviceId
     * @param channelId
     * @return
     */
    @GetMapping("/getCurrentAudio/{deviceId}/{channelId}")
    public R<HashMap<String, Object>> getCurrentAudio(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId) {
        return R.ok(haiKangService.getCurrentAudio(deviceId, channelId));
    }

    /**
     * 获取预置点列表
     *
     * @param deviceId
     * @param channelId
     * @return
     */
    @GetMapping("/getPresets/{deviceId}/{channelId}")
    public R<List<PresetInfo>> getPresets(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId) {
        return R.ok(haiKangService.getPresets(deviceId, channelId));
    }

    /**
     * 设置预置点
     *
     * @param deviceId
     * @param channelId
     * @param presetIndex
     * @return
     */
    @GetMapping("/setPresets/{deviceId}/{channelId}")
    public R<Void> setPresets(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, int presetIndex) {
        haiKangService.setPresets(deviceId, channelId, presetIndex);
        return R.ok();
    }

    /**
     * 清除预置点
     *
     * @param deviceId
     * @param channelId
     * @param presetIndex
     * @return
     */
    @GetMapping("/delPresets/{deviceId}/{channelId}")
    public R<Void> delPresets(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, int presetIndex) {
        haiKangService.delPresets(deviceId, channelId, presetIndex);
        return R.ok();
    }

    /**
     * 调用预置点
     *
     * @param deviceId
     * @param channelId
     * @param presetIndex
     * @return
     */
    @GetMapping("/invokePresets/{deviceId}/{channelId}")
    public R<Void> invokePresets(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, int presetIndex) {
        haiKangService.invokePresets(deviceId, channelId, presetIndex);
        return R.ok();
    }

    /**
     * 辅助设备控制（灯光、雨刷、风扇、加热器等）
     *
     * @param deviceId  设备ID
     * @param channelId 通道ID
     * @param operation 操作类型
     * @param isStart   是否开始（true开始，false停止）
     * @return
     */
    @InnerAuth
    @GetMapping("/cameraAuxControl/{deviceId}/{channelId}")
    public R<Void> cameraAuxControl(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, String operation, boolean isStart) {
        haiKangService.cameraAuxControl(deviceId, channelId, operation, isStart);
        return R.ok();
    }

    /**
     * 巡航控制
     *
     * @param deviceId  设备ID
     * @param channelId 通道ID
     * @param operation 操作类型
     * @param param     参数（预置点号、停顿时间、速度等，根据操作类型不同而不同）
     * @return
     */
    @InnerAuth
    @GetMapping("/cruiseControl/{deviceId}/{channelId}")
    public R<Void> cruiseControl(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, String operation, Integer param) {
        haiKangService.cruiseControl(deviceId, channelId, operation, param);
        return R.ok();
    }

    /**
     * 获取球机PTZ参数
     */
    @InnerAuth
    @GetMapping("/getPTZcfg/{deviceId}/{channelId}")
    public R<HashMap<String, Object>> getPTZcfg(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId) {
        return R.ok(haiKangService.getPTZcfg(deviceId, channelId));
    }

    /**
     * 设置球机PTZ参数
     */
    @InnerAuth
    @GetMapping("/setPTZcfg/{deviceId}/{channelId}")
    public R<Void> setPTZcfg(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, short p, short t, short z) {
        haiKangService.setPTZcfg(deviceId, channelId, p, t, z);
        return R.ok();
    }

    /**
     * 获取高精度PTZ绝对位置配置,一般热成像设备支持
     */
    @InnerAuth
    @GetMapping("/getPTZAbsoluteEx/{deviceId}/{channelId}")
    public R<HashMap<String, Object>> getPTZAbsoluteEx(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId) {
        return R.ok(haiKangService.getPTZAbsoluteEx(deviceId, channelId));
    }

    /**
     * 获取设备时间参数
     */
    @InnerAuth
    @GetMapping("/getDevTime/{deviceId}")
    public R<String> getDevTime(@PathVariable("deviceId") Long deviceId) {
        return R.ok(haiKangService.getDevTime(deviceId));
    }


    /**
     * 设置设备时间参数
     */
    @InnerAuth
    @GetMapping("/setDevTime/{deviceId}")
    public R<Void> setDevTime(@PathVariable("deviceId") Long deviceId, String time) {
        haiKangService.setDevTime(deviceId, time);
        return R.ok();
    }

    /**
     * 海康设备查询录像
     */
    @GetMapping("/getRecMonth/{deviceId}/{channelId}")
    public R<ArrayList<HashMap<String, Object>>> getRecMonth(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, @NotBlank(message = "开始时间不能为空") String startTime, @NotBlank(message = "结束时间不能为空") String endTime) {
        return R.ok(haiKangService.queryRecord(deviceId, channelId, startTime, endTime));
    }
}
