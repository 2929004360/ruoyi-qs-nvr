package com.ruoyi.gb28181.service;

import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.gb28181.api.bean.ErrorCallback;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.transmit.event.SipSubscribe;

import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import java.text.ParseException;

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
    void playStreamCmd(Device device,RtpServerParam rtpServer, SipSubscribe.Event okEvent, SipSubscribe.Event errorEvent, Long timeout) throws SipException, InvalidArgumentException, ParseException;
}
