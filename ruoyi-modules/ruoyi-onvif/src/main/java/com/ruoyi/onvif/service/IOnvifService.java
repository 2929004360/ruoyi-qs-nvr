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

    /**
     * 灯光控制
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param on true为开灯，false为关灯
     */
    void controlLight(String deviceIp, String username, String password, boolean on);

    /**
     * 雨刷控制
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param on true为开雨刷，false为关雨刷
     */
    void controlWiper(String deviceIp, String username, String password, boolean on);

    /**
     * 设备重启
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     */
    void restartDevice(String deviceIp, String username, String password);

    /**
     * 恢复出厂设置
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param factoryDefault 恢复模式："Full"为完全恢复，"Partial"为部分恢复
     */
    void factoryReset(String deviceIp, String username, String password, String factoryDefault);

    /**
     * 获取设备时间
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @return 设备时间信息
     */
    Map<String, Object> getDeviceTime(String deviceIp, String username, String password);

    /**
     * 设备校时
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param dateTime 要设置的时间，格式：yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     */
    void syncDeviceTime(String deviceIp, String username, String password, String dateTime);
}
