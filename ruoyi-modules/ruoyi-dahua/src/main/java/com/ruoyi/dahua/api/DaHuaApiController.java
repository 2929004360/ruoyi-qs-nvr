package com.ruoyi.dahua.api;

import cn.hutool.core.util.ObjUtil;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.dahua.api.domain.DahuaDevice;
import com.ruoyi.dahua.api.domain.LoginDevice;
import com.ruoyi.dahua.service.IDaHuaService;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 大华sdk Controller
 *
 * @FileName DaHuaController
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
@RestController
@RequestMapping("/api/dahua")
public class DaHuaApiController {

    @Autowired
    private IDaHuaService daHuaService;

    /**
     * 大华设备登录
     *
     * @param loginDevice
     */
    @InnerAuth
    @PostMapping("/loginDevice")
    public R<Void> loginDevice(@RequestBody LoginDevice loginDevice) {
        daHuaService.loginDevice(loginDevice.getIpAddress(),
                loginDevice.getPort(),
                loginDevice.getUserName(),
                loginDevice.getPassword(),
                loginDevice.getDeviceId(),
                loginDevice.getOnlineType()
        );
        return R.ok();
    }

    /**
     * 查询是否登录
     *
     * @param ip 设备ip
     */
    @InnerAuth
    @PostMapping("/isUserId/{ip}")
    public R<Boolean> isUserId(@PathVariable String ip) {
        return R.ok(daHuaService.isUserId(ip));
    }

    /**
     * 大华设备获取时间
     *
     * @param ip 设备ip
     */
    @InnerAuth
    @PostMapping("/getTime/{ip}")
    public R<String> getTime(@PathVariable String ip) {
        return R.ok(daHuaService.getTime(ip));
    }

    /**
     * 大华设备设置时间
     */
    @InnerAuth
    @GetMapping("/setTime/{id}")
    public R<Boolean> setTime(@PathVariable Long id, String date, boolean type) {
        return R.ok(daHuaService.setTime(id, date, type));
    }

    /**
     * 大华设备重启
     */
    @InnerAuth
    @GetMapping("/reboot/{id}")
    public R<Boolean> reboot(@PathVariable Long id) {
        return R.ok(daHuaService.reboot(id));
    }

    /**
     * 获取大华主动上线设备
     *
     * @param ip 设备ip
     */
    @InnerAuth
    @PostMapping("/getDahuaDevice/{ip}")
    public R<DahuaDevice> getDahuaDevice(@PathVariable String ip) {
        return R.ok(daHuaService.getDahuaDevice(ip));
    }

    /**
     * 获取大华主动上线设备
     *
     * @param ip 设备ip
     */
    @InnerAuth
    @PostMapping("/logoutDevice/{ip}")
    public R<Boolean> logoutDevice(@PathVariable String ip) {
        return R.ok(daHuaService.logoutDevice(ip));
    }

    /**
     * 开始播放
     *
     * @param rtpServerParam 播放参数
     */
    @InnerAuth
    @PostMapping("/startPlay")
    public R<Void> startPlay(@RequestBody RtpServerParam rtpServerParam) {
        daHuaService.startPlay(rtpServerParam);
        return R.ok();
    }

    /**
     * 停止播放
     *
     * @param id 设备id
     */
    @InnerAuth
    @GetMapping("/stopPlay/{id}")
    public R<Void> stopPlay(@PathVariable Long id) {
        daHuaService.stopPlay(id);
        return R.ok();
    }

    /**
     * 大华设备云台控制（开始）
     */
    @InnerAuth
    @GetMapping("/ptzControlUpStart/{id}/{channelId}")
    public R<Boolean> ptzControlUpStart(@PathVariable Long id, @PathVariable int channelId, String direction, Integer speed) {
        if (StringUtils.isEmpty(direction)) {
            direction = "up";
        }
        if (ObjUtil.isNull(speed)) {
            speed = 5;
        }
        return R.ok(daHuaService.ptzControlStart(direction, id, speed, channelId));
    }

    /**
     * 大华设备云台控制（停止）
     */
    @InnerAuth
    @GetMapping("/ptzControlUpEnd/{id}/{channelId}")
    public R<Boolean> ptzControlUpEnd(@PathVariable Long id, @PathVariable int channelId, String direction) {
        if (StringUtils.isEmpty(direction)) {
            direction = "up";
        }
        return R.ok(daHuaService.ptzControlUpEnd(direction, id, channelId));
    }

    /**
     * 大华设备获取预置点列表
     *
     * @param id
     * @param channelId
     * @return
     */
    @GetMapping("/getPresetList/{id}/{channelId}")
    public R<ArrayList<HashMap<String, Object>>> getPresetList(@PathVariable Long id, @PathVariable int channelId) {
        return R.ok(daHuaService.getPresetList(id, channelId));
    }

    /**
     * 大华设备设置预置点
     *
     * @param id
     * @param channelId
     * @param presetIndex
     * @return
     */
    @GetMapping("/setPreset/{id}/{channelId}")
    public R<Void> setPreset(@PathVariable Long id, @PathVariable int channelId, int presetIndex) {
        daHuaService.setPreset(id, channelId, presetIndex);
        return R.ok();
    }

    /**
     * 大华删除设置预置点
     *
     * @param id
     * @param channelId
     * @param presetIndex
     * @return
     */
    @GetMapping("/delPreset/{id}/{channelId}")
    public R<Void> delPreset(@PathVariable Long id, @PathVariable int channelId, int presetIndex) {
        daHuaService.delPreset(id, channelId, presetIndex);
        return R.ok();
    }

    /**
     * 大华调用设置预置点
     *
     * @param id
     * @param channelId
     * @param presetIndex
     * @return
     */
    @GetMapping("/invokePreset/{id}/{channelId}")
    public R<Void> invokePreset(@PathVariable Long id, @PathVariable int channelId, int presetIndex) {
        daHuaService.invokePreset(id, channelId, presetIndex);
        return R.ok();
    }

    /**
     * 灯光控制
     *
     * @param id
     * @param channelId
     * @param action 0-关,1-开
     * @return
     */
    @GetMapping("/controlLight/{id}/{channelId}")
    public R<Void> controlLight(@PathVariable Long id, @PathVariable int channelId, int action) {
        daHuaService.controlLight(id, channelId, action);
        return R.ok();
    }

    /**
     * 雨刷控制
     *
     * @param id
     * @param channelId
     * @param action 0-关,1-开
     * @return
     */
    @GetMapping("/controlWiper/{id}/{channelId}")
    public R<Void> controlWiper(@PathVariable Long id, @PathVariable int channelId, int action) {
        daHuaService.controlWiper(id, channelId, action);
        return R.ok();
    }

    /**
     * 开始点间巡航
     *
     * @param id
     * @param channelId
     * @param tourIndex 巡航线路号
     * @return
     */
    @GetMapping("/startTour/{id}/{channelId}")
    public R<Void> startTour(@PathVariable Long id, @PathVariable int channelId, int tourIndex) {
        daHuaService.startTour(id, channelId, tourIndex);
        return R.ok();
    }

    /**
     * 停止点间巡航
     *
     * @param id
     * @param channelId
     * @return
     */
    @GetMapping("/stopTour/{id}/{channelId}")
    public R<Void> stopTour(@PathVariable Long id, @PathVariable int channelId) {
        daHuaService.stopTour(id, channelId);
        return R.ok();
    }

    /**
     * 添加预置点到巡航线路
     *
     * @param id
     * @param channelId
     * @param tourIndex 巡航线路号
     * @param presetIndex 预置点号
     * @return
     */
    @GetMapping("/addPresetToTour/{id}/{channelId}")
    public R<Void> addPresetToTour(@PathVariable Long id, @PathVariable int channelId, int tourIndex, int presetIndex) {
        daHuaService.addPresetToTour(id, channelId, tourIndex, presetIndex);
        return R.ok();
    }

    /**
     * 从巡航线路删除预置点
     *
     * @param id
     * @param channelId
     * @param tourIndex 巡航线路号
     * @param presetIndex 预置点号
     * @return
     */
    @GetMapping("/removePresetFromTour/{id}/{channelId}")
    public R<Void> removePresetFromTour(@PathVariable Long id, @PathVariable int channelId, int tourIndex, int presetIndex) {
        daHuaService.removePresetFromTour(id, channelId, tourIndex, presetIndex);
        return R.ok();
    }

    /**
     * 清除巡航线路
     *
     * @param id
     * @param channelId
     * @param tourIndex 巡航线路号
     * @return
     */
    @GetMapping("/clearTour/{id}/{channelId}")
    public R<Void> clearTour(@PathVariable Long id, @PathVariable int channelId, int tourIndex) {
        daHuaService.clearTour(id, channelId, tourIndex);
        return R.ok();
    }

    /**
     * 大华设备查询录像
     */
    @GetMapping("/queryRecord/{id}/{channelId}")
    public R<ArrayList<HashMap<String, Object>>> queryRecord(@PathVariable Long id, @PathVariable int channelId, @NotBlank(message = "开始时间不能为空") String startTime, @NotBlank(message = "结束时间不能为空") String endTime) {
        return R.ok(daHuaService.queryRecord(id, channelId, startTime, endTime));
    }
}
