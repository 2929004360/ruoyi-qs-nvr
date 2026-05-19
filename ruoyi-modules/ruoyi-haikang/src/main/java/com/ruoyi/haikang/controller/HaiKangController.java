package com.ruoyi.haikang.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.haikang.service.IHaiKangService;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


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
@RequestMapping("/device")
public class HaiKangController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(HaiKangController.class);

    @Autowired
    private IHaiKangService haiKangService;


    /**
     * 海康设备查询录像
     */
    @GetMapping("/getRecMonth/{deviceId}/{channelId}")
    public R<ArrayList<HashMap<String, Object>>> getRecMonth(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") Integer channelId, @NotBlank(message = "开始时间不能为空") String startTime, @NotBlank(message = "结束时间不能为空") String endTime) {
        log.info("海康设备查询录像 - deviceId: {}, channelId: {}, startTime: {}, endTime: {}", deviceId, channelId, startTime, endTime);
        if (channelId == null) {
            return R.fail("channelId参数不能为空");
        }
        return R.ok(haiKangService.queryRecord(deviceId, channelId, startTime, endTime));
    }

    /**
     * 海康设备录像下载
     */
    @PostMapping("/downloadRecord")
    public R<com.ruoyi.haikang.api.domain.HaikangRecordDownloadResponse> downloadRecord(@RequestBody com.ruoyi.haikang.api.domain.HaikangRecordDownloadRequest request) {
        log.info("海康设备录像下载 - request: {}", request);
        return R.ok(haiKangService.downloadRecord(request));
    }

    /**
     * 海康设备录像直接下载到用户电脑
     */
    @PostMapping("/downloadRecordDirect")
    public ResponseEntity<Resource> downloadRecordDirect(@RequestBody com.ruoyi.haikang.api.domain.HaikangRecordDownloadRequest request) throws Exception {
        log.info("海康设备录像直接下载到用户电脑 - request: {}", request);
        File file = haiKangService.downloadRecordFile(request);
        log.info("海康设备录像文件下载完成 - fileName: {}", file.getName());
        Resource resource = new FileSystemResource(file);
        
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
            .body(resource);
    }

    /**
     * 海康设备抓图并保存
     */
    @PostMapping("/captureAndSave/{id}/{channelId}")
    public R<Long> captureAndSave(@PathVariable Long id, @PathVariable Integer channelId, String snapshotType) throws IOException {
        if (snapshotType == null || snapshotType.isEmpty()) {
            snapshotType = "manual";
        }
        log.info("海康设备抓图并保存 - id: {}, channelId: {}, snapshotType: {}", id, channelId, snapshotType);
        if (channelId == null) {
            return R.fail("channelId参数不能为空");
        }
        return R.ok(haiKangService.captureAndSave(id, channelId, snapshotType));
    }

    /**
     * 获取海康设备信息
     */
    @GetMapping("/getHaiKangDeviceInfo/{deviceId}")
    public R<HashMap<String, Object>> getHaiKangDeviceInfo(@PathVariable("deviceId") Long deviceId) {
        log.info("获取海康设备信息 - deviceId: {}", deviceId);
        return R.ok(haiKangService.getHaiKangDeviceInfo(deviceId));
    }

    /**
     * 获取海康存储信息
     */
    @GetMapping("/getHaiKangStorageInfo/{deviceId}")
    public R<HashMap<String, Object>> getHaiKangStorageInfo(@PathVariable("deviceId") Long deviceId) {
        log.info("获取海康存储信息 - deviceId: {}", deviceId);
        return R.ok(haiKangService.getHaiKangStorageInfo(deviceId));
    }

    /**
     * 获取海康SD卡信息
     */
    @GetMapping("/getHaiKangSDCardInfo/{deviceId}")
    public R<HashMap<String, Object>> getHaiKangSDCardInfo(@PathVariable("deviceId") Long deviceId) {
        log.info("获取海康SD卡信息 - deviceId: {}", deviceId);
        return R.ok(haiKangService.getHaiKangSDCardInfo(deviceId));
    }

    /**
     * 获取海康码率信息
     */
    @GetMapping("/getHaiKangBitrateInfo/{deviceId}")
    public R<HashMap<String, Object>> getHaiKangBitrateInfo(@PathVariable("deviceId") Long deviceId) {
        log.info("获取海康码率信息 - deviceId: {}", deviceId);
        return R.ok(haiKangService.getHaiKangBitrateInfo(deviceId));
    }

    /**
     * 获取海康网络状态信息
     */
    @GetMapping("/getHaiKangNetworkStatusInfo/{deviceId}")
    public R<HashMap<String, Object>> getHaiKangNetworkStatusInfo(@PathVariable("deviceId") Long deviceId) {
        log.info("获取海康网络状态信息 - deviceId: {}", deviceId);
        return R.ok(haiKangService.getHaiKangNetworkStatusInfo(deviceId));
    }

    /**
     * 获取海康软件版本信息
     */
    @GetMapping("/getHaiKangSoftwareVersionInfo/{deviceId}")
    public R<HashMap<String, Object>> getHaiKangSoftwareVersionInfo(@PathVariable("deviceId") Long deviceId) {
        log.info("获取海康软件版本信息 - deviceId: {}", deviceId);
        return R.ok(haiKangService.getHaiKangSoftwareVersionInfo(deviceId));
    }

    /**
     * 获取海康录像状态信息
     */
    @GetMapping("/getHaiKangRecordStateInfo/{deviceId}")
    public R<HashMap<String, Object>> getHaiKangRecordStateInfo(@PathVariable("deviceId") Long deviceId) {
        log.info("获取海康录像状态信息 - deviceId: {}", deviceId);
        return R.ok(haiKangService.getHaiKangRecordStateInfo(deviceId));
    }

    /**
     * 获取海康电源状态信息
     */
    @GetMapping("/getHaiKangPowerStateInfo/{deviceId}")
    public R<HashMap<String, Object>> getHaiKangPowerStateInfo(@PathVariable("deviceId") Long deviceId) {
        log.info("获取海康电源状态信息 - deviceId: {}", deviceId);
        return R.ok(haiKangService.getHaiKangPowerStateInfo(deviceId));
    }

    /**
     * 获取海康报警布撤防信息
     */
    @GetMapping("/getHaiKangAlarmArmInfo/{deviceId}")
    public R<HashMap<String, Object>> getHaiKangAlarmArmInfo(@PathVariable("deviceId") Long deviceId) {
        log.info("获取海康报警布撤防信息 - deviceId: {}", deviceId);
        return R.ok(haiKangService.getHaiKangAlarmArmInfo(deviceId));
    }

    /**
     * 获取海康摄像头属性信息
     */
    @GetMapping("/getHaiKangCameraInfo/{deviceId}")
    public R<com.ruoyi.haikang.api.domain.HaiKangCameraInfo> getHaiKangCameraInfo(@PathVariable("deviceId") Long deviceId) {
        log.info("获取海康摄像头属性信息 - deviceId: {}", deviceId);
        return R.ok(haiKangService.getHaiKangCameraInfo(deviceId));
    }

    /**
     * 获取海康RTSP URL
     */
    @GetMapping("/getHaiKangRtspUrlInfo/{deviceId}")
    public R<HashMap<String, Object>> getHaiKangRtspUrlInfo(@PathVariable("deviceId") Long deviceId) {
        log.info("获取海康RTSP URL - deviceId: {}", deviceId);
        return R.ok(haiKangService.getHaiKangRtspUrlInfo(deviceId));
    }

    /**
     * 获取海康系统参数
     */
    @GetMapping("/getHaiKangSystemParam/{deviceId}")
    public R<HashMap<String, Object>> getHaiKangSystemParam(@PathVariable("deviceId") Long deviceId) {
        log.info("获取海康系统参数 - deviceId: {}", deviceId);
        return R.ok(haiKangService.getHaiKangSystemParam(deviceId));
    }

    /**
     * 获取海康视频参数
     */
    @GetMapping("/getHaiKangVideoParam/{deviceId}/{channelId}")
    public R<HashMap<String, Object>> getHaiKangVideoParam(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") Integer channelId, String streamType) {
        log.info("获取海康视频参数 - deviceId:{}, channelId:{}, streamType:{}", deviceId, channelId, streamType);
        if (channelId == null) {
            return R.fail("channelId参数不能为空");
        }
        return R.ok(haiKangService.getHaiKangVideoParam(deviceId, channelId, streamType));
    }

    /**
     * 获取海康设备时间
     */
    @GetMapping("/getHaiKangDevTime/{deviceId}")
    public R<String> getHaiKangDevTime(@PathVariable("deviceId") Long deviceId) {
        log.info("获取海康设备时间 - deviceId:{}", deviceId);
        return R.ok(haiKangService.getDevTime(deviceId));
    }

    /**
     * 设置海康设备时间
     */
    @GetMapping("/setHaiKangDevTime/{deviceId}")
    public R<Boolean> setHaiKangDevTime(@PathVariable("deviceId") Long deviceId, String time) {
        log.info("设置海康设备时间 - deviceId:{}, time:{}", deviceId, time);
        haiKangService.setDevTime(deviceId, time);
        return R.ok(true);
    }

    /**
     * 重启海康设备
     */
    @GetMapping("/rebootHaiKangDevice/{deviceId}")
    public R<Boolean> rebootHaiKangDevice(@PathVariable("deviceId") Long deviceId) {
        log.info("重启海康设备 - deviceId:{}", deviceId);
        haiKangService.restartDevice(deviceId);
        return R.ok(true);
    }
}
