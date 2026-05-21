package com.ruoyi.gb28181.service;

import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.gb28181.api.bean.ErrorCallback;
import com.ruoyi.gb28181.api.bean.Preset;
import com.ruoyi.gb28181.api.bean.SipTransactionInfo;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceConfig;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.gb28181.api.domain.DeviceStatus;
import com.ruoyi.gb28181.transmit.event.SipSubscribe;

import javax.sip.InvalidArgumentException;
import javax.sip.PeerUnavailableException;
import javax.sip.SipException;
import java.text.ParseException;
import java.util.List;

public interface ISIPCommander {

    /**
     * 查询设备信息
     *
     * @param device   视频设备
     * @param callback
     * @return
     */
    void deviceInfoQuery(Device device, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 查询设备配置
     *
     * @param device     视频设备
     * @param channelId  通道编码（可选）
     * @param configType 配置类型：
     */
    void deviceConfigQuery(Device device, String channelId, String configType, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 设备配置命令
     *
     * @param device     视频设备
     * @param channelId  通道编码（可选）
     * @param deviceConfig 设备配置
     * @param callback 回调
     */
    void deviceConfigCmd(Device device, String channelId, com.ruoyi.gb28181.api.domain.DeviceConfig deviceConfig, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 查询目录列表
     *
     * @param device    视频设备
     * @param sn        命令序列号
     * @param startTime 起始时间（可选）
     * @param endTime   结束时间（可选）
     * @param callback  回调
     */
    void catalogQuery(Device device, int sn, String startTime, String endTime, ErrorCallback<Object> callback) throws SipException, InvalidArgumentException, ParseException;

    /**
     * 目录订阅
     *
     * @param device            视频设备
     * @param sipTransactionInfo sip事务信息
     * @param expires           过期时间（秒）
     * @param okEvent           成功回调
     * @param errorEvent        失败回调
     */
    void catalogSubscribe(Device device, SipTransactionInfo sipTransactionInfo, Integer expires, SipSubscribe.Event okEvent, SipSubscribe.Event errorEvent) throws SipException, InvalidArgumentException, ParseException, PeerUnavailableException;

    /**
     * 查询设备状态
     *
     * @param device
     * @param callback
     */
    void deviceStatusQuery(Device device, ErrorCallback<DeviceStatus> callback) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 请求预览视频流
     *
     * @param device
     * @param rtpServer
     * @param okEvent
     * @param errorEvent
     * @param timeout
     */
    void playStreamCmd(Device device, RtpServerParam rtpServer, SipSubscribe.Event okEvent, SipSubscribe.Event errorEvent, Long timeout) throws SipException, InvalidArgumentException, ParseException;

    /**
     * 请求回放视频流
     *
     * @param device
     * @param rtpServer
     * @param okEvent
     * @param errorEvent
     * @param timeout
     */
    void playbackStreamCmd(Device device, RtpServerParam rtpServer, SipSubscribe.Event okEvent, SipSubscribe.Event errorEvent, Long timeout) throws SipException, InvalidArgumentException, ParseException;

    /**
     * 停止视频流
     *
     * @param device
     * @param rtpServer
     */
    void stopStreamCmd(Device device, RtpServerParam rtpServer) throws SipException, InvalidArgumentException, ParseException;

    /**
     * 通用前端控制命令(参考国标文档A.3.1指令格式)
     *
     * @param device       设备
     * @param channelId    通道国标编号
     * @param cmdCode      指令码(对应国标文档指令格式中的字节4)
     * @param parameter1   数据一(对应国标文档指令格式中的字节5, 范围0-255)
     * @param parameter2   数据二(对应国标文档指令格式中的字节6, 范围0-255)
     * @param combindCode2 组合码二(对应国标文档指令格式中的字节7, 范围0-15)
     */
    void frontEndCmd(Device device, String channelId, Integer cmdCode, Integer parameter1, Integer parameter2, Integer combindCode2) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 查询预置位
     *
     * @param device    设备国标编号
     * @param channelId 通道国标编号
     * @param callback
     */
    void presetQuery(Device device, String channelId, ErrorCallback<List<Preset>> callback) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 查询录像信息
     *
     * @param device     设备
     * @param channelId  通道id
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param sn         sn
     * @param secrecy
     * @param type
     * @param okEvent
     * @param errorEvent
     * @throws InvalidArgumentException
     * @throws SipException
     * @throws ParseException
     */
    void recordInfoQuery(Device device, String channelId, String startTime, String endTime, int sn, Integer secrecy, String type, SipSubscribe.Event okEvent, SipSubscribe.Event errorEvent) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 远程重启设备
     *
     * @param device  设备
     * @throws InvalidArgumentException
     * @throws SipException
     * @throws ParseException
     */
    void rebootDevice(Device device) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 录像控制
     *
     * @param device       设备
     * @param channelId    通道国标编号
     * @param recordCmd    录像命令：0-停止录像，1-开始录像，2-定时录像
     * @param streamNumber 码流类型：0-主码流，1-子码流1，2-子码流2，以此类推，缺省为0
     * @throws InvalidArgumentException
     * @throws SipException
     * @throws ParseException
     */
    void recordCmd(Device device, String channelId, String recordCmd, Integer streamNumber) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 看守位信息查询
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param callback   回调
     */
    void homePositionQuery(Device device, String channelId, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 看守位设置
     *
     * @param device       设备
     * @param channelId    通道国标编号（可选）
     * @param deviceConfig 看守位配置
     * @param callback     回调
     */
    void homePositionCmd(Device device, String channelId, com.ruoyi.gb28181.api.domain.DeviceConfig deviceConfig, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 巡航轨迹列表查询
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param callback   回调
     */
    void cruiseTrackListQuery(Device device, String channelId, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 巡航轨迹查询
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param number     轨迹编号：0-第一条轨迹，1-第二条轨迹
     * @param callback   回调
     */
    void cruiseTrackQuery(Device device, String channelId, Integer number, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException;

    /**
     * PTZ精准状态查询
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param callback   回调
     */
    void ptzPositionQuery(Device device, String channelId, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 存储卡状态查询
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param callback   回调
     */
    void sdCardStatusQuery(Device device, String channelId, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 报警复位控制
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param alarmMethod 报警方式（可选），0-全部，1-电话报警，2-设备报警，3-短信报警，4-GPS报警，5-视频报警，6-设备故障报警，7-其他报警
     * @param alarmType 报警类型（可选）
     * @param callback   回调
     */
    void alarmResetControl(Device device, String channelId, String alarmMethod, String alarmType, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 强制关键帧控制
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param callback   回调
     */
    void iFrameControl(Device device, String channelId, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 看守位控制
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param deviceConfig 设备配置，包含看守位配置
     * @param callback   回调
     */
    void homePositionControl(Device device, String channelId, DeviceConfig deviceConfig, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException;

    /**
     * PTZ精准控制
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param ptzPreciseCtrl PTZ精准控制参数
     * @param callback   回调
     */
    void ptzPreciseControl(Device device, String channelId, JSONObject ptzPreciseCtrl, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 设备软件升级控制
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param firmware   设备固件版本
     * @param fileURL    升级文件的完整路径
     * @param manufacturer 设备厂商
     * @param sessionID  会话ID
     * @param callback   回调
     */
    void deviceUpgradeControl(Device device, String channelId, String firmware, String fileURL, String manufacturer, String sessionID, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 存储卡格式化控制
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param sdCardId   SD卡编号（0表示所有存储卡）
     * @param callback   回调
     */
    void formatSDCardControl(Device device, String channelId, Integer sdCardId, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 目标跟踪控制
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选，指球机通道）
     * @param targetTrack 跟踪类型：Auto/Manual/Stop
     * @param deviceId2  目标设备编码（可选，指全景相机中的全景通道ID）
     * @param targetArea 目标区域（可选，手动跟踪时需要）
     * @param callback   回调
     */
    void targetTrackControl(Device device, String channelId, String targetTrack, String deviceId2, JSONObject targetArea, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 设备抓图控制
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param callback   回调
     */
    void pictureCaptureControl(Device device, String channelId, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException;

    /**
     * 设备校时控制
     *
     * @param device   设备
     * @param callback 回调
     */
    void timeCheckCmd(Device device, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException;

}
