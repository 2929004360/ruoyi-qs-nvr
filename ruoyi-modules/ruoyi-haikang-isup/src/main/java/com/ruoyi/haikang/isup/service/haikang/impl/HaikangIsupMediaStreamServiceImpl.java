package com.ruoyi.haikang.isup.service.haikang.impl;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.isup.config.HaikangIsupConfig;
import com.ruoyi.haikang.isup.handler.PreviewStreamHandler;
import com.ruoyi.haikang.isup.handler.PlaybackStreamHandler;
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
        CountDownLatch latch = latchMap.get(streamKey);
        if (latch != null) {
            latch.countDown(); // 唤醒 preview
            log.info("结束预览实例: {}", streamKey);
        }

        RtpServerParam rtpServerParam = StreamManager.streamKeyAndRtpServerParamMap.get(streamKey);
        cleanupResources(streamKey, rtpServerParam);

        latchMap.remove(streamKey);
        log.info("停止预览，设备id: {}, 通道号: {}", id, channel);
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

    /**
     * 开始回放
     *
     * @param lUserID
     * @param device
     * @param playbackKey
     * @param rtpServerParam
     */
    @Async("taskExecutor")
    @Override
    public void startPlayback(Integer lUserID, QsDevice device, String playbackKey, RtpServerParam rtpServerParam) {
        if (StreamManager.playbackKeyAndLatchMap.containsKey(playbackKey)) {
            log.info("回放通道已在运行，忽略重复开启: {}", playbackKey);
            return;
        }

        CountDownLatch latch = new CountDownLatch(1);
        StreamManager.playbackKeyAndLatchMap.put(playbackKey, latch);
        StreamManager.playbackKeyAndRtpServerParamMap.put(playbackKey, rtpServerParam);
        boolean needCleanup = true;

        try {
            // 创建异步控制器
            RealPlayback(lUserID, device, playbackKey, rtpServerParam);
            // 阻塞，直到 stopPlayback() 调用 latch.countDown()
            latch.await();
            needCleanup = false;
        } catch (Exception e) {
            log.error("海康设备回放异常，设备id: {}, 通道号: {}, playbackKey: {}", device.getId(), device.getChannel(), playbackKey, e);
            throw new RuntimeException(e);
        } finally {
            if (needCleanup) {
                cleanupPlaybackResources(playbackKey, rtpServerParam);
            }
            StreamManager.playbackKeyAndLatchMap.remove(playbackKey);
        }
    }

    /**
     * 停止回放
     *
     * @param lUserID
     * @param id
     * @param channel
     * @param playbackKey
     */
    @Override
    public void stopPlayback(Integer lUserID, Long id, Integer channel, String playbackKey) {
        CountDownLatch latch = StreamManager.playbackKeyAndLatchMap.get(playbackKey);
        if (latch != null) {
            latch.countDown();
            log.info("结束回放实例: {}", playbackKey);
        }

        RtpServerParam rtpServerParam = StreamManager.playbackKeyAndRtpServerParamMap.get(playbackKey);
        cleanupPlaybackResources(playbackKey, rtpServerParam);

        StreamManager.playbackKeyAndLatchMap.remove(playbackKey);
        log.info("停止回放，设备id: {}, 通道号: {}", id, channel);
    }

    /**
     * 统一回放资源清理方法
     *
     * @param playbackKey
     * @param rtpServerParam
     */
    @Override
    public void cleanupPlaybackResources(String playbackKey, RtpServerParam rtpServerParam) {
        Integer sessionId = StreamManager.playbackKeyAndSessionIDMap.get(playbackKey);
        Integer luserId = StreamManager.playbackKeyAndLuserIdMap.get(playbackKey);
        Integer playbackHandleId = null;
        
        // 通过 sessionId 获取 playbackHandleId
        if (sessionId != null) {
            playbackHandleId = StreamManager.sessionIDAndPlaybackHandleMap.get(sessionId);
        }
        
        PlaybackStreamHandler playbackStreamHandler = null;

        if (sessionId != null) {
            playbackStreamHandler = StreamManager.sessionIDAndPlaybackStreamHandlerMap.get(sessionId);
        }

        try {
            if (playbackHandleId != null) {
                StreamService.hCEhomeStream.NET_ESTREAM_StopPlayBack(playbackHandleId);
            }
        } catch (Exception e) {
            log.error("[海康设备] 停止回放失败，playbackKey: {}", playbackKey, e);
        }

        try {
            if (luserId != null && sessionId != null) {
                CmsService.hCEhomeCMS.NET_ECMS_StopPlayBack(luserId, sessionId);
            }
        } catch (Exception e) {
            log.error("[海康设备] 停止获取回放流失败，playbackKey: {}", playbackKey, e);
        }

        try {
            if (playbackStreamHandler != null && playbackHandleId != null) {
                playbackStreamHandler.close(playbackHandleId);
            }
        } catch (Exception e) {
            log.error("[海康设备] 关闭回放回调失败，playbackKey: {}", playbackKey, e);
        }

        // 清理所有 Map
        if (luserId != null) {
            StreamManager.playbackKeyAndLuserIdMap.remove(playbackKey);
        }
        if (sessionId != null) {
            StreamManager.sessionIDAndPlaybackStreamHandlerMap.remove(sessionId);
            StreamManager.sessionIDAndPlaybackHandleMap.remove(sessionId);
            StreamManager.luserIdAndPlaybackRtpServerParamMap.remove(sessionId);
        }
        if (playbackHandleId != null) {
            StreamManager.playbackHandSAndSessionIDandMap.remove(playbackHandleId);
        }

        StreamManager.playbackKeyAndPlaybackHandleMap.remove(playbackKey);
        StreamManager.playbackKeyAndRtpServerParamMap.remove(playbackKey);
        StreamManager.playbackKeyAndSessionIDMap.remove(playbackKey);

        // 清理 zlm 资源
        if (rtpServerParam != null) {
            cleanupPlaybackZlmResources(playbackKey, rtpServerParam);
        }
    }

    /**
     * 清理回放 zlm 资源
     *
     * @param playbackKey
     * @param rtpServerParam
     */
    private void cleanupPlaybackZlmResources(String playbackKey, RtpServerParam rtpServerParam) {
        try {
            log.info("[海康设备] 清理回放 zlm 资源，playbackKey: {}, ssrc: {}", playbackKey, rtpServerParam.getSsrc());
            remoteZlmService.releaseSsrc(rtpServerParam.getMediaServerId(), rtpServerParam.getSsrc(), SecurityConstants.INNER);
            remoteZlmService.closeRTPServer(rtpServerParam.getMediaServerId(), rtpServerParam, SecurityConstants.INNER);
        } catch (Exception e) {
            log.error("[海康设备] 清理回放 zlm 资源失败，playbackKey: {}", playbackKey, e);
        }
    }

    private void RealPlayback(Integer luserId, QsDevice device, String playbackKey, RtpServerParam rtpServerParam) {
        log.info("开始回放海康设备, deviceId: {}, ip: {}, lUserID: {}, channel: {}, startTime: {}, endTime: {}",
                device.getId(), device.getIpAddress(), luserId,
                device.getChannel(), rtpServerParam.getStartTime(), rtpServerParam.getEndTime());

        // 验证登录句柄
        if (luserId == null || luserId == 0) {
            log.error("登录句柄无效, deviceId: {}", device.getId());
            throw new RuntimeException("登录句柄无效");
        }

        int channelToUse = device.getChannel();

        // 构造回放参数
        HCISUPCMS.NET_EHOME_PLAYBACK_INFO_IN m_struPlayBackInfoIn = new HCISUPCMS.NET_EHOME_PLAYBACK_INFO_IN();
        m_struPlayBackInfoIn.read();
        m_struPlayBackInfoIn.dwSize = m_struPlayBackInfoIn.size();
        m_struPlayBackInfoIn.dwChannel = channelToUse; //通道号
        m_struPlayBackInfoIn.byPlayBackMode = 1;//0- 按文件名回放，1- 按时间回放
        m_struPlayBackInfoIn.byStreamPackage = 1;//回放码流类型，设备端发出的码流格式 0－PS（默认） 1－RTP
        m_struPlayBackInfoIn.unionPlayBackMode.setType(HCISUPCMS.NET_EHOME_PLAYBACKBYTIME.class);

        // 解析开始时间
        String startTime = rtpServerParam.getStartTime();
        String endTime = rtpServerParam.getEndTime();
        String[] dateStartByFile = startTime.split(" ");
        String[] dateStart1 = dateStartByFile[0].split("-");
        String[] dateStart2 = dateStartByFile[1].split(":");

        // 填充开始时间
        m_struPlayBackInfoIn.unionPlayBackMode.struPlayBackbyTime.struStartTime = new HCISUPCMS.NET_EHOME_TIME();
        m_struPlayBackInfoIn.unionPlayBackMode.struPlayBackbyTime.struStartTime.wYear = (short) Integer.parseInt(dateStart1[0]);
        m_struPlayBackInfoIn.unionPlayBackMode.struPlayBackbyTime.struStartTime.byMonth = (byte) Integer.parseInt(dateStart1[1]);
        m_struPlayBackInfoIn.unionPlayBackMode.struPlayBackbyTime.struStartTime.byDay = (byte) Integer.parseInt(dateStart1[2]);
        m_struPlayBackInfoIn.unionPlayBackMode.struPlayBackbyTime.struStartTime.byHour = (byte) Integer.parseInt(dateStart2[0]);
        m_struPlayBackInfoIn.unionPlayBackMode.struPlayBackbyTime.struStartTime.byMinute = (byte) Integer.parseInt(dateStart2[1]);
        m_struPlayBackInfoIn.unionPlayBackMode.struPlayBackbyTime.struStartTime.bySecond = (byte) Integer.parseInt(dateStart2[2]);

        // 解析结束时间
        String[] dateEndByFile = endTime.split(" ");
        String[] dateEnd1 = dateEndByFile[0].split("-");
        String[] dateEnd2 = dateEndByFile[1].split(":");

        // 填充结束时间
        m_struPlayBackInfoIn.unionPlayBackMode.struPlayBackbyTime.struStopTime = new HCISUPCMS.NET_EHOME_TIME();
        m_struPlayBackInfoIn.unionPlayBackMode.struPlayBackbyTime.struStopTime.wYear = (short) Integer.parseInt(dateEnd1[0]);
        m_struPlayBackInfoIn.unionPlayBackMode.struPlayBackbyTime.struStopTime.byMonth = (byte) Integer.parseInt(dateEnd1[1]);
        m_struPlayBackInfoIn.unionPlayBackMode.struPlayBackbyTime.struStopTime.byDay = (byte) Integer.parseInt(dateEnd1[2]);
        m_struPlayBackInfoIn.unionPlayBackMode.struPlayBackbyTime.struStopTime.byHour = (byte) Integer.parseInt(dateEnd2[0]);
        m_struPlayBackInfoIn.unionPlayBackMode.struPlayBackbyTime.struStopTime.byMinute = (byte) Integer.parseInt(dateEnd2[1]);
        m_struPlayBackInfoIn.unionPlayBackMode.struPlayBackbyTime.struStopTime.bySecond = (byte) Integer.parseInt(dateEnd2[2]);

        m_struPlayBackInfoIn.unionPlayBackMode.struPlayBackbyTime.byLocalOrUTC = 0; //0-设备本地时间
        m_struPlayBackInfoIn.unionPlayBackMode.struPlayBackbyTime.byDuplicateSegment = 0; //0-重复时间段的前段

        System.arraycopy(haikangIsupConfig.getSmsBackServer().getIp().getBytes(), 0, m_struPlayBackInfoIn.struStreamSever.szIP,
                0, haikangIsupConfig.getSmsBackServer().getIp().length());
        m_struPlayBackInfoIn.struStreamSever.wPort = (short) haikangIsupConfig.getSmsBackServer().getPort();
        m_struPlayBackInfoIn.write();

        HCISUPCMS.NET_EHOME_PLAYBACK_INFO_OUT m_struPlayBackInfoOut = new HCISUPCMS.NET_EHOME_PLAYBACK_INFO_OUT();
        m_struPlayBackInfoOut.write();

        if (!CmsService.hCEhomeCMS.NET_ECMS_StartPlayBack(luserId, m_struPlayBackInfoIn, m_struPlayBackInfoOut)) {
            log.error("NET_ECMS_StartPlayBack失败，错误代码:" + CmsService.hCEhomeCMS.NET_ECMS_GetLastError());
            throw new RuntimeException("NET_ECMS_StartPlayBack失败");
        } else {
            m_struPlayBackInfoOut.read();
            log.info("NET_ECMS_StartPlayBack成功，lSessionID:" + m_struPlayBackInfoOut.lSessionID);
        }

        int lSessionID = m_struPlayBackInfoOut.lSessionID;

        StreamManager.playbackKeyAndSessionIDMap.put(playbackKey, lSessionID);
        StreamManager.playbackKeyAndLuserIdMap.put(playbackKey, luserId);
        StreamManager.playbackKeyAndPlaybackHandleMap.put(playbackKey, lSessionID);

        // 设置 sessionID 与 rtpServerParam 的映射（供回调函数使用）
        StreamManager.luserIdAndPlaybackRtpServerParamMap.put(lSessionID, rtpServerParam);

        HCISUPCMS.NET_EHOME_PUSHPLAYBACK_IN m_struPushPlayBackIn = new HCISUPCMS.NET_EHOME_PUSHPLAYBACK_IN();
        m_struPushPlayBackIn.read();
        m_struPushPlayBackIn.dwSize = m_struPushPlayBackIn.size();
        m_struPushPlayBackIn.lSessionID = lSessionID;
        m_struPushPlayBackIn.write();

        HCISUPCMS.NET_EHOME_PUSHPLAYBACK_OUT m_struPushPlayBackOut = new HCISUPCMS.NET_EHOME_PUSHPLAYBACK_OUT();
        m_struPushPlayBackOut.read();
        m_struPushPlayBackOut.dwSize = m_struPushPlayBackOut.size();
        m_struPushPlayBackOut.write();

        if (!CmsService.hCEhomeCMS.NET_ECMS_StartPushPlayBack(luserId, m_struPushPlayBackIn, m_struPushPlayBackOut)) {
            log.error("NET_ECMS_StartPushPlayBack失败，错误代码:" + CmsService.hCEhomeCMS.NET_ECMS_GetLastError());
            throw new RuntimeException("NET_ECMS_StartPushPlayBack失败");
        } else {
            log.info("NET_ECMS_StartPushPlayBack成功，lSessionID:" + lSessionID);
        }
    }
}
