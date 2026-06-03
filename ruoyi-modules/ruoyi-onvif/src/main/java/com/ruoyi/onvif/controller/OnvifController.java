package com.ruoyi.onvif.controller;

import lombok.extern.slf4j.Slf4j;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.onvif.api.domain.OnvifDevice;
import com.ruoyi.onvif.api.domain.WSOnvifDevice;
import com.ruoyi.onvif.service.IOnvifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * onvif 设备
 *
 * @FileName OnvifController
 * @Description
 * @Author fengcheng
 * @date 2026-04-09
 **/
@Slf4j
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

    /**
     * 设备重启
     *
     * @param id 设备id
     * @return
     */
    @GetMapping("/rebootOnvifDevice/{id}")
    public AjaxResult rebootOnvifDevice(@PathVariable Long id) {
        onvifService.restartDeviceById(id);
        return AjaxResult.success();
    }

    /**
     * 设备校时
     *
     * @param id 设备id
     * @param dateTime 要设置的时间，格式：yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     * @return
     */
    @GetMapping("/syncOnvifDeviceTime/{id}")
    public AjaxResult syncOnvifDeviceTime(@PathVariable Long id, @RequestParam(required = false) String dateTime) {
        onvifService.syncDeviceTimeById(id, dateTime);
        return AjaxResult.success();
    }

    /**
     * 获取设备时间
     *
     * @param id 设备id
     * @return 设备时间信息
     */
    @GetMapping("/getOnvifDeviceTime/{id}")
    public AjaxResult getOnvifDeviceTime(@PathVariable Long id) {
        Map<String, Object> timeInfo = onvifService.getDeviceTimeById(id);
        
        // 格式化时间信息为字符串，方便前端展示
        StringBuilder timeStr = new StringBuilder();
        if (timeInfo.containsKey("year") && timeInfo.containsKey("month") && timeInfo.containsKey("day") && 
            timeInfo.containsKey("hour") && timeInfo.containsKey("minute") && timeInfo.containsKey("second")) {
            
            // 获取 UTC 时间的各个部分
            int year = Integer.parseInt(timeInfo.get("year").toString());
            int month = Integer.parseInt(timeInfo.get("month").toString());
            int day = Integer.parseInt(timeInfo.get("day").toString());
            int hour = Integer.parseInt(timeInfo.get("hour").toString());
            int minute = Integer.parseInt(timeInfo.get("minute").toString());
            int second = Integer.parseInt(timeInfo.get("second").toString());
            
            // 创建 UTC 时间
            java.time.LocalDateTime utcDateTime = java.time.LocalDateTime.of(year, month, day, hour, minute, second);
            // 转换为系统默认时区时间（北京时间）
            java.time.ZonedDateTime zonedDateTime = utcDateTime.atZone(java.time.ZoneId.of("UTC"));
            java.time.LocalDateTime localDateTime = zonedDateTime.withZoneSameInstant(java.time.ZoneId.systemDefault()).toLocalDateTime();
            
            // 格式化为 yyyy-MM-dd HH:mm:ss 格式（与海康、大华保持一致）
            timeStr.append(localDateTime.getYear()).append("-")
                   .append(String.format("%02d", localDateTime.getMonthValue())).append("-")
                   .append(String.format("%02d", localDateTime.getDayOfMonth())).append(" ")
                   .append(String.format("%02d", localDateTime.getHour())).append(":")
                   .append(String.format("%02d", localDateTime.getMinute())).append(":")
                   .append(String.format("%02d", localDateTime.getSecond()));
        }
        
        AjaxResult result = AjaxResult.success();
        result.put("data", timeStr.toString());
        result.put("timeInfo", timeInfo);
        return result;
    }

    /**
     * 获取设备信息
     *
     * @param id 设备id
     * @return 设备信息
     */
    @GetMapping("/getOnvifDeviceInfo/{id}")
    public AjaxResult getOnvifDeviceInfo(@PathVariable Long id) {
        Map<String, Object> deviceInfo = onvifService.getDeviceInfoById(id);
        AjaxResult result = AjaxResult.success();
        result.putAll(deviceInfo);
        return result;
    }

    /**
     * ONVIF设备抓图并保存
     *
     * @param id 设备id
     * @param channelId 通道id
     * @param snapshotType 抓图类型
     * @return 抓图记录id
     */
    @PostMapping("/captureAndSave/{id}/{channelId}")
    public R<Long> captureAndSave(@PathVariable Long id, @PathVariable Integer channelId, String snapshotType) {
        if (snapshotType == null || snapshotType.isEmpty()) {
            snapshotType = "manual";
        }
        log.info("ONVIF设备抓图并保存 - id: {}, channelId: {}, snapshotType: {}", id, channelId, snapshotType);
        if (channelId == null) {
            return R.fail("channelId参数不能为空");
        }
        return R.ok(onvifService.captureAndSaveById(id, channelId, snapshotType));
    }

    /**
     * 获取存储配置
     *
     * @param id 设备id
     * @return 存储配置信息
     */
    @GetMapping("/getStorageConfigurations/{id}")
    public R<Map<String, Object>> getStorageConfigurations(@PathVariable Long id) {
        log.info("获取存储配置 - id: {}", id);
        return R.ok(onvifService.getStorageConfigurationsById(id));
    }

    /**
     * 获取存储能力
     *
     * @param id 设备id
     * @return 存储能力信息
     */
    @GetMapping("/getStorageCapabilities/{id}")
    public R<Map<String, Object>> getStorageCapabilities(@PathVariable Long id) {
        log.info("获取存储能力 - id: {}", id);
        return R.ok(onvifService.getStorageCapabilitiesById(id));
    }

    /**
     * 获取存储状态
     *
     * @param id 设备id
     * @return 存储状态信息
     */
    @GetMapping("/getStorageState/{id}")
    public R<Map<String, Object>> getStorageState(@PathVariable Long id) {
        log.info("获取存储状态 - id: {}", id);
        return R.ok(onvifService.getStorageStateById(id));
    }

    /**
     * 获取网络接口配置
     *
     * @param id 设备id
     * @return 网络接口配置信息
     */
    @GetMapping("/getNetworkInterfaces/{id}")
    public R<Map<String, Object>> getNetworkInterfaces(@PathVariable Long id) {
        log.info("获取网络接口配置 - id: {}", id);
        return R.ok(onvifService.getNetworkInterfacesById(id));
    }

    /**
     * 获取网络协议配置
     *
     * @param id 设备id
     * @return 网络协议配置信息
     */
    @GetMapping("/getNetworkProtocols/{id}")
    public R<Map<String, Object>> getNetworkProtocols(@PathVariable Long id) {
        log.info("获取网络协议配置 - id: {}", id);
        return R.ok(onvifService.getNetworkProtocolsById(id));
    }

    /**
     * 获取视频源配置
     *
     * @param id 设备id
     * @return 视频源配置信息
     */
    @GetMapping("/getVideoSourceConfigs/{id}")
    public R<Map<String, Object>> getVideoSourceConfigs(@PathVariable Long id) {
        log.info("获取视频源配置 - id: {}", id);
        return R.ok(onvifService.getVideoSourceConfigsById(id));
    }

    /**
     * 获取视频编码器配置
     *
     * @param id 设备id
     * @return 视频编码器配置信息
     */
    @GetMapping("/getVideoEncoderConfigs/{id}")
    public R<Map<String, Object>> getVideoEncoderConfigs(@PathVariable Long id) {
        log.info("获取视频编码器配置 - id: {}", id);
        return R.ok(onvifService.getVideoEncoderConfigsById(id));
    }

    /**
     * 获取音频源配置
     *
     * @param id 设备id
     * @return 音频源配置信息
     */
    @GetMapping("/getAudioSourceConfigs/{id}")
    public R<Map<String, Object>> getAudioSourceConfigs(@PathVariable Long id) {
        log.info("获取音频源配置 - id: {}", id);
        return R.ok(onvifService.getAudioSourceConfigsById(id));
    }

    /**
     * 获取音频编码器配置
     *
     * @param id 设备id
     * @return 音频编码器配置信息
     */
    @GetMapping("/getAudioEncoderConfigs/{id}")
    public R<Map<String, Object>> getAudioEncoderConfigs(@PathVariable Long id) {
        log.info("获取音频编码器配置 - id: {}", id);
        return R.ok(onvifService.getAudioEncoderConfigsById(id));
    }

    /**
     * 获取视频输出配置
     *
     * @param id 设备id
     * @return 视频输出配置信息
     */
    @GetMapping("/getVideoOutputConfigs/{id}")
    public R<Map<String, Object>> getVideoOutputConfigs(@PathVariable Long id) {
        log.info("获取视频输出配置 - id: {}", id);
        return R.ok(onvifService.getVideoOutputConfigsById(id));
    }
}
