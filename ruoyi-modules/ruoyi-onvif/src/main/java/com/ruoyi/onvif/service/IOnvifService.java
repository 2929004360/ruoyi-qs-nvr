package com.ruoyi.onvif.service;

import com.ruoyi.onvif.api.domain.OnvifDevice;
import com.ruoyi.onvif.api.domain.WSOnvifDevice;
import com.ruoyi.onvif.domain.WSDiscoveryDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @FileName IOnvifService
 * @Description
 * @Author fengcheng
 * @date 2026-04-09
 **/
public interface IOnvifService {

    /**
     * 定时任务获取内网onvif设备
     */
    void task();

    /**
     * 验证登录onvif设备
     *
     * @param onvifDevice
     */
    OnvifDevice verifyOnvifDeviceLogin(WSOnvifDevice onvifDevice);

    /**
     * 获取onvif设备列表
     */
    ArrayList<WSDiscoveryDevice> getOnvifDeviceList();

    /**
     * 开始云台控制
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param direction 方向
     * @param speed 速度
     */
    void startPtzControl(String deviceIp, String username, String password, String direction, Integer speed);

    /**
     * 停止云台控制
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     */
    void stopPtzControl(String deviceIp, String username, String password);

    /**
     * 获取预置点列表
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @return 预置点列表
     */
    List<Map<String, Object>> getPresets(String deviceIp, String username, String password);

    /**
     * 设置预置点
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param presetIndex 预置点索引
     * @param presetName 预置点名称
     */
    void setPreset(String deviceIp, String username, String password, Integer presetIndex, String presetName);

    /**
     * 调用预置点
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param presetIndex 预置点索引
     * @param speed 速度
     */
    void gotoPreset(String deviceIp, String username, String password, Integer presetIndex, Integer speed);

    /**
     * 删除预置点
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param presetIndex 预置点索引
     */
    void removePreset(String deviceIp, String username, String password, Integer presetIndex);
}
