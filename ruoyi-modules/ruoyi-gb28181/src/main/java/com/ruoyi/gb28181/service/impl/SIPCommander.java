package com.ruoyi.gb28181.service.impl;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.gb28181.api.bean.ErrorCallback;
import com.ruoyi.gb28181.api.bean.Preset;
import com.ruoyi.gb28181.api.common.InviteSessionType;
import com.ruoyi.gb28181.api.domain.Device;
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

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn, device.getDeviceId(), 1000L, callback);
        messageSubscribe.addSubscribe(messageEvent);

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
        cmdXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        cmdXml.append("<Query>\r\n");
        cmdXml.append("<CmdType>" + cmdType + "</CmdType>\r\n");
        cmdXml.append("<SN>" + sn + "</SN>\r\n");
        if (ObjectUtils.isEmpty(channelId)) {
            cmdXml.append("<DeviceID>" + device.getDeviceId() + "</DeviceID>\r\n");
        } else {
            cmdXml.append("<DeviceID>" + channelId + "</DeviceID>\r\n");
        }
        cmdXml.append("<ConfigType>" + configType + "</ConfigType>\r\n");
        cmdXml.append("</Query>\r\n");

        MessageEvent<Object> messageEvent = MessageEvent.getInstance(cmdType, sn + "", channelId, 1000L, callback);
        messageSubscribe.addSubscribe(messageEvent);

        Request request = headerProvider.createMessageRequest(device, cmdXml.toString(), null, SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));
        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            messageSubscribe.removeSubscribe(messageEvent.getKey());
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

    @Override
    public void catalogQuery(Device device, int sn, ErrorCallback<String> callback) throws SipException, InvalidArgumentException, ParseException {
        String cmdType = "Catalog";

        StringBuffer catalogXml = new StringBuffer(200);
        String charset = device.getCharset();
        catalogXml.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        catalogXml.append("<Query>\r\n");
        catalogXml.append("  <CmdType>" + cmdType + "</CmdType>\r\n");
        catalogXml.append("  <SN>" + sn + "</SN>\r\n");
        catalogXml.append("  <DeviceID>" + device.getDeviceId() + "</DeviceID>\r\n");
        catalogXml.append("</Query>\r\n");

        Request request = headerProvider.createMessageRequest(device, catalogXml.toString(), SipUtils.getNewViaTag(), SipUtils.getNewFromTag(), null, sipSender.getNewCallIdHeader(sipLayer.getLocalIp(device.getLocalIp()), device.getTransport()));

        sipSender.transmitRequest(sipLayer.getLocalIp(device.getLocalIp()), request, eventResult -> {
            if (callback != null) {
                callback.run(ErrorCode.ERROR100.getCode(), "失败，" + eventResult.msg, null);
            }
        });
    }

    /**
     * 查询设备状态
     *
     * @param device
     * @param callback
     */
    @Override
    public void deviceStatusQuery(Device device, ErrorCallback<String> callback) throws InvalidArgumentException, SipException, ParseException {
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

        MessageEvent<String> messageEvent = MessageEvent.getInstance(cmdType, sn + "", device.getDeviceId(), 1000L, callback);
        messageSubscribe.addSubscribe(messageEvent);

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
}
