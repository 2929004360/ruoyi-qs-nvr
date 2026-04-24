package com.ruoyi.haikang.isup.service.haikang.impl;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.isup.config.HaikangIsupConfig;
import com.ruoyi.haikang.isup.handler.PreviewStreamHandler;
import com.ruoyi.haikang.isup.manager.StreamManager;
import com.ruoyi.haikang.isup.service.haikang.IHaikangIsupMediaStreamService;
import com.ruoyi.haikang.isup.service.haikang.cms.CmsService;
import com.ruoyi.haikang.isup.service.haikang.cms.HCISUPCMS;
import com.ruoyi.haikang.isup.service.haikang.stream.StreamService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.RemoteZlmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @FileName HaikangIsupMediaStreamServiceImpl
 * @Description
 * @Author fengcheng
 * @date 2026-04-08
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class HaikangIsupMediaStreamServiceImpl implements IHaikangIsupMediaStreamService {

    @Autowired
    private RemoteZlmService remoteZlmService;

    private final Map<String, CountDownLatch> latchMap = new ConcurrentHashMap<>();

    private final HaikangIsupConfig haikangIsupConfig;

    /**
     * 开始播放
     *
     * @param lUserID
     * @param device
     * @param streamKey
     * @param rtpServerParam
     */
    @Async("taskExecutor")
    @Override
    public void startPlay(Integer lUserID, QsDevice device, String streamKey, RtpServerParam rtpServerParam) {
        if (latchMap.containsKey(streamKey)) {
            log.info("通道已在预览中，忽略重复开启: {}", streamKey);
            return;
        }

        CountDownLatch latch = new CountDownLatch(1);
        latchMap.put(streamKey, latch);
        StreamManager.streamKeyAndRtpServerParamMap.put(streamKey, rtpServerParam);
        boolean needCleanup = true;

        try {
            // 创建异步控制器
            RealPlay(lUserID, device, streamKey, rtpServerParam);
            // 阻塞，直到 stopPreview() 调用 latch.countDown()
            latch.await();
            needCleanup = false;
        } catch (Exception e) {
            log.error("海康设备预览异常，设备id: {}, 通道号: {}, streamKey: {}", device.getId(), device.getChannel(), streamKey, e);
            throw new RuntimeException(e);
        } finally {
            if (needCleanup) {
                cleanupResources(streamKey, rtpServerParam);
            }
            latchMap.remove(streamKey);
        }
    }

    /**
     * 停止播放
     *
     * @param luserId
     * @param id
     * @param channel
     * @param streamKey
     */
    @Override
    public void stopPlay(Integer luserId, Long id, Integer channel, String streamKey) {
        RtpServerParam rtpServerParam = StreamManager.streamKeyAndRtpServerParamMap.get(streamKey);

        cleanupResources(streamKey, rtpServerParam);

        log.info("停止预览，设备id: {}, 通道号: {}", id, channel);
        CountDownLatch latch = latchMap.get(streamKey);
        if (latch != null) {
            latch.countDown(); // 唤醒 preview
            log.info("结束预览实例: {}", streamKey);
        }

        latchMap.remove(streamKey);
    }

    /**
     * 统一资源清理方法
     */
    public void cleanupResources(String streamKey, RtpServerParam rtpServerParam) {
        Integer sessionId = StreamManager.streamKeyAndSessionIDMap.get(streamKey);
        Integer luserId = StreamManager.streamKeyAndLuserIdMap.get(streamKey);
        Integer previewHandleId = null;
        PreviewStreamHandler previewStreamHandler = null;

        if (sessionId != null) {
            previewHandleId = StreamManager.sessionIDAndPreviewHandleMap.get(sessionId);
            previewStreamHandler = StreamManager.sessionIDAndPreviewStreamHandlerMap.get(sessionId);
        }

        try {
            if (previewHandleId != null) {
                StreamService.hCEhomeStream.NET_ESTREAM_StopPreview(previewHandleId);
            }
        } catch (Exception e) {
            log.error("[海康设备] 停止预览失败, streamKey: {}", streamKey, e);
        }

        try {
            if (luserId != null && sessionId != null) {
                CmsService.hCEhomeCMS.NET_ECMS_StopGetRealStream(luserId, sessionId);
            }
        } catch (Exception e) {
            log.error("[海康设备] 停止获取实时流失败, streamKey: {}", streamKey, e);
        }

        try {
            if (previewStreamHandler != null && previewHandleId != null) {
                previewStreamHandler.close(previewHandleId);
            }
        } catch (Exception e) {
            log.error("[海康设备] 关闭回调失败, streamKey: {}", streamKey, e);
        }

        // 清理所有 Map
        if (luserId != null) {
            StreamManager.userIDandSessionMap.remove(luserId);
        }
        if (sessionId != null) {
            StreamManager.sessionIDAndPreviewHandleMap.remove(sessionId);
            StreamManager.sessionIDAndPreviewStreamHandlerMap.remove(sessionId);
            StreamManager.luserIdAndRtpServerParamMap.remove(sessionId);
        }
        if (previewHandleId != null) {
            StreamManager.previewHandSAndSessionIDandMap.remove(previewHandleId);
        }

        StreamManager.streamKeyAndRtpServerParamMap.remove(streamKey);
        StreamManager.streamKeyAndSessionIDMap.remove(streamKey);
        StreamManager.streamKeyAndLuserIdMap.remove(streamKey);

        // 清理 zlm 资源
        if (rtpServerParam != null) {
            cleanupZlmResources(streamKey, rtpServerParam);
        }
    }

    /**
     * 清理 zlm 资源
     *
     * @param streamKey
     * @param rtpServerParam
     */
    private void cleanupZlmResources(String streamKey, RtpServerParam rtpServerParam) {
        try {
            log.info("[海康设备] 清理 zlm 资源, streamKey: {}, ssrc: {}", streamKey, rtpServerParam.getSsrc());
            remoteZlmService.releaseSsrc(rtpServerParam.getMediaServerId(), rtpServerParam.getSsrc(), SecurityConstants.INNER);
            remoteZlmService.closeRTPServer(rtpServerParam.getMediaServerId(), rtpServerParam, SecurityConstants.INNER);
        } catch (Exception e) {
            log.error("[海康设备] 清理 zlm 资源失败, streamKey: {}", streamKey, e);
        }
    }

    private void RealPlay(Integer luserId, QsDevice device, String streamKey, RtpServerParam rtpServerParam) {

        HCISUPCMS.NET_EHOME_PREVIEWINFO_IN_V11 struPreviewInV11 = new HCISUPCMS.NET_EHOME_PREVIEWINFO_IN_V11();
        //通道号
        struPreviewInV11.iChannel = device.getChannel();

        if ("TCP".equals(device.getProtocol())) {
            //0- TCP方式，1- UDP方式
            struPreviewInV11.dwLinkMode = 0;
        } else {
            //0- TCP方式，1- UDP方式
            struPreviewInV11.dwLinkMode = 1;
        }

        if ("1".equals(device.getStreamType())) {
            //码流类型：0- 主码流，1- 子码流, 2- 第三码流
            struPreviewInV11.dwStreamType = 0;
        } else {
            //码流类型：0- 主码流，1- 子码流, 2- 第三码流
            struPreviewInV11.dwStreamType = 1;
        }


        log.info("ip: {}, port: {}", haikangIsupConfig.getSmsServer().getIp(), haikangIsupConfig.getSmsServer().getPort());
        //流媒体服务器IP地址,公网地址
        struPreviewInV11.struStreamSever.szIP = haikangIsupConfig.getSmsServer().getIp().getBytes();
        //流媒体服务器端口，需要跟服务器启动监听端口一致
        struPreviewInV11.struStreamSever.wPort = (short) haikangIsupConfig.getSmsServer().getPort();
        struPreviewInV11.write();

        //预览请求
        HCISUPCMS.NET_EHOME_PREVIEWINFO_OUT struPreviewOut = new HCISUPCMS.NET_EHOME_PREVIEWINFO_OUT();
        boolean getRS = CmsService.hCEhomeCMS.NET_ECMS_StartGetRealStreamV11(luserId, struPreviewInV11, struPreviewOut);
        log.info("NET_ECMS_StartGetRealStream 预览请求: {}", getRS);


        if (!getRS) {
            log.error("NET_ECMS_StartGetRealStream 失败, error code: {}", CmsService.hCEhomeCMS.NET_ECMS_GetLastError());
            throw new RuntimeException("海康设备预览失败");
        } else {
            struPreviewOut.read();
            log.info("NET_ECMS_StartGetRealStream succeed, sessionID: {}", struPreviewOut.lSessionID);
            StreamManager.userIDandSessionMap.put(luserId, struPreviewOut.lSessionID);
            StreamManager.streamKeyAndLuserIdMap.put(streamKey, luserId);
            StreamManager.streamKeyAndSessionIDMap.put(streamKey, struPreviewOut.lSessionID);
            HCISUPCMS.NET_EHOME_PUSHSTREAM_IN struPushInfoIn = new HCISUPCMS.NET_EHOME_PUSHSTREAM_IN();
            struPushInfoIn.read();
            struPushInfoIn.dwSize = struPushInfoIn.size();
            struPushInfoIn.lSessionID = struPreviewOut.lSessionID;
            struPushInfoIn.write();
            HCISUPCMS.NET_EHOME_PUSHSTREAM_OUT struPushInfoOut = new HCISUPCMS.NET_EHOME_PUSHSTREAM_OUT();
            struPushInfoOut.read();
            struPushInfoOut.dwSize = struPushInfoOut.size();
            struPushInfoOut.write();

            if (!CmsService.hCEhomeCMS.NET_ECMS_StartPushRealStream(luserId, struPushInfoIn, struPushInfoOut)) {
                log.error("NET_ECMS_StartPushRealStream 失败, error code: {}", CmsService.hCEhomeCMS.NET_ECMS_GetLastError());
                throw new RuntimeException("海康设备推流失败");
            } else {
                log.info("NET_ECMS_StartPushRealStream 成功, sessionID: {}", struPreviewOut.lSessionID);

                StreamManager.luserIdAndRtpServerParamMap.put(struPushInfoIn.lSessionID, rtpServerParam);
            }
        }
    }
}
