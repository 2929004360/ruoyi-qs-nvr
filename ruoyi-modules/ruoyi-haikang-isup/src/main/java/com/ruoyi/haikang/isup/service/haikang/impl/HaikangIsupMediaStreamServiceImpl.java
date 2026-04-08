package com.ruoyi.haikang.isup.service.haikang.impl;

import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.isup.config.HaikangIsupConfig;
import com.ruoyi.haikang.isup.handler.PreviewStreamHandler;
import com.ruoyi.haikang.isup.manager.StreamManager;
import com.ruoyi.haikang.isup.service.haikang.IHaikangIsupMediaStreamService;
import com.ruoyi.haikang.isup.service.haikang.cms.CmsService;
import com.ruoyi.haikang.isup.service.haikang.cms.HCISUPCMS;
import com.ruoyi.haikang.isup.service.haikang.stream.StreamService;
import com.ruoyi.qs.api.domain.QsDevice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        try {
            // 创建异步控制器
            RealPlay(lUserID, device, streamKey, rtpServerParam);
            // 阻塞，直到 stopPreview() 调用 latch.countDown()
            latch.await();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
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
        Integer sessionId = StreamManager.userIDandSessionMap.remove(luserId);
        Integer previewHandleId = StreamManager.sessionIDAndPreviewHandleMap.remove(sessionId);
        PreviewStreamHandler previewStreamHandler = StreamManager.sessionIDAndPreviewStreamHandlerMap.remove(sessionId);
        StreamManager.previewHandSAndSessionIDandMap.remove(previewHandleId);
        StreamManager.luserIdAndRtpServerParamMap.remove(sessionId);

        if (sessionId == null) {
//            log.error("无效的会话ID，无法停止预览");
            return;
        }

        if (previewHandleId == null) {
//            log.error("无效的预览句柄，无法停止预览");
            return;
        }
        if (!StreamService.hCEhomeStream.NET_ESTREAM_StopPreview(previewHandleId)) {
//            log.error("NET_ESTREAM_StopPreview 失败,err = {}", StreamService.hCEhomeStream.NET_ESTREAM_GetLastError());
            return;
        }
        if (!CmsService.hCEhomeCMS.NET_ECMS_StopGetRealStream(luserId, sessionId)) {
//            log.error("NET_ECMS_StopGetRealStream 失败,err = {}", CmsService.hCEhomeCMS.NET_ECMS_GetLastError());
            return;
        }

        if (previewStreamHandler != null) {
            previewStreamHandler.close(previewHandleId);
        }

        log.info("CMS已发送停止预览请求");
        CountDownLatch latch = latchMap.get(streamKey);
        if (latch != null) {
            latch.countDown(); // 唤醒 preview
//            log.info("结束预览实例: {}", streamKey);
        }

        latchMap.remove(streamKey);
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
            struPreviewInV11.dwLinkMode = 01;
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


        if (!CmsService.hCEhomeCMS.NET_ECMS_StartGetRealStreamV11(luserId, struPreviewInV11, struPreviewOut)) {
            log.error("NET_ECMS_StartGetRealStream 失败, error code: {}", CmsService.hCEhomeCMS.NET_ECMS_GetLastError());
        } else {
            struPreviewOut.read();
            log.info("NET_ECMS_StartGetRealStream succeed, sessionID: {}", struPreviewOut.lSessionID);
            StreamManager.userIDandSessionMap.put(luserId, struPreviewOut.lSessionID);
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
            } else {
                log.info("NET_ECMS_StartPushRealStream 成功, sessionID: {}", struPreviewOut.lSessionID);

                StreamManager.luserIdAndRtpServerParamMap.put(struPushInfoIn.lSessionID, rtpServerParam);
            }
        }
    }
}
