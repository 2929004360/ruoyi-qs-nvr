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
import com.ruoyi.dahua.api.domain.DahuaStorageInfo;
import com.ruoyi.dahua.api.domain.DahuaSystemResourceInfo;
import com.ruoyi.dahua.api.domain.DahuaSDCardInfo;
import com.ruoyi.dahua.api.domain.DahuaBitrateInfo;
import com.ruoyi.dahua.api.domain.DahuaNetworkStatusInfo;
import com.ruoyi.dahua.api.domain.DahuaSoftwareVersionInfo;
import com.ruoyi.dahua.api.domain.DahuaRecordStateInfo;
import com.ruoyi.dahua.api.domain.DahuaPowerStateInfo;
import com.ruoyi.dahua.api.domain.DahuaAlarmArmInfo;
import com.ruoyi.dahua.api.domain.DahuaCameraInfo;
import com.ruoyi.dahua.api.domain.DahuaRtspUrlInfo;
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

    /**
     * 大华设备抓图并保存
     */
    @PostMapping("/captureAndSave/{id}/{channelId}")
    public R<Long> captureAndSave(@PathVariable Long id, @PathVariable int channelId, String snapshotType) {
        if (snapshotType == null || snapshotType.isEmpty()) {
            snapshotType = "manual";
        }
        return R.ok(daHuaService.captureAndSave(id, channelId, snapshotType));
    }

    /**
     * 获取大华设备存储/硬盘信息
     */
    @GetMapping("/storageInfo/{id}")
    public R<DahuaStorageInfo> getStorageInfo(@PathVariable Long id) {
        return R.ok(daHuaService.getStorageInfo(id));
    }

    /**
     * 获取大华设备系统资源信息
     */
    @GetMapping("/systemResourceInfo/{id}")
    public R<DahuaSystemResourceInfo> getSystemResourceInfo(@PathVariable Long id) {
        return R.ok(daHuaService.getSystemResourceInfo(id));
    }

    /**
     * 获取大华设备SD卡信息
     */
    @GetMapping("/sdCardInfo/{id}")
    public R<DahuaSDCardInfo> getSDCardInfo(@PathVariable Long id) {
        return R.ok(daHuaService.getSDCardInfo(id));
    }

    /**
     * 获取大华设备通道码流信息
     */
    @GetMapping("/bitrateInfo/{id}")
    public R<DahuaBitrateInfo> getBitrateInfo(@PathVariable Long id) {
        return R.ok(daHuaService.getBitrateInfo(id));
    }

    /**
     * 获取大华设备网络状态信息
     */
    @GetMapping("/networkStatusInfo/{id}")
    public R<DahuaNetworkStatusInfo> getNetworkStatusInfo(@PathVariable Long id) {
        return R.ok(daHuaService.getNetworkStatusInfo(id));
    }

    /**
     * 获取大华设备软件版本信息
     */
    @GetMapping("/softwareVersionInfo/{id}")
    public R<DahuaSoftwareVersionInfo> getSoftwareVersionInfo(@PathVariable Long id) {
        return R.ok(daHuaService.getSoftwareVersionInfo(id));
    }

    /**
     * 获取大华设备录像状态信息
     */
    @GetMapping("/recordStateInfo/{id}")
    public R<DahuaRecordStateInfo> getRecordStateInfo(@PathVariable Long id) {
        return R.ok(daHuaService.getRecordStateInfo(id));
    }

    /**
     * 获取大华设备电源状态信息
     */
    @GetMapping("/powerStateInfo/{id}")
    public R<DahuaPowerStateInfo> getPowerStateInfo(@PathVariable Long id) {
        return R.ok(daHuaService.getPowerStateInfo(id));
    }

    /**
     * 获取大华设备报警布撤防信息
     */
    @GetMapping("/alarmArmInfo/{id}")
    public R<DahuaAlarmArmInfo> getAlarmArmInfo(@PathVariable Long id) {
        return R.ok(daHuaService.getAlarmArmInfo(id));
    }

    /**
     * 获取大华设备摄像头属性信息
     */
    @GetMapping("/cameraInfo/{id}")
    public R<DahuaCameraInfo> getCameraInfo(@PathVariable Long id) {
        return R.ok(daHuaService.getCameraInfo(id));
    }

    /**
     * 获取大华设备RTSP URL信息
     */
    @GetMapping("/rtspUrlInfo/{id}")
    public R<DahuaRtspUrlInfo> getRtspUrlInfo(@PathVariable Long id) {
        return R.ok(daHuaService.getRtspUrlInfo(id));
    }
}

