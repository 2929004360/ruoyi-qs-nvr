package com.ruoyi.haikang.service.impl;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.callback.FRealDataForRtpOverTcpCallback;
import com.ruoyi.haikang.manager.StreamManager;
import com.ruoyi.haikang.net.Client;
import com.ruoyi.haikang.net.HCNetSDK;
import com.ruoyi.haikang.service.IHaikangMediaStreamService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.RemoteZlmService;
import com.sun.jna.Pointer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @FileName HaikangMediaStreamServiceImpl
 * @Description
 * @Author fengcheng
 * @date 2026-01-15
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class HaikangMediaStreamServiceImpl implements IHaikangMediaStreamService {

    @Autowired
    private Client client;

    @Autowired
    private RemoteZlmService remoteZlmService;

    // 每个设备一个 latch，用于控制阻塞/停止
    private final Map<String, CountDownLatch> latchMap = new ConcurrentHashMap<>();

    /**
     * 播放视频
     *
     * @param lUserID
     * @param device
     * @param streamKey
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

        long realHandle = -1;
        FRealDataForRtpOverTcpCallback fRealDataCallBack = null;
        boolean needCleanup = true;

        try {
            fRealDataCallBack = new FRealDataForRtpOverTcpCallback(
                    rtpServerParam.getIp(),
                    rtpServerParam.getPort(),
                    rtpServerParam.getSsrc()
            );
            HCNetSDK.NET_DVR_PREVIEWINFO netDvrPreviewinfo = new HCNetSDK.NET_DVR_PREVIEWINFO();
            netDvrPreviewinfo.lChannel = device.getChannel();

            if ("1".equals(device.getStreamType())) {
                netDvrPreviewinfo.dwStreamType = 0;
            } else {
                netDvrPreviewinfo.dwStreamType = 1;
            }

            netDvrPreviewinfo.bBlocked = 0;

            if ("TCP".equals(device.getProtocol())) {
                netDvrPreviewinfo.dwLinkMode = 0;
            } else {
                netDvrPreviewinfo.dwLinkMode = 1;
            }

            netDvrPreviewinfo.byProtoType = 0;

            //播放视频
            realHandle = client.hCNetSDK.NET_DVR_RealPlay_V40(lUserID, netDvrPreviewinfo, fRealDataCallBack, Pointer.NULL);
            if (realHandle == -1) {
                throw new ServiceException("开始sdk播放视频失败! 错误码：" + client.hCNetSDK.NET_DVR_GetLastError());
            }

            StreamManager.streamKeyAndRealHandleMap.put(streamKey, realHandle);
            StreamManager.streamKeyAndFRealDataForRtpOverTcpCallbackMap.put(streamKey, fRealDataCallBack);

            // 阻塞,调用 latch.countDown()
            latch.await();
            needCleanup = false;
        } catch (Exception e) {
            log.error("海康设备预览异常，设备id：{}，通道号：{}", device.getId(), device.getChannel(), e);
            throw new RuntimeException(e);
        } finally {
            if (needCleanup) {
                cleanupResources(streamKey, rtpServerParam, realHandle, fRealDataCallBack);
            }
            latchMap.remove(streamKey);
        }
    }

    /**
     * 结束播放视频
     *
     * @param deviceId
     * @param channelId
     * @param streamKey
     */
    @Override
    public void endPlay(Long deviceId, int channelId, String streamKey) {
        RtpServerParam rtpServerParam = StreamManager.streamKeyAndRtpServerParamMap.get(streamKey);
        Long realHandle = StreamManager.streamKeyAndRealHandleMap.get(streamKey);
        FRealDataForRtpOverTcpCallback fRealDataCallBack = StreamManager.streamKeyAndFRealDataForRtpOverTcpCallbackMap.get(streamKey);

        cleanupResources(streamKey, rtpServerParam, realHandle, fRealDataCallBack);

        CountDownLatch latch = latchMap.get(streamKey);
        if (latch != null) {
            latch.countDown();
            log.info("结束预览实例: {}", streamKey);
        }

        latchMap.remove(streamKey);
        log.info("停止预览，设备id：{}，通道号：{}", deviceId, channelId);
    }

    /**
     * 统一资源清理方法
     */
    public void cleanupResources(String streamKey, RtpServerParam rtpServerParam,
                                  Long realHandle, FRealDataForRtpOverTcpCallback fRealDataCallBack) {
        try {
            if (realHandle != null && realHandle != -1) {
                client.hCNetSDK.NET_DVR_StopRealPlay(Math.toIntExact(realHandle));
            }
        } catch (Exception e) {
            log.error("[海康设备] 停止预览失败，streamKey：{}", streamKey, e);
        }

        try {
            if (fRealDataCallBack != null) {
                fRealDataCallBack.close();
            }
        } catch (Exception e) {
            log.error("[海康设备] 关闭回调失败，streamKey：{}", streamKey, e);
        }

        StreamManager.streamKeyAndRealHandleMap.remove(streamKey);
        StreamManager.streamKeyAndFRealDataForRtpOverTcpCallbackMap.remove(streamKey);
        StreamManager.streamKeyAndRtpServerParamMap.remove(streamKey);

        if (rtpServerParam != null) {
            cleanupZlmResources(streamKey, rtpServerParam);
        }
    }

    /**
     * 清理zlm资源
     *
     * @param streamKey
     * @param rtpServerParam
     */
    private void cleanupZlmResources(String streamKey, RtpServerParam rtpServerParam) {
        try {
            log.info("[海康设备] 清理zlm资源，streamKey：{}，ssrc：{}", streamKey, rtpServerParam.getSsrc());
            remoteZlmService.releaseSsrc(rtpServerParam.getMediaServerId(), rtpServerParam.getSsrc(), SecurityConstants.INNER);
            remoteZlmService.closeRTPServer(rtpServerParam.getMediaServerId(), rtpServerParam, SecurityConstants.INNER);
        } catch (Exception e) {
            log.error("[海康设备] 清理zlm资源失败，streamKey：{}", streamKey, e);
        }
    }
}
