package com.ruoyi.gb28181.transmit.event.request.impl;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.dahua.api.RemoteDaHuaService;
import com.ruoyi.gb28181.api.common.InviteSessionType;
import com.ruoyi.gb28181.api.domain.SsrcTransaction;
import com.ruoyi.gb28181.session.SipInviteSessionManager;
import com.ruoyi.gb28181.transmit.ISIPProcessorObserver;
import com.ruoyi.gb28181.transmit.SIPSender;
import com.ruoyi.gb28181.transmit.event.request.ISIPRequestProcessor;
import com.ruoyi.gb28181.transmit.event.request.SIPRequestProcessorParent;
import com.ruoyi.haikang.api.RemoteHaiKangService;
import com.ruoyi.haikang.isup.api.RemoteHaiKangIsupService;
import com.ruoyi.onvif.api.RemoteOnvifService;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.RemoteZlmCloudRecordService;
import com.ruoyi.zlm.api.RemoteZlmService;
import com.ruoyi.zlm.api.domain.ZlmCloudRecord;
import gov.nist.javax.sip.message.SIPRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sip.RequestEvent;
import javax.sip.header.CallIdHeader;
import javax.sip.message.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ByeRequestProcessor extends SIPRequestProcessorParent implements InitializingBean, ISIPRequestProcessor {

    public final String method = "BYE";

    @Autowired
    private ISIPProcessorObserver sipProcessorObserver;

    @Autowired
    private SIPSender sipSender;

    @Autowired
    private SipInviteSessionManager sessionManager;

    @Autowired
    private RemoteZlmService remoteZlmService;

    @Autowired
    private RemoteHaiKangService remoteHaiKangService;

    @Autowired
    private RemoteHaiKangIsupService remoteHaiKangIsupService;

    @Autowired
    private RemoteDaHuaService remoteDaHuaService;

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private RemoteZlmCloudRecordService remoteZlmCloudRecordService;

    @Autowired
    private RemoteOnvifService remoteOnvifService;

    @Override
    public void afterPropertiesSet() throws Exception {
        sipProcessorObserver.addRequestProcessor(method, this);
    }

    @Override
    public void process(RequestEvent evt) {
        SIPRequest request = (SIPRequest) evt.getRequest();
        CallIdHeader callIdHeader = request.getCallIdHeader();
        String callId = callIdHeader.getCallId();

        log.info("[收到 BYE 请求] callId: {}", callId);

        try {
            Response okResponse = getMessageFactory().createResponse(Response.OK, request);
            sipSender.transmitRequest(request.getLocalAddress().getHostAddress(), okResponse);

            SsrcTransaction ssrcTransaction = sessionManager.getSsrcTransactionByCallId(callId);
            if (ssrcTransaction != null) {
                // 判断是设备会话还是平台级联会话
                String sessionType = (ssrcTransaction.getPlatformId() != null) ? "平台级联" : "设备点播";
                String deviceId = (ssrcTransaction.getPlatformId() != null) ? ssrcTransaction.getPlatformId() : ssrcTransaction.getDeviceId();
                
                log.info("[BYE清理资源] 类型: {}, 设备ID: {}, 通道ID: {}, app: {}, stream: {}, ssrc: {}, 媒体服务器: {}, 会话类型: {}, 设备类型: {}", 
                        sessionType,
                        deviceId, 
                        ssrcTransaction.getChannelId(), 
                        ssrcTransaction.getApp(), 
                        ssrcTransaction.getStream(), 
                        ssrcTransaction.getSsrc(),
                        ssrcTransaction.getMediaServerId(),
                        ssrcTransaction.getType(),
                        ssrcTransaction.getQsDeviceType());

                // 如果是回放并且有qsDevice信息，先停止设备回放
                if (ssrcTransaction.getQsDeviceId() != null && 
                    ssrcTransaction.getType() == InviteSessionType.PLAYBACK &&
                    ssrcTransaction.getQsDeviceType() != null) {
                    stopDevicePlayback(ssrcTransaction);
                }

                // 对于平台级联会话，先调用stopSendRtp停止发送RTP流
                if (ssrcTransaction.getPlatformId() != null) {
                    try {
                        Map<String, Object> stopParams = new HashMap<>();
                        stopParams.put("ssrc", ssrcTransaction.getSsrc());
                        stopParams.put("vhost", "__defaultVhost__");
                        stopParams.put("app", ssrcTransaction.getApp());
                        stopParams.put("stream", ssrcTransaction.getStream());
                        
                        log.info("[平台级联停止] 调用stopSendRtp] params: {}", stopParams);
                        remoteZlmService.stopSendRtp(ssrcTransaction.getMediaServerId(), stopParams, SecurityConstants.INNER);
                        log.info("[平台级联停止] stopSendRtp调用成功");
                    } catch (Exception e) {
                        log.error("[平台级联停止] stopSendRtp调用失败] error: ", e);
                    }
                }

                // 清理会话
                sessionManager.removeByCallId(callId);
                log.info("[会话已清理] callId: {}", callId);

                // 释放SSRC
                try {
                    remoteZlmService.releaseSsrc(ssrcTransaction.getMediaServerId(), ssrcTransaction.getSsrc(), SecurityConstants.INNER);
                    log.info("[SSRC已释放] ssrc: {}", ssrcTransaction.getSsrc());
                } catch (Exception e) {
                    log.error("[释放SSRC失败] ssrc: {}, error: ", ssrcTransaction.getSsrc(), e);
                }
                
                // 关闭RTP服务器
                try {
                    RtpServerParam rtpServerParam = new RtpServerParam();
                    rtpServerParam.setMediaServerId(ssrcTransaction.getMediaServerId());
                    rtpServerParam.setApp(ssrcTransaction.getApp());
                    rtpServerParam.setStream(ssrcTransaction.getStream());
                    rtpServerParam.setSsrc(ssrcTransaction.getSsrc());
                    rtpServerParam.setGbDeviceId(ssrcTransaction.getDeviceId());
                    rtpServerParam.setGbChannelId(ssrcTransaction.getChannelId());
                    remoteZlmService.closeRTPServer(ssrcTransaction.getMediaServerId(), rtpServerParam, SecurityConstants.INNER);
                    log.info("[RTP服务器已关闭] app: {}, stream: {}", ssrcTransaction.getApp(), ssrcTransaction.getStream());
                } catch (Exception e) {
                    log.error("[关闭RTP服务器失败] app: {}, stream: {}, error: ", 
                            ssrcTransaction.getApp(), ssrcTransaction.getStream(), e);
                }
                
                log.info("[BYE 资源清理完成] type: {}, callId: {}", sessionType, callId);
            } else {
                log.warn("[BYE 未找到会话] callId: {}", callId);
            }
        } catch (Exception e) {
            log.error("[BYE 处理异常] callId: {}, error: ", callId, e);
        }
    }

    /**
     * 停止设备回放
     */
    private void stopDevicePlayback(SsrcTransaction ssrcTransaction) {
        try {
            String deviceType = ssrcTransaction.getQsDeviceType();
            Long deviceId = ssrcTransaction.getQsDeviceId();
            String stream = ssrcTransaction.getStream();
            
            log.info("[停止设备回放] 设备ID: {}, 设备类型: {}, 流名称: {}", deviceId, deviceType, stream);
            
            if (deviceType == null || deviceId == null || stream == null) {
                log.warn("[停止设备回放] 设备类型、设备ID或流名称为空，跳过");
                return;
            }

            R<Void> result = remoteZlmService.stopPlayback(deviceId, deviceType, stream, SecurityConstants.INNER);
            if (result.getCode() == Constants.SUCCESS) {
                log.info("[停止设备回放成功] deviceId: {}, deviceType: {}", deviceId, deviceType);
            } else {
                log.warn("[停止设备回放失败] deviceId: {}, deviceType: {}, code: {}, msg: {}", deviceId, deviceType, result.getCode(), result.getMsg());
            }
        } catch (Exception e) {
            log.error("[停止设备回放异常] error: ", e);
        }
    }
}
