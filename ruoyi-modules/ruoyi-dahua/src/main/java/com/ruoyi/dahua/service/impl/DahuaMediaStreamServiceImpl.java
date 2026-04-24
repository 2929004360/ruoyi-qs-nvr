package com.ruoyi.dahua.service.impl;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.dahua.callback.FRealDatarTPCallback;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.manager.StreamManager;
import com.ruoyi.dahua.service.IDahuaMediaStreamService;
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
 * @FileName DahuaMediaStreamServiceImpl
 * @Description
 * @Author fengcheng
 * @date 2026-04-09
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class DahuaMediaStreamServiceImpl implements IDahuaMediaStreamService {

    @Autowired
    private RemoteZlmService remoteZlmService;

    // 每个设备一个 latch，用于控制阻塞/停止
    private final Map<String, CountDownLatch> latchMap = new ConcurrentHashMap<>();

    /**
     * 开始播放
     *
     * @param lLong
     * @param device
     * @param streamKey
     * @param rtpServerParam
     */
    @Async("taskExecutor")
    @Override
    public void startPlay(NetSDKLib.LLong lLong, QsDevice device, String streamKey, RtpServerParam rtpServerParam) {
        if (latchMap.containsKey(streamKey)) {
            log.info("通道已在预览中，忽略重复开启: {}", streamKey);
            return;
        }

        CountDownLatch latch = new CountDownLatch(1);
        latchMap.put(streamKey, latch);
        StreamManager.streamKeyAndRtpServerParamMap.put(streamKey, rtpServerParam);

        NetSDKLib.LLong lRealHandle = new NetSDKLib.LLong(0);
        FRealDatarTPCallback fRealDataCallBack = null;
        boolean needCleanup = true;

        try {
            NetSDKLib.NET_IN_REALPLAY_BY_DATA_TYPE inParam = new NetSDKLib.NET_IN_REALPLAY_BY_DATA_TYPE();
            NetSDKLib.NET_OUT_REALPLAY_BY_DATA_TYPE outParam = new NetSDKLib.NET_OUT_REALPLAY_BY_DATA_TYPE();
            inParam.nChannelID = device.getChannel();

            if ("1".equals(device.getStreamType())) {
                inParam.rType = 2;
            } else if ("2".equals(device.getStreamType())) {
                inParam.rType = 3;
            } else {
                inParam.rType = 0;
            }

            inParam.emDataType = 1;
            lRealHandle = DaHuaServiceImpl.netsdk.CLIENT_RealPlayByDataType(lLong, inParam, outParam, 3000);

            if (lRealHandle.longValue() != 0) {
                fRealDataCallBack = new FRealDatarTPCallback(
                        rtpServerParam.getIp(),
                        rtpServerParam.getPort(),
                        rtpServerParam.getSsrc()
                );

                DaHuaServiceImpl.netsdk.CLIENT_SetRealDataCallBackEx(lRealHandle, fRealDataCallBack, null, 31);

                StreamManager.streamKeyAndRealHandleMap.put(streamKey, lRealHandle);
                StreamManager.streamKeyAndFRealDatarTPCallbackMap.put(streamKey, fRealDataCallBack);

                latch.await();
                needCleanup = false;
            } else {
                log.error("大华设备预览失败，设备id：{}，通道号：{}", device.getId(), device.getChannel());
                throw new RuntimeException("大华设备预览失败");
            }
        } catch (Exception e) {
            log.error("大华设备预览异常，设备id：{}，通道号：{}", device.getId(), device.getChannel(), e);
            throw new RuntimeException(e);
        } finally {
            if (needCleanup) {
                cleanupResources(streamKey, rtpServerParam, lRealHandle, fRealDataCallBack);
            }
            latchMap.remove(streamKey);
        }
    }

    /**
     * 停止播放
     *
     * @param lLong
     * @param id
     * @param channel
     * @param streamKey
     */
    @Override
    public void stopPlay(NetSDKLib.LLong lLong, Long id, Integer channel, String streamKey) {
        RtpServerParam rtpServerParam = StreamManager.streamKeyAndRtpServerParamMap.get(streamKey);
        NetSDKLib.LLong realHandle = StreamManager.streamKeyAndRealHandleMap.get(streamKey);
        FRealDatarTPCallback fRealDataCallBack = StreamManager.streamKeyAndFRealDatarTPCallbackMap.get(streamKey);

        cleanupResources(streamKey, rtpServerParam, realHandle, fRealDataCallBack);

        CountDownLatch latch = latchMap.get(streamKey);
        if (latch != null) {
            latch.countDown();
            log.info("结束预览实例: {}", streamKey);
        }

        latchMap.remove(streamKey);
        log.info("停止预览，设备id：{}，通道号：{}", id, channel);
    }

    /**
     * 统一资源清理方法
     */
    public void cleanupResources(String streamKey, RtpServerParam rtpServerParam, 
                                   NetSDKLib.LLong realHandle, FRealDatarTPCallback fRealDataCallBack) {
        try {
            if (realHandle != null && realHandle.longValue() != 0) {
                DaHuaServiceImpl.netsdk.CLIENT_StopRealPlayEx(realHandle);
            }
        } catch (Exception e) {
            log.error("[大华设备] 停止预览失败，streamKey：{}", streamKey, e);
        }

        try {
            if (fRealDataCallBack != null) {
                fRealDataCallBack.close();
            }
        } catch (Exception e) {
            log.error("[大华设备] 关闭回调失败，streamKey：{}", streamKey, e);
        }

        StreamManager.streamKeyAndRealHandleMap.remove(streamKey);
        StreamManager.streamKeyAndFRealDatarTPCallbackMap.remove(streamKey);
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
            log.info("[大华设备] 清理zlm资源，streamKey：{}，ssrc：{}", streamKey, rtpServerParam.getSsrc());
            remoteZlmService.releaseSsrc(rtpServerParam.getMediaServerId(), rtpServerParam.getSsrc(), SecurityConstants.INNER);
            remoteZlmService.closeRTPServer(rtpServerParam.getMediaServerId(), rtpServerParam, SecurityConstants.INNER);
        } catch (Exception e) {
            log.error("[大华设备] 清理zlm资源失败，streamKey：{}", streamKey, e);
        }
    }
}
