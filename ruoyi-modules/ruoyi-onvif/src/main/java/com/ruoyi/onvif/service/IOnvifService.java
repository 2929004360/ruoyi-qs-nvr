package com.ruoyi.onvif.service;

import com.ruoyi.onvif.api.domain.OnvifDevice;
import com.ruoyi.onvif.api.domain.WSOnvifDevice;
import com.ruoyi.onvif.domain.WSDiscoveryDevice;

import java.util.ArrayList;
import java.util.HashMap;
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
     * @param dateTime 要设置的时间，支持多种格式：
     *                 - null 或空字符串：使用服务器当前 UTC 时间
     *                 - ISO格式（包含"T"）：直接使用
     *                 - yyyy-MM-dd HH:mm:ss：本地时间，会自动转换为 UTC
     */
    void syncDeviceTime(String deviceIp, String username, String password, String dateTime);
    
    /**
     * ONVIF设备查询录像
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param startTime 开始时间，格式：yyyy-MM-dd HH:mm:ss
     * @param endTime 结束时间，格式：yyyy-MM-dd HH:mm:ss
     * @return 录像文件列表（包含回放地址）
     */
    ArrayList<HashMap<String, Object>> queryRecord(String deviceIp, String username, String password, String startTime, String endTime);
    
    /**
     * 获取回放地址
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param recordingToken 录制令牌
     * @param trackToken 轨道令牌
     * @return 回放地址
     */
    String getReplayUri(String deviceIp, String username, String password, String recordingToken, String trackToken);
    
    /**
     * 根据设备id重启设备
     *
     * @param id 设备id
     */
    void restartDeviceById(Long id);
    
    /**
     * 根据设备id校时
     *
     * @param id 设备id
     * @param dateTime 要设置的时间
     */
    void syncDeviceTimeById(Long id, String dateTime);

    /**
     * 根据设备id获取设备时间
     *
     * @param id 设备id
     * @return 设备时间信息
     */
    Map<String, Object> getDeviceTimeById(Long id);

    /**
     * 获取设备信息
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @return 设备信息
     */
    Map<String, Object> getDeviceInfo(String deviceIp, String username, String password);

    /**
     * 根据设备id获取设备信息
     *
     * @param id 设备id
     * @return 设备信息
     */
    Map<String, Object> getDeviceInfoById(Long id);

    /**
     * 抓图并保存
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @param channelId 通道ID
     * @param snapshotType 抓图类型
     * @return 抓图记录ID
     */
    Long captureAndSave(String deviceIp, String username, String password, Integer channelId, String snapshotType);

    /**
     * 根据设备id抓图并保存
     *
     * @param id 设备id
     * @param channelId 通道ID
     * @param snapshotType 抓图类型
     * @return 抓图记录ID
     */
    Long captureAndSaveById(Long id, Integer channelId, String snapshotType);

    /**
     * 获取存储配置
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @return 存储配置信息
     */
    Map<String, Object> getStorageConfigurations(String deviceIp, String username, String password);

    /**
     * 获取存储能力
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @return 存储能力信息
     */
    Map<String, Object> getStorageCapabilities(String deviceIp, String username, String password);

    /**
     * 获取存储状态
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @return 存储状态信息
     */
    Map<String, Object> getStorageState(String deviceIp, String username, String password);

    /**
     * 根据设备id获取存储配置
     *
     * @param id 设备id
     * @return 存储配置信息
     */
    Map<String, Object> getStorageConfigurationsById(Long id);

    /**
     * 根据设备id获取存储能力
     *
     * @param id 设备id
     * @return 存储能力信息
     */
    Map<String, Object> getStorageCapabilitiesById(Long id);

    /**
     * 根据设备id获取存储状态
     *
     * @param id 设备id
     * @return 存储状态信息
     */
    Map<String, Object> getStorageStateById(Long id);

    /**
     * 获取网络接口配置
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @return 网络接口配置信息
     */
    Map<String, Object> getNetworkInterfaces(String deviceIp, String username, String password);

    /**
     * 获取网络协议配置
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @return 网络协议配置信息
     */
    Map<String, Object> getNetworkProtocols(String deviceIp, String username, String password);

    /**
     * 获取视频源配置
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @return 视频源配置信息
     */
    Map<String, Object> getVideoSourceConfigs(String deviceIp, String username, String password);

    /**
     * 获取视频编码器配置
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @return 视频编码器配置信息
     */
    Map<String, Object> getVideoEncoderConfigs(String deviceIp, String username, String password);

    /**
     * 获取音频源配置
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @return 音频源配置信息
     */
    Map<String, Object> getAudioSourceConfigs(String deviceIp, String username, String password);

    /**
     * 获取音频编码器配置
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @return 音频编码器配置信息
     */
    Map<String, Object> getAudioEncoderConfigs(String deviceIp, String username, String password);

    /**
     * 获取视频输出配置
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @return 视频输出配置信息
     */
    Map<String, Object> getVideoOutputConfigs(String deviceIp, String username, String password);

    /**
     * 根据设备id获取网络接口配置
     *
     * @param id 设备id
     * @return 网络接口配置信息
     */
    Map<String, Object> getNetworkInterfacesById(Long id);

    /**
     * 根据设备id获取网络协议配置
     *
     * @param id 设备id
     * @return 网络协议配置信息
     */
    Map<String, Object> getNetworkProtocolsById(Long id);

    /**
     * 根据设备id获取视频源配置
     *
     * @param id 设备id
     * @return 视频源配置信息
     */
    Map<String, Object> getVideoSourceConfigsById(Long id);

    /**
     * 根据设备id获取视频编码器配置
     *
     * @param id 设备id
     * @return 视频编码器配置信息
     */
    Map<String, Object> getVideoEncoderConfigsById(Long id);

    /**
     * 根据设备id获取音频源配置
     *
     * @param id 设备id
     * @return 音频源配置信息
     */
    Map<String, Object> getAudioSourceConfigsById(Long id);

    /**
     * 根据设备id获取音频编码器配置
     *
     * @param id 设备id
     * @return 音频编码器配置信息
     */
    Map<String, Object> getAudioEncoderConfigsById(Long id);

    /**
     * 根据设备id获取视频输出配置
     *
     * @param id 设备id
     * @return 视频输出配置信息
     */
    Map<String, Object> getVideoOutputConfigsById(Long id);
}
