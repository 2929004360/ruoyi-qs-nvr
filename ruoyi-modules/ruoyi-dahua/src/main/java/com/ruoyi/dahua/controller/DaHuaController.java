package com.ruoyi.dahua.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.dahua.api.domain.DahuaDevice;
import com.ruoyi.dahua.api.domain.DahuaDeviceInfo;
import com.ruoyi.dahua.api.domain.DahuaSystemParam;
import com.ruoyi.dahua.api.domain.DahuaVideoParam;
import com.ruoyi.dahua.api.domain.DahuaDeviceVideoParam;
import com.ruoyi.dahua.runner.DahuaCommandLineRunnerImpl;
import com.ruoyi.dahua.service.IDaHuaService;

import jakarta.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 大华sdk Controller
 *
 * @FileName DaHuaController
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
@RestController
@RequestMapping("/device")
public class DaHuaController extends BaseController {

    @Autowired
    private IDaHuaService daHuaService;

    @Autowired
    private DahuaCommandLineRunnerImpl commandLineRunnerimpl;

    /**
     * 获取自动注册设备列表
     */
    @GetMapping("/list")
    public AjaxResult list() {
        // 自动注册设备列表
        List<DahuaDevice> registerDeviceList = commandLineRunnerimpl.getRegisterDeviceList();
        return success(registerDeviceList);
    }

    /**
     * 大华设备查询录像
     */
    @GetMapping("/queryRecord/{id}/{channelId}")
    public R<ArrayList<HashMap<String, Object>>> queryRecord(@PathVariable Long id, @PathVariable int channelId, @NotBlank(message = "开始时间不能为空") String startTime, @NotBlank(message = "结束时间不能为空") String endTime) {
        return R.ok(daHuaService.queryRecord(id, channelId, startTime, endTime));
    }

    /**
     * 大华设备获取时间
     *
     * @param ip 设备ip
     */
    @PostMapping("/getTime/{ip}")
    public R<String> getTime(@PathVariable String ip) {
        return R.ok(daHuaService.getTime(ip));
    }

    /**
     * 大华设备设置时间
     */
    @GetMapping("/setTime/{id}")
    public R<Boolean> setTime(@PathVariable Long id, String date, boolean type) {
        return R.ok(daHuaService.setTime(id, date, type));
    }

    /**
     * 大华设备重启
     */
    @GetMapping("/reboot/{id}")
    public R<Boolean> reboot(@PathVariable Long id) {
        return R.ok(daHuaService.reboot(id));
    }

    /**
     * 获取大华设备详细信息
     */
    @GetMapping("/deviceInfo/{id}")
    public R<DahuaDeviceInfo> getDeviceInfo(@PathVariable Long id) {
        return R.ok(daHuaService.getDeviceInfo(id));
    }

    /**
     * 获取大华设备详细信息(通过IP)
     */
    @GetMapping("/deviceInfoByIp/{ip}")
    public R<DahuaDeviceInfo> getDeviceInfoByIp(@PathVariable String ip) {
        return R.ok(daHuaService.getDeviceInfoByIp(ip));
    }

    /**
     * 获取大华设备系统参数
     */
    @GetMapping("/systemParam/{id}")
    public R<DahuaSystemParam> getSystemParam(@PathVariable Long id) {
        return R.ok(daHuaService.getSystemParam(id));
    }

    /**
     * 获取大华设备视频参数
     */
    @GetMapping("/videoParam/{id}/{channelId}")
    public R<DahuaVideoParam> getVideoParam(@PathVariable Long id, @PathVariable int channelId, int streamType) {
        return R.ok(daHuaService.getVideoParam(id, channelId, streamType));
    }

    /**
     * 设置大华设备视频参数
     */
    @PutMapping("/videoParam/{id}/{channelId}")
    public R<Boolean> setVideoParam(@PathVariable Long id, @PathVariable int channelId, int streamType, @RequestBody DahuaVideoParam param) {
        return R.ok(daHuaService.setVideoParam(id, channelId, streamType, param));
    }

    /**
     * 获取大华设备视频输入参数
     */
    @GetMapping("/deviceVideoParam/{id}/{channelId}")
    public R<DahuaDeviceVideoParam> getDeviceVideoParam(@PathVariable Long id, @PathVariable int channelId) {
        return R.ok(daHuaService.getDeviceVideoParam(id, channelId));
    }

    /**
     * 设置大华设备视频输入参数
     */
    @PutMapping("/deviceVideoParam/{id}/{channelId}")
    public R<Boolean> setDeviceVideoParam(@PathVariable Long id, @PathVariable int channelId, @RequestBody DahuaDeviceVideoParam param) {
        return R.ok(daHuaService.setDeviceVideoParam(id, channelId, param));
    }
}

