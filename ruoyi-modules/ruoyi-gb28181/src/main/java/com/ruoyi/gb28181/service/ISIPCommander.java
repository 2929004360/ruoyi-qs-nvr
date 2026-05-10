package com.ruoyi.gb28181.service;

import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.gb28181.api.bean.ErrorCallback;
import com.ruoyi.gb28181.api.bean.Preset;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.transmit.event.SipSubscribe;

import javax.sip.InvalidArgumentException;
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
     * 查询目录列表
     *
     * @param device 视频设备
     */
    void catalogQuery(Device device, int sn, ErrorCallback<String> callback) throws SipException, InvalidArgumentException, ParseException;

    /**
     * 查询设备状态
     *
     * @param device
     * @param callback
     */
    void deviceStatusQuery(Device device, ErrorCallback<String> callback) throws InvalidArgumentException, SipException, ParseException;

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

}
