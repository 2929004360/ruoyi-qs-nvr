package com.ruoyi.haikang.isup.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.isup.api.domain.HaiKangIsupDeviceInfo;
import com.ruoyi.haikang.isup.api.factory.RemoteHaiKangIsupFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 海康isup服务
 *
 * @FileName RemoteHaiKangIsupService
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
@FeignClient(contextId = "remoteHaiKangIsupService", value = ServiceNameConstants.HAIKANG_ISUP_SERVICE, fallbackFactory = RemoteHaiKangIsupFallbackFactory.class)
public interface RemoteHaiKangIsupService {

    /**
     * 获取设备登录的用户ID
     *
     * @param ip     设备ip
     * @param source 请求来源
     * @return
     */
    @PostMapping("/api/haikang/isup/getUserId/{ip}")
    R<Integer> getUserId(@PathVariable String ip, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 获取设备信息
     *
     * @param ip     设备ip
     * @param source 请求来源
     * @return
     */
    @PostMapping("/api/haikang/isup/getDevInfo/{ip}")
    R<HaiKangIsupDeviceInfo> getDevInfo(@PathVariable String ip, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 开始播放
     *
     * @param rtpServerParam 播放参数
     * @param inner          请求来源
     */
    @PostMapping("/api/haikang/isup/startPlay")
    public R<Void> startPlay(@RequestBody RtpServerParam rtpServerParam, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 停止播放
     *
     * @param id    失败id
     * @param inner 请求来源
     */
    @GetMapping("/api/haikang/isup/stopPlay/{id}")
    public R<Void> stopPlay(@PathVariable Long id, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 开始云台控制
     *
     * @param deviceId  设备ID
     * @param channelId 通道ID
     * @param PTZCmd    云台命令
     * @param speed     速度
     * @param inner     请求来源
     * @return
     */
    @GetMapping("/api/haikang/isup/startPtz/{deviceId}/{channelId}")
    R<Void> startPtz(@PathVariable("deviceId") Long deviceId,
                   @PathVariable("channelId") Integer channelId,
                   @RequestParam int PTZCmd,
                   @RequestParam int speed,
                   @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 结束云台控制
     *
     * @param deviceId  设备ID
     * @param channelId 通道ID
     * @param PTZCmd    云台命令
     * @param speed     速度
     * @param inner     请求来源
     * @return
     */
    @GetMapping("/api/haikang/isup/endPtz/{deviceId}/{channelId}")
    R<Void> endPtz(@PathVariable("deviceId") Long deviceId,
                 @PathVariable("channelId") Integer channelId,
                 @RequestParam int PTZCmd,
                 @RequestParam int speed,
                 @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 设置预置点
     *
     * @param deviceId    设备ID
     * @param channelId   通道ID
     * @param presetIndex 预置点编号
     * @param inner       请求来源
     * @return
     */
    @GetMapping("/api/haikang/isup/setPreset/{deviceId}/{channelId}")
    R<Void> setPreset(@PathVariable("deviceId") Long deviceId,
                     @PathVariable("channelId") Integer channelId,
                     @RequestParam int presetIndex,
                     @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 清除预置点
     *
     * @param deviceId    设备ID
     * @param channelId   通道ID
     * @param presetIndex 预置点编号
     * @param inner       请求来源
     * @return
     */
    @GetMapping("/api/haikang/isup/clearPreset/{deviceId}/{channelId}")
    R<Void> clearPreset(@PathVariable("deviceId") Long deviceId,
                       @PathVariable("channelId") Integer channelId,
                       @RequestParam int presetIndex,
                       @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 调用预置点
     *
     * @param deviceId    设备ID
     * @param channelId   通道ID
     * @param presetIndex 预置点编号
     * @param inner       请求来源
     * @return
     */
    @GetMapping("/api/haikang/isup/gotoPreset/{deviceId}/{channelId}")
    R<Void> gotoPreset(@PathVariable("deviceId") Long deviceId,
                        @PathVariable("channelId") Integer channelId,
                        @RequestParam int presetIndex,
                        @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 辅助设备控制（灯光、雨刮、风扇等）
     *
     * @param deviceId  设备ID
     * @param channelId 通道ID
     * @param operation 操作类型
     * @param isStart    是否开始
     * @param inner       请求来源
     * @return
     */
    @GetMapping("/api/haikang/isup/cameraAuxControl/{deviceId}/{channelId}")
    R<Void> cameraAuxControl(@PathVariable("deviceId") Long deviceId,
                            @PathVariable("channelId") Integer channelId,
                            @RequestParam String operation,
                            @RequestParam boolean isStart,
                            @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 巡航控制
     *
     * @param deviceId  设备ID
     * @param channelId 通道ID
     * @param operation 操作类型
     * @param param      参数
     * @param inner       请求来源
     * @return
     */
    @GetMapping("/api/haikang/isup/cruiseControl/{deviceId}/{channelId}")
    R<Void> cruiseControl(@PathVariable("deviceId") Long deviceId,
                           @PathVariable("channelId") Integer channelId,
                           @RequestParam String operation,
                           @RequestParam Integer param,
                           @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);
}
