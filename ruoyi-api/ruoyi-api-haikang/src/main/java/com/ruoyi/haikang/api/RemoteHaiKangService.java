package com.ruoyi.haikang.api;


import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.haikang.api.domain.HaikangDeviceInfo;
import com.ruoyi.haikang.api.domain.LoginDevice;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.api.domain.PresetInfo;
import com.ruoyi.haikang.api.factory.RemoteHaiKangFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 海康sdk 服务
 *
 * @FileName RemoteHaiKangService
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@FeignClient(contextId = "remoteHaiKangService", value = ServiceNameConstants.HAIKANG_SERVICE, fallbackFactory = RemoteHaiKangFallbackFactory.class)
public interface RemoteHaiKangService {

    /**
     * 登录设备，支持 V40 和 V30 版本，功能一致。
     *
     * @param loginDevice 海康设备登录信息
     * @param source      请求来源
     * @return 登录成功返回用户ID，失败返回-1
     */
    @PostMapping(value = "/api/haikang/loginDevice")
    public R<Integer> loginDevice(@RequestBody LoginDevice loginDevice, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 设备注销
     *
     * @param ip     设备ip
     * @param source 请求来源
     */
    @PostMapping(value = "/api/haikang/logoutDevice/{ip}")
    public R<Void> logoutDevice(@PathVariable String ip, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 获取设备登录的用户ID
     *
     * @param ip     设备ip
     * @param source 请求来源
     * @return
     */
    @PostMapping("/api/haikang/getUserId/{ip}")
    public R<Integer> getUserId(@PathVariable String ip, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


    /**
     * 获取设备的基本参数
     *
     * @param ip     设备ip
     * @param source 请求来源
     */
    @PostMapping("/api/haikang/getDeviceInfo/{ip}")
    R<HaikangDeviceInfo> getDeviceInfo(@PathVariable String ip, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 开始播放
     *
     * @param rtpServerParam 播放参数
     * @param source         请求来源
     */
    @PostMapping("/api/haikang/startPlay")
    public R<Void> startPlay(@RequestBody RtpServerParam rtpServerParam, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 停止播放
     *
     * @param id    设备id
     * @param inner 请求来源
     */
    @GetMapping("/api/haikang/stopPlay/{id}")
    public R<Void> stopPlay(@PathVariable Long id, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 开始云台控制
     *
     * @param deviceId  设备id
     * @param channelId 通道id
     * @param direction 方向
     * @param source    请求来源
     */
    @GetMapping("/api/haikang/startPlayControl/{deviceId}/{channelId}")
    R<Void> startPlayControl(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, @RequestParam String direction, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 结束云台控制
     *
     * @param deviceId  设备id
     * @param channelId 通道id
     * @param direction 方向
     * @param source    请求来源
     */
    @GetMapping("/api/haikang/endPlayControl/{deviceId}/{channelId}")
    R<Void> endPlayControl(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, @RequestParam String direction, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 重启设备
     *
     * @param deviceId 设备id
     * @param source   请求来源
     */
    @GetMapping("/api/haikang/restartDevice/{deviceId}")
    R<Void> restartDevice(@PathVariable("deviceId") Long deviceId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 关机
     *
     * @param deviceId 设备id
     * @param source   请求来源
     */
    @GetMapping("/api/haikang/shutDown/{deviceId}")
    R<Void> shutDown(@PathVariable("deviceId") Long deviceId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 获取设备音频编码参数，确定转发音频参数
     *
     * @param deviceId  设备id
     * @param channelId 通道id
     * @param source    请求来源
     */
    @GetMapping("/api/haikang/getCurrentAudio/{deviceId}/{channelId}")
    R<HashMap<String, Object>> getCurrentAudio(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 获取预置点列表
     *
     * @param deviceId  设备id
     * @param channelId 通道id
     * @param source    请求来源
     */
    @GetMapping("/api/haikang/getPresets/{deviceId}/{channelId}")
    R<List<PresetInfo>> getPresets(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 设置预置点
     *
     * @param deviceId    设备id
     * @param channelId   通道id
     * @param presetIndex 预置点索引
     * @param source      请求来源
     */
    @GetMapping("/api/haikang/setPresets/{deviceId}/{channelId}")
    R<Void> setPresets(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, @RequestParam int presetIndex, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 清除预置点
     *
     * @param deviceId    设备id
     * @param channelId   通道id
     * @param presetIndex 预置点索引
     * @param source      请求来源
     */
    @GetMapping("/api/haikang/delPresets/{deviceId}/{channelId}")
    R<Void> delPresets(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, @RequestParam int presetIndex, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 调用预置点
     *
     * @param deviceId    设备id
     * @param channelId 通道id
     * @param presetIndex 预置点索引
     * @param source      请求来源
     */
    @GetMapping("/api/haikang/invokePresets/{deviceId}/{channelId}")
    R<Void> invokePresets(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, @RequestParam int presetIndex, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 辅助设备控制（灯光、雨刷、风扇、加热器等）
     *
     * @param deviceId 设备id
     * @param channelId 通道id
     * @param operation 操作类型
     * @param isStart 是否开始（true开始，false停止）
     * @param source 请求来源
     */
    @GetMapping("/api/haikang/cameraAuxControl/{deviceId}/{channelId}")
    R<Void> cameraAuxControl(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, @RequestParam String operation, @RequestParam boolean isStart, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 巡航控制
     *
     * @param deviceId 设备id
     * @param channelId 通道id
     * @param operation 操作类型
     * @param param 参数（预置点号、停顿时间、速度等，根据操作类型不同而不同）
     * @param source 请求来源
     */
    @GetMapping("/api/haikang/cruiseControl/{deviceId}/{channelId}")
    R<Void> cruiseControl(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, @RequestParam String operation, @RequestParam Integer param, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 获取球机PTZ参数
     *
     * @param deviceId 设备id
     * @param channelId 通道id
     * @param source 请求来源
     */
    @GetMapping("/api/haikang/getPTZcfg/{deviceId}/{channelId}")
    R<HashMap<String, Object>> getPTZcfg(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 设置球机PTZ参数
     *
     * @param deviceId 设备id
     * @param channelId 通道id
     * @param p 云台水平角度
     * @param t 云台垂直角度
     * @param z 云台变倍倍数
     * @param source 请求来源
     */
    @GetMapping("/api/haikang/setPTZcfg/{deviceId}/{channelId}")
    R<Void> setPTZcfg(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, @RequestParam short p, @RequestParam short t, @RequestParam short z, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 获取高精度PTZ绝对位置配置
     *
     * @param deviceId 设备id
     * @param channelId 通道id
     * @param source 请求来源
     */
    @GetMapping("/api/haikang/getPTZAbsoluteEx/{deviceId}/{channelId}")
    R<HashMap<String, Object>> getPTZAbsoluteEx(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 获取设备时间参数
     *
     * @param deviceId 设备id
     * @param source 请求来源
     */
    @GetMapping("/api/haikang/getDevTime/{deviceId}")
    R<String> getDevTime(@PathVariable("deviceId") Long deviceId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 设置设备时间参数
     *
     * @param deviceId 设备id
     * @param time 时间
     * @param source 请求来源
     */
    @GetMapping("/api/haikang/setDevTime/{deviceId}")
    R<Void> setDevTime(@PathVariable("deviceId") Long deviceId, @RequestParam String time, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 海康设备查询录像
     *
     * @param deviceId 设备id
     * @param channelId 通道id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param source 请求来源
     */
    @GetMapping("/api/haikang/getRecMonth/{deviceId}/{channelId}")
    R<ArrayList<HashMap<String, Object>>> getRecMonth(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, @RequestParam String startTime, @RequestParam String endTime, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
