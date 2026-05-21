package com.ruoyi.gb28181.service.impl;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.gb28181.api.bean.ErrorCallback;
import com.ruoyi.gb28181.api.bean.Preset;
import com.ruoyi.gb28181.api.bean.SipTransactionInfo;
import com.ruoyi.gb28181.api.common.InviteSessionType;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceConfig;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.gb28181.api.domain.DeviceStatus;
import com.ruoyi.gb28181.api.domain.SsrcTransaction;
import com.ruoyi.gb28181.api.utils.DateUtil;
import com.ruoyi.gb28181.api.utils.SipUtils;
import com.ruoyi.gb28181.common.ErrorCode;
import com.ruoyi.gb28181.config.SipConfig;
import com.ruoyi.gb28181.config.UserSetting;
import com.ruoyi.gb28181.runner.SipLayer;
import com.ruoyi.gb28181.service.ISIPCommander;
import com.ruoyi.gb28181.session.SipInviteSessionManager;
import com.ruoyi.gb28181.transmit.SIPSender;
import com.ruoyi.gb28181.transmit.cmd.SIPRequestHeaderProvider;
import com.ruoyi.gb28181.transmit.event.MessageSubscribe;
import com.ruoyi.gb28181.transmit.event.SipSubscribe;
import com.ruoyi.gb28181.transmit.event.sip.MessageEvent;
import com.ruoyi.zlm.api.RemoteZlmService;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.message.SIPResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.sip.InvalidArgumentException;
import javax.sip.PeerUnavailableException;
import javax.sip.ResponseEvent;
import javax.sip.SipException;
import javax.sip.header.CallIdHeader;
import javax.sip.message.Request;
import java.text.ParseException;
import java.util.List;

/**
 * @description:设备能力接口，用于定义设备的控制、查询能力
 * @author: swwheihei
 * @date: 2020年5月3日 下午9:22:48
 */
@Component
@DependsOn("sipLayer")
@Slf4j
public class SIPCommander implements ISIPCommander {

    @Autowired
    private SipConfig sipConfig;

    @Autowired
    private SipLayer sipLayer;

    @Autowired
    private SIPSender sipSender;

    @Autowired
    private SIPRequestHeaderProvider headerProvider;

    @Autowired
    private MessageSubscribe messageSubscribe;

    @Autowired
    private UserSetting userSetting;

    @Autowired
    private RemoteZlmService remoteZlmService;


    @Autowired
    private SipInviteSessionManager sessionManager;

    /**
     * 查询设备信息
     *
     * @param device   视频设备
     * @param callback
     * @return
     */
    @Override
    public void deviceInfoQuery(Device device, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "DeviceInfo";
        String sn = (int) ((Math.random() * 9 + 1) * 100000) + "";

        StringBuffer catalogXml = new StringBuffer(200);
        String charset = device.getCharset();
        catalogXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        catalogXml.append("<Query>\r\n");
        catalogXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        catalogXml.append("<SN>" + sn + "</SN>\r\n");
        catalogXml.append("<DeviceID>" + device.getDeviceId() + "</DeviceID>\r\n");
        catalogXml.append("</Query>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn, device.getDeviceId(), 4000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[设备信息查询] 设备编号： {}， SN： {}", device.getDeviceId(), sn);

        Request request = headerProvider.createMessageRequest(device, catalogXml.toString(), SipUtils.getNewViaTag(), SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));

        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

    /**
     * 查询设备配置
     *
     * @param device     视频设备
     * @param channelId  通道编码（可选）
     * @param configType 配置类型：
     */
    @Override
    public void deviceConfigQuery(Device device, String channelId, String configType, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "ConfigDownload";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        StringBuffer cmdXml = new StringBuffer(200);
        String charset = device.getCharset();
        String targetId = ObjectUtils.isEmpty(channelId) ? device.getDeviceId() : channelId;
        cmdXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        cmdXml.append("<Query>\r\n");
        cmdXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        cmdXml.append("<SN>" + sn + "</SN>\r\n");
        cmdXml.append("<DeviceID>" + targetId + "</DeviceID>\r\n");
        cmdXml.append("<ConfigType>" + configType + "</ConfigType>\r\n");
        cmdXml.append("</Query>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn + "", targetId, 10000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[设备配置查询] 设备编号: {}, SN: {}, 配置类型: {}, 目标ID: {}", device.getDeviceId(), sn, configType, targetId);

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

    /**
     * 设备配置命令
     *
     * @param device     视频设备
     * @param channelId  通道编码（可选）
     * @param deviceConfig 设备配置
     * @param callback 回调
     */
    @Override
    public void deviceConfigCmd(Device device, String channelId, com.ruoyi.gb28181.api.domain.DeviceConfig deviceConfig, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "DeviceConfig";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        StringBuffer cmdXml = new StringBuffer(500);
        String charset = device.getCharset();
        String targetId = ObjectUtils.isEmpty(channelId) ? device.getDeviceId() : channelId;
        
        cmdXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        cmdXml.append("<Control>\r\n");
        cmdXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        cmdXml.append("<SN>" + sn + "</SN>\r\n");
        cmdXml.append("<DeviceID>" + targetId + "</DeviceID>\r\n");
        
        // 添加各种配置类型
        if (deviceConfig.getBasicParam() != null) {
            appendJsonObjectToXml(cmdXml, "BasicParam", deviceConfig.getBasicParam());
        }
        if (deviceConfig.getVideoParamOpt() != null) {
            appendJsonObjectToXml(cmdXml, "VideoParamOpt", deviceConfig.getVideoParamOpt());
        }
        if (deviceConfig.getSvacEncodeConfig() != null) {
            appendJsonObjectToXml(cmdXml, "SVACEncodeConfig", deviceConfig.getSvacEncodeConfig());
        }
        if (deviceConfig.getSvacDecodeConfig() != null) {
            appendJsonObjectToXml(cmdXml, "SVACDecodeConfig", deviceConfig.getSvacDecodeConfig());
        }
        if (deviceConfig.getVideoParamAttribute() != null) {
            appendJsonObjectToXml(cmdXml, "VideoParamAttribute", deviceConfig.getVideoParamAttribute());
        }
        if (deviceConfig.getVideoRecordPlan() != null) {
            appendJsonObjectToXml(cmdXml, "VideoRecordPlan", deviceConfig.getVideoRecordPlan());
        }
        if (deviceConfig.getVideoAlarmRecord() != null) {
            appendJsonObjectToXml(cmdXml, "VideoAlarmRecord", deviceConfig.getVideoAlarmRecord());
        }
        if (deviceConfig.getPictureMask() != null) {
            appendJsonObjectToXml(cmdXml, "PictureMask", deviceConfig.getPictureMask());
        }
        if (deviceConfig.getFrameMirror() != null) {
            appendJsonObjectToXml(cmdXml, "FrameMirror", deviceConfig.getFrameMirror());
        }
        if (deviceConfig.getAlarmReport() != null) {
            appendJsonObjectToXml(cmdXml, "AlarmReport", deviceConfig.getAlarmReport());
        }
        if (deviceConfig.getOsdConfig() != null) {
            appendJsonObjectToXml(cmdXml, "OSDConfig", deviceConfig.getOsdConfig());
        }
        if (deviceConfig.getSnapshot() != null) {
            appendJsonObjectToXml(cmdXml, "Snapshot", deviceConfig.getSnapshot());
        }
        
        cmdXml.append("</Control>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn + "", targetId, 10000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[设备配置命令] 设备编号: {}, SN: {}, 目标ID: {}", device.getDeviceId(), sn, targetId);

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }
    
    /**
     * 将 JSONObject 转换为 XML 格式并追加到 StringBuffer 中
     */
    private void appendJsonObjectToXml(StringBuffer sb, String tagName, com.alibaba.fastjson2.JSONObject jsonObj) {
        sb.append("<").append(tagName).append(">\r\n");
        for (String key : jsonObj.keySet()) {
            Object value = jsonObj.get(key);
            if (value instanceof com.alibaba.fastjson2.JSONObject) {
                appendJsonObjectToXml(sb, key, (com.alibaba.fastjson2.JSONObject) value);
            } else {
                sb.append("<").append(key).append(">").append(value != null ? value.toString() : "").append("</").append(key).append(">\r\n");
            }
        }
        sb.append("</").append(tagName).append(">\r\n");
    }

    /**
     * 看守位信息查询
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param callback   回调
     */
    @Override
    public void homePositionQuery(Device device, String channelId, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "HomePositionQuery";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        StringBuffer cmdXml = new StringBuffer(200);
        String charset = device.getCharset();
        String targetId = ObjectUtils.isEmpty(channelId) ? device.getDeviceId() : channelId;
        cmdXml.append("<?xml version=\"1.0\" encoding=\"").append(charset).append("\"?>\r\n");
        cmdXml.append("<Query>\r\n");
        cmdXml.append("<CmdType>").append(cmdType).append("</CmdType>\r\n");
        cmdXml.append("<SN>").append(sn).append("</SN>\r\n");
        cmdXml.append("<DeviceID>").append(targetId).append("</DeviceID>\r\n");
        cmdXml.append("</Query>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn + "", targetId, 10000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[看守位查询] 设备编号: {}, SN: {}, 目标ID: {}", device.getDeviceId(), sn, targetId);

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

    /**
     * 看守位设置
     *
     * @param device       设备
     * @param channelId    通道国标编号（可选）
     * @param deviceConfig 看守位配置
     * @param callback     回调
     */
    @Override
    public void homePositionCmd(Device device, String channelId, com.ruoyi.gb28181.api.domain.DeviceConfig deviceConfig, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "HomePosition";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        StringBuffer cmdXml = new StringBuffer(500);
        String charset = device.getCharset();
        String targetId = ObjectUtils.isEmpty(channelId) ? device.getDeviceId() : channelId;
        
        cmdXml.append("<?xml version=\"1.0\" encoding=\"").append(charset).append("\"?>\r\n");
        cmdXml.append("<Control>\r\n");
        cmdXml.append("<CmdType>").append(cmdType).append("</CmdType>\r\n");
        cmdXml.append("<SN>").append(sn).append("</SN>\r\n");
        cmdXml.append("<DeviceID>").append(targetId).append("</DeviceID>\r\n");
        
        // 添加看守位配置
        if (deviceConfig.getHomePosition() != null) {
            appendJsonObjectToXml(cmdXml, "HomePosition", deviceConfig.getHomePosition());
        }
        
        cmdXml.append("</Control>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn + "", targetId, 10000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[看守位设置] 设备编号: {}, SN: {}, 目标ID: {}", device.getDeviceId(), sn, targetId);

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

    @Override
    public void catalogQuery(Device device, int sn, String startTime, String endTime, ErrorCallback<Object> callback) throws SipException, InvalidArgumentException, ParseException {
        String cmdType = "Catalog";

        StringBuffer catalogXml = new StringBuffer(200);
        String charset = device.getCharset();
        catalogXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        catalogXml.append("<Query>\r\n");
        catalogXml.append("  <CmdType>" + cmdType + "</CmdType>\r\n");
        catalogXml.append("  <SN>" + sn + "</SN>\r\n");
        catalogXml.append("  <DeviceID>" + device.getDeviceId() + "</DeviceID>\r\n");
        if (startTime != null && !startTime.isEmpty()) {
            catalogXml.append("  <StartTime>" + DateUtil.yyyy_MM_dd_HH_mm_ssToISO8601(startTime) + "</StartTime>\r\n");
        }
        if (endTime != null && !endTime.isEmpty()) {
            catalogXml.append("  <EndTime>" + DateUtil.yyyy_MM_dd_HH_mm_ssToISO8601(endTime) + "</EndTime>\r\n");
        }
        catalogXml.append("</Query>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn + "", device.getDeviceId(), 4000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[目录查询] 设备编号: {}, SN: {}, startTime: {}, endTime: {}", device.getDeviceId(), sn, startTime, endTime);

        Request request = headerProvider.createMessageRequest(device, catalogXml.toString(), SipUtils.getNewViaTag(), SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));

        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

    @Override
    public void catalogSubscribe(Device device, SipTransactionInfo sipTransactionInfo, Integer expires, SipSubscribe.Event okEvent, SipSubscribe.Event errorEvent) throws SipException, InvalidArgumentException, ParseException, PeerUnavailableException {
        // 构建目录订阅XML
        StringBuffer subscribeXml = new StringBuffer(200);
        String charset = device.getCharset();
        subscribeXml.append("<?xml version=\"1.0\" encoding=\"").append(charset).append("\"?>\r\n");
        subscribeXml.append("<Query>\r\n");
        subscribeXml.append("<CmdType>Catalog</CmdType>\r\n");
        subscribeXml.append("<SN>").append((int) ((Math.random() * 9 + 1) * 100000)).append("</SN>\r\n");
        subscribeXml.append("<DeviceID>").append(device.getDeviceId()).append("</DeviceID>\r\n");
        subscribeXml.append("</Query>\r\n");

        log.info("[目录订阅] 设备编号: {}, 过期时间: {}秒", device.getDeviceId(), expires);

        // 创建SUBSCRIBE请求
        CallIdHeader callIdHeader = sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport());
        Request request = headerProvider.createSubscribeRequest(
            device, 
            subscribeXml.toString(), 
            sipTransactionInfo, 
            expires, 
            "Catalog", 
            callIdHeader
        );

        // 发送请求
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, errorEvent, okEvent);
    }

    /**
     * 查询设备状态
     *
     * @param device
     * @param callback
     */
    @Override
    public void deviceStatusQuery(Device device, ErrorCallback<DeviceStatus> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "DeviceStatus";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);

        String charset = device.getCharset();
        StringBuffer catalogXml = new StringBuffer(200);
        catalogXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        catalogXml.append("<Query>\r\n");
        catalogXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        catalogXml.append("<SN>" + sn + "</SN>\r\n");
        catalogXml.append("<DeviceID>" + device.getDeviceId() + "</DeviceID>\r\n");
        catalogXml.append("</Query>\r\n");

        MessageEvent<DeviceStatus> messageEvent = MessageEvent.getInstance(cmdType, sn + "", device.getDeviceId(), 4000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[设备状态查询] 设备编号： {}， SN： {}", device.getDeviceId(), sn);

        Request request = headerProvider.createMessageRequest(device, catalogXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));

        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
        });
    }

    /**
     * 请求预览视频流
     *
     * @param device
     * @param rtpServer
     * @param okEvent
     * @param errorEvent
     * @param timeout
     */
    @Override
    public void playStreamCmd(Device device, RtpServerParam rtpServer, SipSubscribe.Event okEvent, SipSubscribe.Event errorEvent, Long timeout) throws SipException, InvalidArgumentException, ParseException {
        String sdpIp = rtpServer.getIp();

        StringBuffer content = new StringBuffer(200);
        content.append("v=0\r\n");
        content.append("o=" + rtpServer.getGbDeviceId() + " 0 0 IN IP4 " + sdpIp + "\r\n");
        content.append("s=Play\r\n");
        content.append("c=IN IP4 " + sdpIp + "\r\n");
        content.append("t=0 0\r\n");

        if (userSetting.getSeniorSdp()) {
            if ("TCP-PASSIVE".equalsIgnoreCase(device.getStreamMode())) {
                content.append("m=video " + rtpServer.getPort() + " TCP/RTP/AVP 96 126 125 99 34 98 97\r\n");
            } else if ("TCP-ACTIVE".equalsIgnoreCase(device.getStreamMode())) {
                content.append("m=video " + rtpServer.getPort() + " TCP/RTP/AVP 96 126 125 99 34 98 97\r\n");
            } else if ("UDP".equalsIgnoreCase(device.getStreamMode())) {
                content.append("m=video " + rtpServer.getPort() + " RTP/AVP 96 126 125 99 34 98 97\r\n");
            }
            content.append("a=recvonly\r\n");
            content.append("a=rtpmap:96 PS/90000\r\n");
            content.append("a=fmtp:126 profile-level-id=42e01e\r\n");
            content.append("a=rtpmap:126 H264/90000\r\n");
            content.append("a=rtpmap:125 H264S/90000\r\n");
            content.append("a=fmtp:125 profile-level-id=42e01e\r\n");
            content.append("a=rtpmap:99 H265/90000\r\n");
            content.append("a=rtpmap:98 H264/90000\r\n");
            content.append("a=rtpmap:97 MPEG4/90000\r\n");
            if ("TCP-PASSIVE".equalsIgnoreCase(device.getStreamMode())) { // tcp被动模式
                content.append("a=setup:passive\r\n");
                content.append("a=connection:new\r\n");
            } else if ("TCP-ACTIVE".equalsIgnoreCase(device.getStreamMode())) { // tcp主动模式
                content.append("a=setup:active\r\n");
                content.append("a=connection:new\r\n");
            }
        } else {
            if ("TCP-PASSIVE".equalsIgnoreCase(device.getStreamMode())) {
                content.append("m=video " + rtpServer.getPort() + " TCP/RTP/AVP 96 97 98 99\r\n");
            } else if ("TCP-ACTIVE".equalsIgnoreCase(device.getStreamMode())) {
                content.append("m=video " + rtpServer.getPort() + " TCP/RTP/AVP 96 97 98 99\r\n");
            } else if ("UDP".equalsIgnoreCase(device.getStreamMode())) {
                content.append("m=video " + rtpServer.getPort() + " RTP/AVP 96 97 98 99\r\n");
            }
            content.append("a=recvonly\r\n");
            content.append("a=rtpmap:96 PS/90000\r\n");
            content.append("a=rtpmap:98 H264/90000\r\n");
            content.append("a=rtpmap:97 MPEG4/90000\r\n");
            content.append("a=rtpmap:99 H265/90000\r\n");
            if ("TCP-PASSIVE".equalsIgnoreCase(device.getStreamMode())) { // tcp被动模式
                content.append("a=setup:passive\r\n");
                content.append("a=connection:new\r\n");
            } else if ("TCP-ACTIVE".equalsIgnoreCase(device.getStreamMode())) { // tcp主动模式
                content.append("a=setup:active\r\n");
                content.append("a=connection:new\r\n");
            }
        }

//        if (!ObjectUtils.isEmpty(channel.getStreamIdentification())) {
//            content.append("a=" + channel.getStreamIdentification() + "\r\n");
//        }

        content.append("y=" + rtpServer.getSsrc() + "\r\n");//ssrc
        // f字段:f= v/编码格式/分辨率/帧率/码率类型/码率大小a/编码格式/码率大小/采样率
//			content.append("f= v/2/5/25/1/4000a/1/8/1" + "\r\n"); // 未发现支持此特性的设备


        Request request = headerProvider.createInviteRequest(device, rtpServer.getGbChannelId(), content.toString(), SipUtils.getNewViaTag(), SipUtils.getNewFromTag(), null, rtpServer.getSsrc(), sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, (e -> {
            sessionManager.removeByStream(rtpServer.getApp(), rtpServer.getStream());
            remoteZlmService.releaseSsrc(rtpServer.getMediaServerId(), rtpServer.getSsrc(), SecurityConstants.INNER);
            remoteZlmService.closeRTPServer(rtpServer.getMediaServerId(), rtpServer, SecurityConstants.INNER);
            errorEvent.response(e);
        }), e -> {
            ResponseEvent responseEvent = (ResponseEvent) e.event;
            SIPResponse response = (SIPResponse) responseEvent.getResponse();
            String callId = response.getCallIdHeader().getCallId();
            SsrcTransaction ssrcTransaction = SsrcTransaction.buildForDevice(device.getDeviceId(), rtpServer.getGbChannelId(),
                    callId, rtpServer.getApp(), rtpServer.getStream(), rtpServer.getSsrc(), rtpServer.getMediaServerId(), response,
                    InviteSessionType.PLAY);
            ssrcTransaction.setApp(rtpServer.getApp());
            ssrcTransaction.setStream(rtpServer.getStream());
            sessionManager.put(ssrcTransaction);
            okEvent.response(e);
        }, timeout);
    }

    @Override
    public void playbackStreamCmd(Device device, RtpServerParam rtpServer, SipSubscribe.Event okEvent, SipSubscribe.Event errorEvent, Long timeout) throws SipException, InvalidArgumentException, ParseException {
        log.info("{} 分配的ZLM为: {} [{}:{}]", rtpServer.getStream(), rtpServer.getMediaServerId(), rtpServer.getIp(), rtpServer.getPort());
        String sdpIp;
        if (!ObjectUtils.isEmpty(device.getSdpIp())) {
            sdpIp = device.getSdpIp();
        }else {
            sdpIp = rtpServer.getIp();
        }
        StringBuffer content = new StringBuffer(200);
        content.append("v=0\r\n");
        content.append("o=" + device.getDeviceId() + " 0 0 IN IP4 " + sdpIp + "\r\n");
        content.append("s=Playback\r\n");
        content.append("u=" + rtpServer.getGbChannelId() + ":0\r\n");
        content.append("c=IN IP4 " + sdpIp + "\r\n");
        
        // 添加回放时间范围，将时间字符串转换为时间戳
        if (rtpServer.getStartTime() != null && rtpServer.getEndTime() != null) {
            long startTimeTimestamp = DateUtil.yyyy_MM_dd_HH_mm_ssToTimestamp(rtpServer.getStartTime());
            long endTimeTimestamp = DateUtil.yyyy_MM_dd_HH_mm_ssToTimestamp(rtpServer.getEndTime());
            content.append("t=" + startTimeTimestamp + " " + endTimeTimestamp + "\r\n");
        } else {
            content.append("t=0 0\r\n");
        }

        String streamMode = device.getStreamMode();

        if (userSetting.getSeniorSdp()) {
            if ("TCP-PASSIVE".equalsIgnoreCase(streamMode)) {
                content.append("m=video " + rtpServer.getPort() + " TCP/RTP/AVP 96 126 125 99 34 98 97\r\n");
            } else if ("TCP-ACTIVE".equalsIgnoreCase(streamMode)) {
                content.append("m=video " + rtpServer.getPort() + " TCP/RTP/AVP 96 126 125 99 34 98 97\r\n");
            } else if ("UDP".equalsIgnoreCase(streamMode)) {
                content.append("m=video " + rtpServer.getPort() + " RTP/AVP 96 126 125 99 34 98 97\r\n");
            }
            content.append("a=recvonly\r\n");
            content.append("a=rtpmap:96 PS/90000\r\n");
            content.append("a=fmtp:126 profile-level-id=42e01e\r\n");
            content.append("a=rtpmap:126 H264/90000\r\n");
            content.append("a=rtpmap:125 H264S/90000\r\n");
            content.append("a=fmtp:125 profile-level-id=42e01e\r\n");
            content.append("a=rtpmap:99 H265/90000\r\n");
            content.append("a=rtpmap:98 H264/90000\r\n");
            content.append("a=rtpmap:97 MPEG4/90000\r\n");
            if ("TCP-PASSIVE".equalsIgnoreCase(streamMode)) { // tcp被动模式
                content.append("a=setup:passive\r\n");
                content.append("a=connection:new\r\n");
            } else if ("TCP-ACTIVE".equalsIgnoreCase(streamMode)) { // tcp主动模式
                content.append("a=setup:active\r\n");
                content.append("a=connection:new\r\n");
            }
        } else {
            if ("TCP-PASSIVE".equalsIgnoreCase(streamMode)) {
                content.append("m=video " + rtpServer.getPort() + " TCP/RTP/AVP 96 97 98 99\r\n");
            } else if ("TCP-ACTIVE".equalsIgnoreCase(streamMode)) {
                content.append("m=video " + rtpServer.getPort() + " TCP/RTP/AVP 96 97 98 99\r\n");
            } else if ("UDP".equalsIgnoreCase(streamMode)) {
                content.append("m=video " + rtpServer.getPort() + " RTP/AVP 96 97 98 99\r\n");
            }
            content.append("a=recvonly\r\n");
            content.append("a=rtpmap:96 PS/90000\r\n");
            content.append("a=rtpmap:97 MPEG4/90000\r\n");
            content.append("a=rtpmap:98 H264/90000\r\n");
            content.append("a=rtpmap:99 H265/90000\r\n");
            if ("TCP-PASSIVE".equalsIgnoreCase(streamMode)) {
                // tcp被动模式
                content.append("a=setup:passive\r\n");
                content.append("a=connection:new\r\n");
            } else if ("TCP-ACTIVE".equalsIgnoreCase(streamMode)) {
                // tcp主动模式
                content.append("a=setup:active\r\n");
                content.append("a=connection:new\r\n");
            }
        }

        //ssrc
        content.append("y=" + rtpServer.getSsrc() + "\r\n");

        CallIdHeader callIdHeader = sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport());
        Request request = headerProvider.createPlaybackInviteRequest(device, rtpServer.getGbChannelId(), content.toString(), SipUtils.getNewViaTag(), SipUtils.getNewFromTag(), null, callIdHeader, rtpServer.getSsrc());

        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, errorEvent, event -> {
            ResponseEvent responseEvent = (ResponseEvent) event.event;
            SIPResponse response = (SIPResponse) responseEvent.getResponse();
            SsrcTransaction ssrcTransaction = SsrcTransaction.buildForDevice(device.getDeviceId(),
                    rtpServer.getGbChannelId(), callIdHeader.getCallId(), rtpServer.getApp(), rtpServer.getStream(), rtpServer.getSsrc(),
                    rtpServer.getMediaServerId(), response, InviteSessionType.PLAYBACK);
            sessionManager.put(ssrcTransaction);
            okEvent.response(event);
        }, timeout);
    }

    @Override
    public void stopStreamCmd(Device device, RtpServerParam rtpServer) throws SipException, InvalidArgumentException, ParseException {
        SsrcTransaction ssrcTransaction = sessionManager.getSsrcTransactionByStream(rtpServer.getApp(), rtpServer.getStream());

        if (ssrcTransaction != null) {
            log.info("[停止播放] 发送 BYE 请求 deviceId: {}, channelId: {}",
                    rtpServer.getGbDeviceId(),
                    rtpServer.getGbChannelId());
            Request byeRequest = headerProvider.createByteRequest(
                    device,
                    ssrcTransaction.getChannelId(),
                    ssrcTransaction.getSipTransactionInfo()
            );
            sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), byeRequest);

            sessionManager.removeByCallId(ssrcTransaction.getCallId());
            remoteZlmService.releaseSsrc(ssrcTransaction.getMediaServerId(), ssrcTransaction.getSsrc(), SecurityConstants.INNER);
            remoteZlmService.closeRTPServer(ssrcTransaction.getMediaServerId(), rtpServer, SecurityConstants.INNER);
        } else {
            log.warn("[停止播放] 未找到会话信息 app: {}, stream: {}",
                    rtpServer.getApp(), rtpServer.getStream());
        }
    }

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
    @Override
    public void frontEndCmd(Device device, String channelId, Integer cmdCode, Integer parameter1, Integer parameter2, Integer combindCode2) throws InvalidArgumentException, SipException, ParseException {
        String cmdStr = frontEndCmdString(cmdCode, parameter1, parameter2, combindCode2);
        StringBuffer ptzXml = new StringBuffer(200);
        String charset = device.getCharset();
        ptzXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        ptzXml.append("<Control>\r\n");
        ptzXml.append("<CmdType>DeviceControl</CmdType>\r\n");
        ptzXml.append("<SN>" + (int) ((Math.random() * 9 + 1) * 100000) + "</SN>\r\n");
        ptzXml.append("<DeviceID>" + channelId + "</DeviceID>\r\n");
        ptzXml.append("<PTZCmd>" + cmdStr + "</PTZCmd>\r\n");
        ptzXml.append("<Info>\r\n");
        ptzXml.append("<ControlPriority>5</ControlPriority>\r\n");
        ptzXml.append("</Info>\r\n");
        ptzXml.append("</Control>\r\n");

        SIPRequest request = (SIPRequest) headerProvider.createMessageRequest(device, ptzXml.toString(), SipUtils.getNewViaTag(), SipUtils.getNewFromTag(), null,sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()),device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()),request);
    }

    /**
     * 查询预置位
     *
     * @param device    设备国标编号
     * @param channelId 通道国标编号
     * @param callback
     */
    @Override
    public void presetQuery(Device device, String channelId, ErrorCallback<List<Preset>> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "PresetQuery";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);

        StringBuffer cmdXml = new StringBuffer(200);
        String charset = device.getCharset();
        cmdXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        cmdXml.append("<Query>\r\n");
        cmdXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        cmdXml.append("<SN>" + sn + "</SN>\r\n");
        if (ObjectUtils.isEmpty(channelId)) {
            cmdXml.append("<DeviceID>" + device.getDeviceId() + "</DeviceID>\r\n");
        } else {
            cmdXml.append("<DeviceID>" + channelId + "</DeviceID>\r\n");
        }
        cmdXml.append("</Query>\r\n");

        MessageEvent<List<Preset>> messageEvent = MessageEvent.getInstance(cmdType, sn + "", channelId, 4000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[预置位查询] 设备编号： {}， 通道编号： {}， SN： {}", device.getDeviceId(), channelId, sn);
        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), SipUtils.getNewViaTag(), SipUtils.getNewFromTag(), null,sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()),device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
        });
    }

    /**
     * 云台指令码计算
     *
     * @param cmdCode      指令码
     * @param parameter1   数据1
     * @param parameter2   数据2
     * @param combineCode2 组合码2
     */
    public static String frontEndCmdString(int cmdCode, int parameter1, int parameter2, int combineCode2) {
        StringBuilder builder = new StringBuilder("A50F01");
        String strTmp;
        strTmp = String.format("%02X", cmdCode);
        builder.append(strTmp, 0, 2);
        strTmp = String.format("%02X", parameter1);
        builder.append(strTmp, 0, 2);
        strTmp = String.format("%02X", parameter2);
        builder.append(strTmp, 0, 2);
        strTmp = String.format("%02X", combineCode2 << 4);
        builder.append(strTmp, 0, 2);
        //计算校验码
        int checkCode = (0XA5 + 0X0F + 0X01 + cmdCode + parameter1 + parameter2 + (combineCode2 << 4)) % 0X100;
        strTmp = String.format("%02X", checkCode);
        builder.append(strTmp, 0, 2);
        return builder.toString();
    }

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
    @Override
    public void recordInfoQuery(Device device, String channelId, String startTime, String endTime, int sn, Integer secrecy, String type, SipSubscribe.Event okEvent, SipSubscribe.Event errorEvent) throws InvalidArgumentException, SipException, ParseException {
        if (secrecy == null) {
            secrecy = 0;
        }
        if (type == null) {
            type = "all";
        }

        StringBuffer recordInfoXml = new StringBuffer(200);
        String charset = device.getCharset();
        recordInfoXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        recordInfoXml.append("<Query>\r\n");
        recordInfoXml.append("<CmdType>RecordInfo</CmdType>\r\n");
        recordInfoXml.append("<SN>" + sn + "</SN>\r\n");
        recordInfoXml.append("<DeviceID>" + channelId + "</DeviceID>\r\n");
        if (startTime != null) {
            recordInfoXml.append("<StartTime>" + DateUtil.yyyy_MM_dd_HH_mm_ssToISO8601(startTime) + "</StartTime>\r\n");
        }
        if (endTime != null) {
            recordInfoXml.append("<EndTime>" + DateUtil.yyyy_MM_dd_HH_mm_ssToISO8601(endTime) + "</EndTime>\r\n");
        }
        if (secrecy != null) {
            recordInfoXml.append("<Secrecy> " + secrecy + " </Secrecy>\r\n");
        }
        if (type != null) {
            // 大华NVR要求必须增加一个值为all的文本元素节点Type
            recordInfoXml.append("<Type>" + type + "</Type>\r\n");
        }
        recordInfoXml.append("</Query>\r\n");



        Request request = headerProvider.createMessageRequest(device, recordInfoXml.toString(),
                SipUtils.getNewViaTag(), SipUtils.getNewFromTag(), null,sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()),device.getTransport()));

        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, errorEvent, okEvent);
    }

    /**
     * 远程重启设备
     *
     * @param device  设备
     * @throws InvalidArgumentException
     * @throws SipException
     * @throws ParseException
     */
    @Override
    public void rebootDevice(Device device) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "DeviceControl";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);

        String charset = device.getCharset();
        StringBuffer cmdXml = new StringBuffer(200);
        cmdXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        cmdXml.append("<Control>\r\n");
        cmdXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        cmdXml.append("<SN>" + sn + "</SN>\r\n");
        cmdXml.append("<DeviceID>" + device.getDeviceId() + "</DeviceID>\r\n");
        cmdXml.append("<TeleBoot>Boot</TeleBoot>\r\n");
        cmdXml.append("<Info>\r\n");
        cmdXml.append("<ControlPriority>5</ControlPriority>\r\n");
        cmdXml.append("</Info>\r\n");
        cmdXml.append("</Control>\r\n");

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request);
        log.info("[远程重启设备] 设备编号: {}, SN: {}", device.getDeviceId(), sn);
    }

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
    @Override
    public void recordCmd(Device device, String channelId, String recordCmd, Integer streamNumber) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "DeviceControl";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);

        String charset = device.getCharset();
        StringBuffer cmdXml = new StringBuffer(200);
        cmdXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        cmdXml.append("<Control>\r\n");
        cmdXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        cmdXml.append("<SN>" + sn + "</SN>\r\n");
        cmdXml.append("<DeviceID>" + channelId + "</DeviceID>\r\n");
        cmdXml.append("<RecordCmd>" + recordCmd + "</RecordCmd>\r\n");
        if (streamNumber != null) {
            cmdXml.append("<StreamNumber>" + streamNumber + "</StreamNumber>\r\n");
        }
        cmdXml.append("<Info>\r\n");
        cmdXml.append("<ControlPriority>5</ControlPriority>\r\n");
        cmdXml.append("</Info>\r\n");
        cmdXml.append("</Control>\r\n");

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request);
        log.info("[录像控制] 设备编号: {}, 通道编号: {}, 录像命令: {}, 码流类型: {}, SN: {}", device.getDeviceId(), channelId, recordCmd, streamNumber, sn);
    }

    /**
     * 巡航轨迹列表查询
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param callback   回调
     */
    @Override
    public void cruiseTrackListQuery(Device device, String channelId, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "CruiseTrackListQuery";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        StringBuffer cmdXml = new StringBuffer(200);
        String charset = device.getCharset();
        String targetId = ObjectUtils.isEmpty(channelId) ? device.getDeviceId() : channelId;
        cmdXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        cmdXml.append("<Query>\r\n");
        cmdXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        cmdXml.append("<SN>" + sn + "</SN>\r\n");
        cmdXml.append("<DeviceID>" + targetId + "</DeviceID>\r\n");
        cmdXml.append("</Query>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn + "", targetId, 10000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[巡航轨迹列表查询] 设备编号: {}, SN: {}, 目标ID: {}", device.getDeviceId(), sn, targetId);

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

    /**
     * 巡航轨迹查询
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param number     轨迹编号：0-第一条轨迹，1-第二条轨迹
     * @param callback   回调
     */
    @Override
    public void cruiseTrackQuery(Device device, String channelId, Integer number, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "CruiseTrackQuery";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        StringBuffer cmdXml = new StringBuffer(200);
        String charset = device.getCharset();
        String targetId = ObjectUtils.isEmpty(channelId) ? device.getDeviceId() : channelId;
        cmdXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        cmdXml.append("<Query>\r\n");
        cmdXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        cmdXml.append("<SN>" + sn + "</SN>\r\n");
        cmdXml.append("<DeviceID>" + targetId + "</DeviceID>\r\n");
        cmdXml.append("<Number>" + number + "</Number>\r\n");
        cmdXml.append("</Query>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn + "", targetId, 10000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[巡航轨迹查询] 设备编号: {}, SN: {}, 目标ID: {}, 轨迹编号: {}", device.getDeviceId(), sn, targetId, number);

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

    /**
     * PTZ精准状态查询
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param callback   回调
     */
    @Override
    public void ptzPositionQuery(Device device, String channelId, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "PTZPosition";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        StringBuffer cmdXml = new StringBuffer(200);
        String charset = device.getCharset();
        String targetId = ObjectUtils.isEmpty(channelId) ? device.getDeviceId() : channelId;
        cmdXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        cmdXml.append("<Query>\r\n");
        cmdXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        cmdXml.append("<SN>" + sn + "</SN>\r\n");
        cmdXml.append("<DeviceID>" + targetId + "</DeviceID>\r\n");
        cmdXml.append("</Query>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn + "", targetId, 10000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[PTZ精准状态查询] 设备编号: {}, SN: {}, 目标ID: {}", device.getDeviceId(), sn, targetId);

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

    /**
     * 存储卡状态查询
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param callback   回调
     */
    @Override
    public void sdCardStatusQuery(Device device, String channelId, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "SDCardStatus";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        StringBuffer cmdXml = new StringBuffer(200);
        String charset = device.getCharset();
        String targetId = ObjectUtils.isEmpty(channelId) ? device.getDeviceId() : channelId;
        cmdXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        cmdXml.append("<Query>\r\n");
        cmdXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        cmdXml.append("<SN>" + sn + "</SN>\r\n");
        cmdXml.append("<DeviceID>" + targetId + "</DeviceID>\r\n");
        cmdXml.append("</Query>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn + "", targetId, 10000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[存储卡状态查询] 设备编号: {}, SN: {}, 目标ID: {}", device.getDeviceId(), sn, targetId);

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

    /**
     * 报警复位控制
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param alarmMethod  报警方式（可选），0-全部，1-电话报警，2-设备报警，3-短信报警，4-GPS报警，5-视频报警，6-设备故障报警，7-其他报警
     * @param alarmType  报警类型（可选）
     * @param callback   回调
     */
    @Override
    public void alarmResetControl(Device device, String channelId, String alarmMethod, String alarmType, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "DeviceControl";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        StringBuffer cmdXml = new StringBuffer(400);
        String charset = device.getCharset();
        String targetId = ObjectUtils.isEmpty(channelId) ? device.getDeviceId() : channelId;
        cmdXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        cmdXml.append("<Control>\r\n");
        cmdXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        cmdXml.append("<SN>" + sn + "</SN>\r\n");
        cmdXml.append("<DeviceID>" + targetId + "</DeviceID>\r\n");
        cmdXml.append("<AlarmCmd>ResetAlarm</AlarmCmd>\r\n");
        if (!ObjectUtils.isEmpty(alarmMethod) || !ObjectUtils.isEmpty(alarmType)) {
            cmdXml.append("<Info>\r\n");
            if (!ObjectUtils.isEmpty(alarmMethod)) {
                cmdXml.append("<AlarmMethod>" + alarmMethod + "</AlarmMethod>\r\n");
            }
            if (!ObjectUtils.isEmpty(alarmType)) {
                cmdXml.append("<AlarmType>" + alarmType + "</AlarmType>\r\n");
            }
            cmdXml.append("</Info>\r\n");
        }
        cmdXml.append("</Control>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn + "", targetId, 10000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[报警复位控制] 设备编号: {}, SN: {}, 目标 ID: {}, 报警方式: {}, 报警类型: {}", device.getDeviceId(), sn, targetId, alarmMethod, alarmType);

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

    /**
     * 强制关键帧控制
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param callback   回调
     */
    @Override
    public void iFrameControl(Device device, String channelId, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "DeviceControl";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        StringBuffer cmdXml = new StringBuffer(400);
        String charset = device.getCharset();
        String targetId = ObjectUtils.isEmpty(channelId) ? device.getDeviceId() : channelId;
        cmdXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        cmdXml.append("<Control>\r\n");
        cmdXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        cmdXml.append("<SN>" + sn + "</SN>\r\n");
        cmdXml.append("<DeviceID>" + targetId + "</DeviceID>\r\n");
        cmdXml.append("<IFrameCmd>Send</IFrameCmd>\r\n");
        cmdXml.append("</Control>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn + "", targetId, 10000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[强制关键帧控制] 设备编号: {}, SN: {}, 目标 ID: {}", device.getDeviceId(), sn, targetId);

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

    /**
     * 看守位控制
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param deviceConfig 设备配置，包含看守位配置
     * @param callback   回调
     */
    @Override
    public void homePositionControl(Device device, String channelId, DeviceConfig deviceConfig, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "DeviceControl";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        StringBuffer cmdXml = new StringBuffer(400);
        String charset = device.getCharset();
        String targetId = ObjectUtils.isEmpty(channelId) ? device.getDeviceId() : channelId;
        cmdXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        cmdXml.append("<Control>\r\n");
        cmdXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        cmdXml.append("<SN>" + sn + "</SN>\r\n");
        cmdXml.append("<DeviceID>" + targetId + "</DeviceID>\r\n");
        if (deviceConfig != null && deviceConfig.getHomePosition() != null) {
            JSONObject homePosition = deviceConfig.getHomePosition();
            cmdXml.append("<HomePosition>\r\n");
            if (homePosition.containsKey("Enabled")) {
                cmdXml.append("<Enabled>" + homePosition.getInteger("Enabled") + "</Enabled>\r\n");
            }
            if (homePosition.containsKey("ResetTime")) {
                cmdXml.append("<ResetTime>" + homePosition.getInteger("ResetTime") + "</ResetTime>\r\n");
            }
            if (homePosition.containsKey("PresetIndex")) {
                cmdXml.append("<PresetIndex>" + homePosition.getInteger("PresetIndex") + "</PresetIndex>\r\n");
            }
            cmdXml.append("</HomePosition>\r\n");
        }
        cmdXml.append("</Control>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn + "", targetId, 10000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[看守位控制] 设备编号: {}, SN: {}, 目标 ID: {}, 配置: {}", device.getDeviceId(), sn, targetId, deviceConfig != null ? deviceConfig.getHomePosition() : null);

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

    /**
     * PTZ精准控制
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param ptzPreciseCtrl PTZ精准控制参数
     * @param callback   回调
     */
    @Override
    public void ptzPreciseControl(Device device, String channelId, JSONObject ptzPreciseCtrl, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "DeviceControl";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        StringBuffer cmdXml = new StringBuffer(400);
        String charset = device.getCharset();
        String targetId = ObjectUtils.isEmpty(channelId) ? device.getDeviceId() : channelId;
        cmdXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        cmdXml.append("<Control>\r\n");
        cmdXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        cmdXml.append("<SN>" + sn + "</SN>\r\n");
        cmdXml.append("<DeviceID>" + targetId + "</DeviceID>\r\n");
        if (ptzPreciseCtrl != null) {
            cmdXml.append("<PTZPreciseCtrl>\r\n");
            if (ptzPreciseCtrl.containsKey("Pan")) {
                cmdXml.append("<Pan>" + ptzPreciseCtrl.getDouble("Pan") + "</Pan>\r\n");
            }
            if (ptzPreciseCtrl.containsKey("Tilt")) {
                cmdXml.append("<Tilt>" + ptzPreciseCtrl.getDouble("Tilt") + "</Tilt>\r\n");
            }
            if (ptzPreciseCtrl.containsKey("Zoom")) {
                cmdXml.append("<Zoom>" + ptzPreciseCtrl.getDouble("Zoom") + "</Zoom>\r\n");
            }
            cmdXml.append("</PTZPreciseCtrl>\r\n");
        }
        cmdXml.append("</Control>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn + "", targetId, 10000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[PTZ精准控制] 设备编号: {}, SN: {}, 目标 ID: {}, 参数: {}", device.getDeviceId(), sn, targetId, ptzPreciseCtrl);

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

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
    @Override
    public void deviceUpgradeControl(Device device, String channelId, String firmware, String fileURL, String manufacturer, String sessionID, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "DeviceControl";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        StringBuffer cmdXml = new StringBuffer(400);
        String charset = device.getCharset();
        String targetId = ObjectUtils.isEmpty(channelId) ? device.getDeviceId() : channelId;
        cmdXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        cmdXml.append("<Control>\r\n");
        cmdXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        cmdXml.append("<SN>" + sn + "</SN>\r\n");
        cmdXml.append("<DeviceID>" + targetId + "</DeviceID>\r\n");
        cmdXml.append("<DeviceUpgrade>\r\n");
        cmdXml.append("<Firmware>" + firmware + "</Firmware>\r\n");
        cmdXml.append("<FileURL>" + fileURL + "</FileURL>\r\n");
        cmdXml.append("<Manufacturer>" + manufacturer + "</Manufacturer>\r\n");
        cmdXml.append("<SessionID>" + sessionID + "</SessionID>\r\n");
        cmdXml.append("</DeviceUpgrade>\r\n");
        cmdXml.append("</Control>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn + "", targetId, 10000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[设备软件升级控制] 设备编号: {}, SN: {}, 目标 ID: {}, 固件版本: {}, 文件URL: {}, 厂商: {}, 会话ID: {}", 
            device.getDeviceId(), sn, targetId, firmware, fileURL, manufacturer, sessionID);

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

    /**
     * 存储卡格式化控制
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param sdCardId   SD卡编号（0表示所有存储卡）
     * @param callback   回调
     */
    @Override
    public void formatSDCardControl(Device device, String channelId, Integer sdCardId, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "DeviceControl";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        StringBuffer cmdXml = new StringBuffer(400);
        String charset = device.getCharset();
        String targetId = ObjectUtils.isEmpty(channelId) ? device.getDeviceId() : channelId;
        cmdXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        cmdXml.append("<Control>\r\n");
        cmdXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        cmdXml.append("<SN>" + sn + "</SN>\r\n");
        cmdXml.append("<DeviceID>" + targetId + "</DeviceID>\r\n");
        cmdXml.append("<FormatSDCard>" + sdCardId + "</FormatSDCard>\r\n");
        cmdXml.append("</Control>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn + "", targetId, 10 * 1000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[存储卡格式化控制] 设备编号: {}, SN: {}, 目标 ID: {}, SD卡编号: {}", 
            device.getDeviceId(), sn, targetId, sdCardId);

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

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
    @Override
    public void targetTrackControl(Device device, String channelId, String targetTrack, String deviceId2, JSONObject targetArea, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "DeviceControl";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        StringBuffer cmdXml = new StringBuffer(500);
        String charset = device.getCharset();
        String targetId = ObjectUtils.isEmpty(channelId) ? device.getDeviceId() : channelId;
        cmdXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        cmdXml.append("<Control>\r\n");
        cmdXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        cmdXml.append("<SN>" + sn + "</SN>\r\n");
        cmdXml.append("<DeviceID>" + targetId + "</DeviceID>\r\n");
        cmdXml.append("<TargetTrack>" + targetTrack + "</TargetTrack>\r\n");
        if (!ObjectUtils.isEmpty(deviceId2)) {
            cmdXml.append("<DeviceID2>" + deviceId2 + "</DeviceID2>\r\n");
        }
        if (targetArea != null) {
            cmdXml.append("<TargetArea>\r\n");
            if (targetArea.containsKey("Length")) {
                cmdXml.append("<Length>" + targetArea.getInteger("Length") + "</Length>\r\n");
            }
            if (targetArea.containsKey("Width")) {
                cmdXml.append("<Width>" + targetArea.getInteger("Width") + "</Width>\r\n");
            }
            if (targetArea.containsKey("MidPoineX")) {
                cmdXml.append("<MidPoineX>" + targetArea.getInteger("MidPoineX") + "</MidPoineX>\r\n");
            }
            if (targetArea.containsKey("MidPoineY")) {
                cmdXml.append("<MidPoineY>" + targetArea.getInteger("MidPoineY") + "</MidPoineY>\r\n");
            }
            if (targetArea.containsKey("LengthX")) {
                cmdXml.append("<LengthX>" + targetArea.getInteger("LengthX") + "</LengthX>\r\n");
            }
            if (targetArea.containsKey("LengthY")) {
                cmdXml.append("<LengthY>" + targetArea.getInteger("LengthY") + "</LengthY>\r\n");
            }
            cmdXml.append("</TargetArea>\r\n");
        }
        cmdXml.append("</Control>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn + "", targetId, 10 * 1000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[目标跟踪控制] 设备编号: {}, SN: {}, 目标 ID: {}, 跟踪类型: {}, 全景通道 ID: {}, 目标区域: {}", 
            device.getDeviceId(), sn, targetId, targetTrack, deviceId2, targetArea);

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

    /**
     * 设备抓图控制
     *
     * @param device     设备
     * @param channelId  通道国标编号（可选）
     * @param callback   回调
     */
    @Override
    public void pictureCaptureControl(Device device, String channelId, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "DeviceControl";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        StringBuffer cmdXml = new StringBuffer(400);
        String charset = device.getCharset();
        String targetId = ObjectUtils.isEmpty(channelId) ? device.getDeviceId() : channelId;
        cmdXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        cmdXml.append("<Control>\r\n");
        cmdXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        cmdXml.append("<SN>" + sn + "</SN>\r\n");
        cmdXml.append("<DeviceID>" + targetId + "</DeviceID>\r\n");
        cmdXml.append("<Picture>0</Picture>\r\n");
        cmdXml.append("</Control>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn + "", targetId, 10 * 1000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[设备抓图控制] 设备编号: {}, SN: {}, 目标 ID: {}", 
            device.getDeviceId(), sn, targetId);

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

    /**
     * 设备校时控制
     *
     * @param device   设备
     * @param callback 回调
     */
    @Override
    public void timeCheckCmd(Device device, ErrorCallback<Object> callback) throws InvalidArgumentException, SipException, ParseException {
        String cmdType = "DeviceControl";
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        StringBuffer cmdXml = new StringBuffer(400);
        String charset = device.getCharset();
        String targetId = device.getDeviceId();
        cmdXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        cmdXml.append("<Control>\r\n");
        cmdXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        cmdXml.append("<SN>" + sn + "</SN>\r\n");
        cmdXml.append("<DeviceID>" + targetId + "</DeviceID>\r\n");
        cmdXml.append("<TimeCheck>0</TimeCheck>\r\n");
        cmdXml.append("</Control>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn + "", targetId, 10 * 1000L, callback);
        messageSubscribe.addSubscribe(messageEvent);
        log.info("[设备校时控制] 设备编号: {}, SN: {}, 目标 ID: {}", 
            device.getDeviceId(), sn, targetId);

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

}

