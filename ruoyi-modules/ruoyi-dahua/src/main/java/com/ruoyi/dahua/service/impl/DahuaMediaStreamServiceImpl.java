package com.ruoyi.dahua.service.impl;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.dahua.callback.FPlayBackDataCallBack;
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
        CountDownLatch latch = latchMap.get(streamKey);
        if (latch != null) {
            latch.countDown();
            log.info("结束预览实例: {}", streamKey);
        }

        RtpServerParam rtpServerParam = StreamManager.streamKeyAndRtpServerParamMap.get(streamKey);
        NetSDKLib.LLong realHandle = StreamManager.streamKeyAndRealHandleMap.get(streamKey);
        FRealDatarTPCallback fRealDataCallBack = StreamManager.streamKeyAndFRealDatarTPCallbackMap.get(streamKey);

        cleanupResources(streamKey, rtpServerParam, realHandle, fRealDataCallBack);
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

    /**
     * 开始回放
     *
     * @param lLong
     * @param device
     * @param playbackKey
     * @param rtpServerParam
     */
    @Async("taskExecutor")
    @Override
    public void startPlayback(NetSDKLib.LLong lLong, QsDevice device, String playbackKey, RtpServerParam rtpServerParam) {
        if (StreamManager.playbackKeyAndLatchMap.containsKey(playbackKey)) {
            log.info("回放通道已在运行，忽略重复开启: {}", playbackKey);
            return;
        }

        java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(1);
        StreamManager.playbackKeyAndLatchMap.put(playbackKey, latch);
        StreamManager.playbackKeyAndRtpServerParamMap.put(playbackKey, rtpServerParam);

        NetSDKLib.LLong lPlaybackHandle = null;
        FPlayBackDataCallBack fPlayBackDataCallBack = null;
        boolean needCleanup = true;

        try {
            // 开始时间
            NetSDKLib.NET_TIME stTimeStart = new NetSDKLib.NET_TIME();
            // 结束时间
            NetSDKLib.NET_TIME stTimeEnd = new NetSDKLib.NET_TIME();

            // 开始时间
            String[] dateStartByFile = rtpServerParam.getStartTime().split(" ");
            String[] dateStart1 = dateStartByFile[0].split("-");
            String[] dateStart2 = dateStartByFile[1].split(":");

            stTimeStart.dwYear = Integer.parseInt(dateStart1[0]);
            stTimeStart.dwMonth = Integer.parseInt(dateStart1[1]);
            stTimeStart.dwDay = Integer.parseInt(dateStart1[2]);

            stTimeStart.dwHour = Integer.parseInt(dateStart2[0]);
            stTimeStart.dwMinute = Integer.parseInt(dateStart2[1]);
            stTimeStart.dwSecond = Integer.parseInt(dateStart2[2]);


            // 结束时间
            String[] dateEndByFile = rtpServerParam.getEndTime().split(" ");
            String[] dateEnd1 = dateEndByFile[0].split("-");
            String[] dateEnd2 = dateEndByFile[1].split(":");

            stTimeEnd.dwYear = Integer.parseInt(dateEnd1[0]);
            stTimeEnd.dwMonth = Integer.parseInt(dateEnd1[1]);
            stTimeEnd.dwDay = Integer.parseInt(dateEnd1[2]);

            stTimeEnd.dwHour = Integer.parseInt(dateEnd2[0]);
            stTimeEnd.dwMinute = Integer.parseInt(dateEnd2[1]);
            stTimeEnd.dwSecond = Integer.parseInt(dateEnd2[2]);

            log.info("开始回放大华设备, deviceId:{}, ip:{}, loginId:{}, channel:{}, startTime:{}, endTime:{}",
                    device.getId(), device.getIpAddress(), lLong.longValue(),
                    device.getChannel(), rtpServerParam.getStartTime(), rtpServerParam.getEndTime());
            
            // 验证登录句柄
            if (lLong == null || lLong.longValue() == 0) {
                log.error("登录句柄无效, deviceId:{}", device.getId());
                throw new RuntimeException("登录句柄无效");
            }

            int channelToUse = device.getChannel();

            // 创建RTP回调
            fPlayBackDataCallBack = new FPlayBackDataCallBack(
                    rtpServerParam.getIp(),
                    rtpServerParam.getPort(),
                    rtpServerParam.getSsrc()
            );

            log.info("调用 CLIENT_PlayBackByDataType, loginId:{}, channel:{}, startTime:{}, endTime:{}", 
                    lLong.longValue(), channelToUse, 
                    rtpServerParam.getStartTime(), rtpServerParam.getEndTime());
            
            NetSDKLib.NET_IN_PLAYBACK_BY_DATA_TYPE stIn = new NetSDKLib.NET_IN_PLAYBACK_BY_DATA_TYPE();
            stIn.emDataType = NetSDKLib.EM_REAL_DATA_TYPE.EM_REAL_DATA_TYPE_GBPS;
            stIn.nChannelID = channelToUse;
            stIn.stStartTime = stTimeStart;
            stIn.stStopTime = stTimeEnd;
            stIn.nPlayDirection = 0;
            stIn.cbDownLoadPos = DaHuaServiceImpl.PlayBackPosCallBack.getInstance();
            stIn.dwPosUser = null;
            stIn.fDownLoadDataCallBack = fPlayBackDataCallBack;
            stIn.dwDataUser = null;

            NetSDKLib.NET_OUT_PLAYBACK_BY_DATA_TYPE stOut = new NetSDKLib.NET_OUT_PLAYBACK_BY_DATA_TYPE();
            lPlaybackHandle = DaHuaServiceImpl.netsdk.CLIENT_PlayBackByDataType(lLong, stIn, stOut, 10000);

            if (lPlaybackHandle.longValue() != 0) {
                StreamManager.playbackKeyAndPlaybackHandleMap.put(playbackKey, lPlaybackHandle);
                StreamManager.playbackKeyAndFPlayBackDataCallBackMap.put(playbackKey, fPlayBackDataCallBack);

                log.info("大华设备回放成功, deviceId:{}, channel:{}, playbackKey:{}",
                        device.getId(), device.getChannel(), playbackKey);

                latch.await();
                needCleanup = false;
            } else {
                log.error("大华设备回放失败, deviceId:{}, channel:{}, error:{}",
                        device.getId(), device.getChannel(), DaHuaServiceImpl.getErrorCodePrint());
                throw new RuntimeException("大华设备回放失败");
            }
        } catch (Exception e) {
            log.error("大华设备回放异常, deviceId:{}, channel:{}", device.getId(), device.getChannel(), e);
            throw new RuntimeException(e);
        } finally {
            if (needCleanup) {
                if(lPlaybackHandle != null){
                    cleanupPlaybackResources(playbackKey, rtpServerParam, lPlaybackHandle, fPlayBackDataCallBack);
                }
            }
            StreamManager.playbackKeyAndLatchMap.remove(playbackKey);
        }
    }

    /**
     * 停止回放
     *
     * @param lLong
     * @param id
     * @param channel
     * @param playbackKey
     */
    @Override
    public void stopPlayback(NetSDKLib.LLong lLong, Long id, Integer channel, String playbackKey) {
        java.util.concurrent.CountDownLatch latch = StreamManager.playbackKeyAndLatchMap.get(playbackKey);
        if (latch != null) {
            latch.countDown();
            log.info("结束回放实例: {}", playbackKey);
        }

        RtpServerParam rtpServerParam = StreamManager.playbackKeyAndRtpServerParamMap.get(playbackKey);
        NetSDKLib.LLong playbackHandle = StreamManager.playbackKeyAndPlaybackHandleMap.get(playbackKey);
        FPlayBackDataCallBack fPlayBackDataCallBack = StreamManager.playbackKeyAndFPlayBackDataCallBackMap.get(playbackKey);

        cleanupPlaybackResources(playbackKey, rtpServerParam, playbackHandle, fPlayBackDataCallBack);
        log.info("停止回放, deviceId:{}, channel:{}", id, channel);
    }

    /**
     * 统一回放资源清理方法
     *
     * @param playbackKey
     * @param rtpServerParam
     * @param playbackHandle
     * @param fPlayBackDataCallBack
     */
    @Override
    public void cleanupPlaybackResources(String playbackKey, RtpServerParam rtpServerParam,
                                         NetSDKLib.LLong playbackHandle, FPlayBackDataCallBack fPlayBackDataCallBack) {
        try {
            if (playbackHandle != null && playbackHandle.longValue() != 0) {
                DaHuaServiceImpl.netsdk.CLIENT_StopPlayBack(playbackHandle);
            }
        } catch (Exception e) {
            log.error("[大华设备] 停止回放失败, playbackKey:{}", playbackKey, e);
        }

        try {
            if (fPlayBackDataCallBack != null) {
                fPlayBackDataCallBack.close();
            }
        } catch (Exception e) {
            log.error("[大华设备] 关闭回调失败, playbackKey:{}", playbackKey, e);
        }

        StreamManager.playbackKeyAndPlaybackHandleMap.remove(playbackKey);
        StreamManager.playbackKeyAndFPlayBackDataCallBackMap.remove(playbackKey);
        StreamManager.playbackKeyAndRtpServerParamMap.remove(playbackKey);

        if (rtpServerParam != null) {
            try {
                log.info("[大华设备] 清理回放zlm资源, playbackKey:{}, ssrc:{}", playbackKey, rtpServerParam.getSsrc());
                remoteZlmService.releaseSsrc(rtpServerParam.getMediaServerId(), rtpServerParam.getSsrc(), SecurityConstants.INNER);
                remoteZlmService.closeRTPServer(rtpServerParam.getMediaServerId(), rtpServerParam, SecurityConstants.INNER);
            } catch (Exception e) {
                log.error("[大华设备] 清理回放zlm资源失败, playbackKey:{}", playbackKey, e);
            }
        }
    }
}
