package com.ruoyi.gb28181.service;

import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import com.ruoyi.qs.api.domain.SimpleDeviceInfo;

import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import javax.sip.message.Response;
import java.text.ParseException;
import java.util.List;

/**
 * 平台级联SIP命令服务接口
 *
 * @author ruoyi
 */
public interface IPlatformSIPCommander {

    /**
     * 注册到上级平台
     */
    void register(Gb28181Platform platform) throws SipException, InvalidArgumentException, ParseException;

    /**
     * 注销
     */
    void unregister(Gb28181Platform platform) throws SipException, InvalidArgumentException, ParseException;

    /**
     * 发送心跳
     */
    void keepAlive(Gb28181Platform platform) throws SipException, InvalidArgumentException, ParseException;

    /**
     * 发送设备信息
     */
    void sendDeviceInfo(Gb28181Platform platform) throws SipException, InvalidArgumentException, ParseException;

    /**
     * 发送设备信息（带SN）
     */
    void sendDeviceInfo(Gb28181Platform platform, int sn) throws SipException, InvalidArgumentException, ParseException;

    /**
     * 发送目录（设备列表）
     */
    void sendCatalog(Gb28181Platform platform, List<SimpleDeviceInfo> deviceList) throws SipException, InvalidArgumentException, ParseException;

    /**
     * 发送目录（设备列表）（带SN）
     */
    void sendCatalog(Gb28181Platform platform, List<SimpleDeviceInfo> deviceList, int sn) throws SipException, InvalidArgumentException, ParseException;

    /**
     * 处理REGISTER响应
     */
    void handleRegisterResponse(Response response, String localIp);
}
