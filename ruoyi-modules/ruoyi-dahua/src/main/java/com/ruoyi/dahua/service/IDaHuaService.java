package com.ruoyi.dahua.service;

import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.dahua.api.domain.DahuaDevice;
import com.ruoyi.dahua.api.domain.DahuaDeviceInfo;
import com.ruoyi.dahua.api.domain.DahuaSystemParam;
import com.ruoyi.dahua.api.domain.DahuaVideoParam;
import com.ruoyi.dahua.api.domain.DahuaDeviceVideoParam;
import com.ruoyi.dahua.lib.NetSDKLib;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 大华sdk 接口
 *
 * @FileName IDaHuaService
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
public interface IDaHuaService {

    /**
     * 大华设备登录
     *
     * @param m_strIp       设备ip
     * @param m_nPort       设备端口
     * @param m_strUser     设备用户名
     * @param m_strPassword 设备密码
     * @param deviceId      设备id
     * @param onlineType    上线类型(1=主动添加, 2=主动注册)
     */
    NetSDKLib.LLong loginDevice(String m_strIp, int m_nPort, String m_strUser, String m_strPassword, String deviceId, String onlineType);

    /**
     * 查询是否登录
     *
     * @param ip 设备ip
     */
    Boolean isUserId(String ip);

    /**
     * 大华设备获取时间
     *
     * @param ip 设备ip
     * @return
     */
    String getTime(String ip);

    /**
     * 获取大华主动上线设备
     *
     * @param ip 设备ip
     */
    DahuaDevice getDahuaDevice(String ip);

    /**
     * 大华设备登出
     *
     * @param ip 设备ip
     * @return
     */
    Boolean logoutDevice(String ip);

    /**
     * 开始播放
     *
     * @param rtpServerParam 播放参数
     */
    void startPlay(RtpServerParam rtpServerParam);

    /**
     * 停止播放
     *
     * @param id 设备id
     */
    void stopPlay(Long id);

    /**
     * 大华设备云台控制（开始）
     *
     * @param direction 方向
     * @param id        设备id
     * @param speed     速度
     * @param channelId 通道id
     * @return
     */
    boolean ptzControlStart(String direction, Long id, Integer speed, int channelId);

    /**
     * 大华设备云台控制（停止）
     *
     * @param direction 方向
     * @param id        设备id
     * @param channelId 通道id
     * @return
     */
    boolean ptzControlUpEnd(String direction, Long id, int channelId);

    /**
     * 大华设备获取预置点列表
     *
     * @param id
     * @param channelId
     */
    ArrayList<HashMap<String, Object>> getPresetList(Long id, int channelId);

    /**
     * 大华设备设置预置点
     *
     * @param id
     * @param channelId
     * @param presetIndex
     */
    void setPreset(Long id, int channelId, int presetIndex);

    /**
     * 大华删除设置预置点
     *
     * @param id
     * @param channelId
     * @param presetIndex
     * @return
     */
    void delPreset(Long id, int channelId, int presetIndex);

    /**
     * 大华调用设置预置点
     *
     * @param id
     * @param channelId
     * @param presetIndex
     * @return
     */
    void invokePreset(Long id, int channelId, int presetIndex);

    /**
     * 灯光控制
     *
     * @param id
     * @param channelId
     * @param action    0-关,1-开
     */
    void controlLight(Long id, int channelId, int action);

    /**
     * 雨刷控制
     *
     * @param id
     * @param channelId
     * @param action    0-关,1-开
     */
    void controlWiper(Long id, int channelId, int action);

    /**
     * 开始点间巡航
     *
     * @param id
     * @param channelId
     * @param tourIndex 巡航线路号
     */
    void startTour(Long id, int channelId, int tourIndex);

    /**
     * 停止点间巡航
     *
     * @param id
     * @param channelId
     */
    void stopTour(Long id, int channelId);

    /**
     * 添加预置点到巡航线路
     *
     * @param id
     * @param channelId
     * @param tourIndex   巡航线路号
     * @param presetIndex 预置点号
     */
    void addPresetToTour(Long id, int channelId, int tourIndex, int presetIndex);

    /**
     * 从巡航线路删除预置点
     *
     * @param id
     * @param channelId
     * @param tourIndex   巡航线路号
     * @param presetIndex 预置点号
     */
    void removePresetFromTour(Long id, int channelId, int tourIndex, int presetIndex);

    /**
     * 清除巡航线路
     *
     * @param id
     * @param channelId
     * @param tourIndex 巡航线路号
     */
    void clearTour(Long id, int channelId, int tourIndex);

    /**
     * 大华设备设置时间
     *
     * @param id   设备ID
     * @param date 日期时间字符串
     * @param type 类型
     * @return 是否成功
     */
    boolean setTime(Long id, String date, boolean type);

    /**
     * 大华设备重启
     *
     * @param id 设备ID
     * @return 是否成功
     */
    boolean reboot(Long id);

    /**
     * 大华设备查询录像
     *
     * @param id         设备id
     * @param channelId  通道id
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return
     */
    ArrayList<HashMap<String, Object>> queryRecord(Long id, int channelId, String startTime, String endTime);

    /**
     * 大华设备开始录像回放
     *
     * @param rtpServerParam 回放参数
     */
    void startPlayback(RtpServerParam rtpServerParam);

    /**
     * 大华设备停止录像回放
     *
     * @param id 设备id
     */
    void stopPlayback(Long id);

    /**
     * 获取大华设备详细信息
     *
     * @param id 设备ID
     * @return 设备详细信息
     */
    DahuaDeviceInfo getDeviceInfo(Long id);

    /**
     * 获取大华设备详细信息(通过IP)
     *
     * @param ip 设备IP
     * @return 设备详细信息
     */
    DahuaDeviceInfo getDeviceInfoByIp(String ip);

    /**
     * 获取大华设备系统参数
     *
     * @param id 设备ID
     * @return 系统参数
     */
    DahuaSystemParam getSystemParam(Long id);

    /**
     * 获取大华设备视频参数
     *
     * @param id        设备ID
     * @param channelId 通道ID
     * @param streamType 码流类型(0-主码流, 1-辅码流1, 2-辅码流2)
     * @return 视频参数
     */
    DahuaVideoParam getVideoParam(Long id, int channelId, int streamType);

    /**
     * 获取大华设备视频输入参数
     *
     * @param id        设备ID
     * @param channelId 通道ID
     * @return 视频输入参数
     */
    DahuaDeviceVideoParam getDeviceVideoParam(Long id, int channelId);

    /**
     * 设置大华设备视频参数
     *
     * @param id        设备ID
     * @param channelId 通道ID
     * @param streamType 码流类型 0-主码流 1-辅码流1 2-辅码流2
     * @param param     视频参数
     * @return 是否成功
     */
    boolean setVideoParam(Long id, int channelId, int streamType, DahuaVideoParam param);

    /**
     * 设置大华设备视频输入参数
     *
     * @param id        设备ID
     * @param channelId 通道ID
     * @param param     视频输入参数
     * @return 是否成功
     */
    boolean setDeviceVideoParam(Long id, int channelId, DahuaDeviceVideoParam param);
}
