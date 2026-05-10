package com.ruoyi.haikang.service;

import com.ruoyi.haikang.api.domain.HaikangDeviceInfo;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.api.domain.PresetInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @FileName IHaikangService
 * @Description
 * @Author fengcheng
 * @date 2026-03-27
 **/
public interface IHaiKangService {

    /**
     * 登录设备，支持 V40 和 V30 版本，功能一致。
     *
     * @param ip   设备IP地址
     * @param port SDK端口，默认为设备的8000端口
     * @param user 设备用户名
     * @param psw  设备密码
     * @return 登录成功返回用户ID，失败返回-1
     */
    public int loginDevice(String ip, short port, String user, String psw);

    /**
     * 设备注销
     *
     * @param ip 设备ip
     */
    public void logoutDevice(String ip);

    /**
     * 获取设备登录的用户ID
     *
     * @param ip 设备ip
     * @return
     */
    public Integer getUserId(String ip);

    /**
     * 获取设备的基本参数
     *
     * @param ip 设备ip
     * @return
     */
    HaikangDeviceInfo getDeviceInfo(String ip);

    /**
     * 开始播放
     *
     * @param rtpServerParam
     */
    void startPlay(RtpServerParam rtpServerParam);

    /**
     * 停止播放
     *
     * @param id 设备id
     */
    void stopPlay(Long id);

    /**
     * 开始回放
     *
     * @param rtpServerParam
     */
    void startPlayback(RtpServerParam rtpServerParam);

    /**
     * 停止回放
     *
     * @param id 设备id
     */
    void stopPlayback(Long id);

    /**
     * 开始云台控制
     *
     * @param deviceId 设备id
     * @param channelId 通道id
     * @param direction 方向
     */
    void startPlayControl(Long deviceId, int channelId, String direction);

    /**
     * 结束云台控制
     *
     * @param deviceId 设备id
     * @param channelId 通道id
     * @param direction 方向
     */
    void endPlayControl(Long deviceId, int channelId, String direction);

    /**
     * 重启设备
     *
     * @param deviceId
     */
    void restartDevice(Long deviceId);

    /**
     * 关机
     *
     * @param deviceId
     */
    void shutDown(Long deviceId);

    /**
     * 获取设备音频编码参数，确定转发音频参数
     *
     * @param deviceId
     * @param channelId
     * @return
     */
    HashMap<String, Object> getCurrentAudio(Long deviceId, int channelId);

    /**
     * 获取预置点列表
     *
     * @param deviceId
     * @param channelId
     * @return
     */
    List<PresetInfo> getPresets(Long deviceId, int channelId);

    /**
     * 设置预置点
     *
     * @param deviceId
     * @param channelId
     * @param presetIndex
     * @return
     */
    void setPresets(Long deviceId, int channelId, int presetIndex);

    /**
     * 清除预置点
     *
     * @param deviceId
     * @param channelId
     * @param presetIndex
     * @return
     */
    void delPresets(Long deviceId, int channelId, int presetIndex);

    /**
     * 调用预置点
     *
     * @param deviceId
     * @param channelId
     * @param presetIndex
     * @return
     */
    void invokePresets(Long deviceId, int channelId, int presetIndex);

    /**
     * 辅助设备控制（灯光、雨刷、风扇、加热器等）
     *
     * @param deviceId 设备ID
     * @param channelId 通道ID
     * @param operation 操作类型
     * @param isStart 是否开始（true开始，false停止）
     */
    void cameraAuxControl(Long deviceId, int channelId, String operation, boolean isStart);

    /**
     * 巡航控制
     *
     * @param deviceId 设备ID
     * @param channelId 通道ID
     * @param operation 操作类型
     * @param param 参数（预置点号、停顿时间、速度等，根据操作类型不同而不同）
     */
    void cruiseControl(Long deviceId, int channelId, String operation, Integer param);

    /**
     * 获取球机PTZ参数
     *
     * @param deviceId 设备ID
     * @param channelId 通道ID
     * @return PTZ配置
     */
    HashMap<String, Object> getPTZcfg(Long deviceId, int channelId);

    /**
     * 设置球机PTZ参数
     *
     * @param deviceId 设备ID
     * @param channelId 通道ID
     * @param p 云台水平角度
     * @param t 云台垂直角度
     * @param z 云台变倍倍数
     */
    void setPTZcfg(Long deviceId, int channelId, short p, short t, short z);

    /**
     * 获取高精度PTZ绝对位置配置
     *
     * @param deviceId 设备ID
     * @param channelId 通道ID
     * @return PTZ绝对位置配置
     */
    HashMap<String, Object> getPTZAbsoluteEx(Long deviceId, int channelId);

    /**
     * 获取设备时间参数
     *
     * @param deviceId 设备ID
     * @return 设备时间
     */
    String getDevTime(Long deviceId);

    /**
     * 设置设备时间参数
     *
     * @param deviceId 设备ID
     * @param time 时间
     */
    void setDevTime(Long deviceId, String time);

    /**
     * 海康设备查询录像
     *
     * @param deviceId  设备id
     * @param channelId 通道id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    ArrayList<HashMap<String, Object>> queryRecord(Long deviceId, int channelId, String startTime, String endTime);
}
