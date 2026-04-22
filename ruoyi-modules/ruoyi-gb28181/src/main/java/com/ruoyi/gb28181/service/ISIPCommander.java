package com.ruoyi.gb28181.service;

import com.ruoyi.gb28181.bean.ErrorCallback;
import com.ruoyi.gb28181.domain.Device;

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
}
