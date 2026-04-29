package com.ruoyi.onvif.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.onvif.api.domain.OnvifDevice;
import com.ruoyi.onvif.api.domain.WSOnvifDevice;
import com.ruoyi.onvif.api.factory.RemoteOnvifFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * onvif 服务
 *
 * @FileName RemoteOnvifService
 * @Description
 * @Author fengcheng
 * @date 2026-04-10
 **/
@FeignClient(contextId = "remoteOnvifService", value = ServiceNameConstants.ONVIF_SERVICE, fallbackFactory = RemoteOnvifFallbackFactory.class)
public interface RemoteOnvifService {

    /**
     * 验证登录onvif设备
     *
     * @param onvifDevice 设备信息
     * @param source      请求来源
     * @return
     */
    @PostMapping("/api/onvif/login")
    R<OnvifDevice> login(@RequestBody WSOnvifDevice onvifDevice, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 开始云台控制
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param direction 方向
     * @param speed 速度
     * @param source 请求来源
     * @return
     */
    @GetMapping("/api/onvif/startPtzControl/{deviceIp}")
    R<Void> startPtzControl(@PathVariable("deviceIp") String deviceIp,
                             @RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("direction") String direction,
                             @RequestParam(value = "speed", defaultValue = "50") Integer speed,
                             @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 停止云台控制
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param source 请求来源
     * @return
     */
    @GetMapping("/api/onvif/stopPtzControl/{deviceIp}")
    R<Void> stopPtzControl(@PathVariable("deviceIp") String deviceIp,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 获取预置点列表
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param source 请求来源
     * @return
     */
    @GetMapping("/api/onvif/getPresets/{deviceIp}")
    R<List<Map<String, Object>>> getPresets(@PathVariable("deviceIp") String deviceIp,
                                             @RequestParam("username") String username,
                                             @RequestParam("password") String password,
                                             @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 设置预置点
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param presetIndex 预置点索引
     * @param presetName 预置点名称
     * @param source 请求来源
     * @return
     */
    @GetMapping("/api/onvif/setPreset/{deviceIp}")
    R<Void> setPreset(@PathVariable("deviceIp") String deviceIp,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "presetIndex", required = false) Integer presetIndex,
                      @RequestParam(value = "presetName", required = false) String presetName,
                      @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 调用预置点
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param presetIndex 预置点索引
     * @param speed 速度
     * @param source 请求来源
     * @return
     */
    @GetMapping("/api/onvif/gotoPreset/{deviceIp}")
    R<Void> gotoPreset(@PathVariable("deviceIp") String deviceIp,
                       @RequestParam("username") String username,
                       @RequestParam("password") String password,
                       @RequestParam("presetIndex") Integer presetIndex,
                       @RequestParam(value = "speed", defaultValue = "50") Integer speed,
                       @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 删除预置点
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param presetIndex 预置点索引
     * @param source 请求来源
     * @return
     */
    @GetMapping("/api/onvif/removePreset/{deviceIp}")
    R<Void> removePreset(@PathVariable("deviceIp") String deviceIp,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("presetIndex") Integer presetIndex,
                        @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
