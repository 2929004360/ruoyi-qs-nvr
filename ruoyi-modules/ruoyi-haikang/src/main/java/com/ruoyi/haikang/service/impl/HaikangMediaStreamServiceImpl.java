package com.ruoyi.haikang.service.impl;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.callback.FPlayBackDataCallBack;
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
        CountDownLatch latch = latchMap.get(streamKey);
        if (latch != null) {
            latch.countDown();
            log.info("结束预览实例: {}", streamKey);
        }

        RtpServerParam rtpServerParam = StreamManager.streamKeyAndRtpServerParamMap.get(streamKey);
        Long realHandle = StreamManager.streamKeyAndRealHandleMap.get(streamKey);
        FRealDataForRtpOverTcpCallback fRealDataCallBack = StreamManager.streamKeyAndFRealDataForRtpOverTcpCallbackMap.get(streamKey);

        cleanupResources(streamKey, rtpServerParam, realHandle, fRealDataCallBack);

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

        long playbackHandle = -1;
        FPlayBackDataCallBack fPlayBackDataCallBack = null;
        boolean needCleanup = true;

        try {
            // 开始时间
            HCNetSDK.NET_DVR_TIME stTimeStart = new HCNetSDK.NET_DVR_TIME();
            // 结束时间
            HCNetSDK.NET_DVR_TIME stTimeEnd = new HCNetSDK.NET_DVR_TIME();

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

            log.info("开始回放海康设备, deviceId:{}, ip:{}, lUserID:{}, channel:{}, startTime:{}, endTime:{}",
                    device.getId(), device.getIpAddress(), lUserID,
                    device.getChannel(), rtpServerParam.getStartTime(), rtpServerParam.getEndTime());

            // 验证登录句柄
            if (lUserID == null || lUserID == 0) {
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

            // 构造回放参数
            HCNetSDK.NET_DVR_VOD_PARA vodPara = new HCNetSDK.NET_DVR_VOD_PARA();
            vodPara.dwSize = vodPara.size();
            
            // 设置通道
            vodPara.struIDInfo.dwSize = vodPara.struIDInfo.size();
            vodPara.struIDInfo.dwChannel = channelToUse;
            
            // 设置时间
            vodPara.struBeginTime = stTimeStart;
            vodPara.struEndTime = stTimeEnd;
            
            // 设置码流类型
            if ("1".equals(device.getStreamType())) {
                vodPara.byStreamType = 0; // 主码流
            } else {
                vodPara.byStreamType = 1; // 子码流
            }
            
            vodPara.write();

            log.info("调用 NET_DVR_PlayBackByTime_V40, lUserID:{}, channel:{}", lUserID, channelToUse);
            
            playbackHandle = client.hCNetSDK.NET_DVR_PlayBackByTime_V40(lUserID, vodPara);

            if (playbackHandle != -1) {
                // 设置回放数据回调
                boolean setCallbackResult = client.hCNetSDK.NET_DVR_SetPlayDataCallBack((int) playbackHandle, fPlayBackDataCallBack, 0);
                if (!setCallbackResult) {
                    log.error("设置回放数据回调失败, error:{}", client.hCNetSDK.NET_DVR_GetLastError());
                }
                
                // 开始回放
                boolean startResult = client.hCNetSDK.NET_DVR_PlayBackControl((int) playbackHandle, 1, 0, null);
                if (!startResult) {
                    log.error("开始回放失败, error:{}", client.hCNetSDK.NET_DVR_GetLastError());
                }
                
                StreamManager.playbackKeyAndPlaybackHandleMap.put(playbackKey, playbackHandle);
                StreamManager.playbackKeyAndFPlayBackDataCallBackMap.put(playbackKey, fPlayBackDataCallBack);

                log.info("海康设备回放成功, deviceId:{}, channel:{}, playbackKey:{}",
                        device.getId(), device.getChannel(), playbackKey);

                latch.await();
                needCleanup = false;
            } else {
                log.error("海康设备回放失败, deviceId:{}, channel:{}, error:{}",
                        device.getId(), device.getChannel(), client.hCNetSDK.NET_DVR_GetLastError());
                throw new RuntimeException("海康设备回放失败");
            }
        } catch (Exception e) {
            log.error("海康设备回放异常, deviceId:{}, channel:{}", device.getId(), device.getChannel(), e);
            throw new RuntimeException(e);
        } finally {
            if (needCleanup) {
                if (playbackHandle != -1) {
                    cleanupPlaybackResources(playbackKey, rtpServerParam, playbackHandle, fPlayBackDataCallBack);
                }
            }
            StreamManager.playbackKeyAndLatchMap.remove(playbackKey);
        }
    }

    /**
     * 停止回放
     *
     * @param lUserID
     * @param deviceId
     * @param channel
     * @param playbackKey
     */
    @Override
    public void stopPlayback(Integer lUserID, Long deviceId, Integer channel, String playbackKey) {
        CountDownLatch latch = StreamManager.playbackKeyAndLatchMap.get(playbackKey);
        if (latch != null) {
            latch.countDown();
            log.info("结束回放实例: {}", playbackKey);
        }

        RtpServerParam rtpServerParam = StreamManager.playbackKeyAndRtpServerParamMap.get(playbackKey);
        Long playbackHandle = StreamManager.playbackKeyAndPlaybackHandleMap.get(playbackKey);
        FPlayBackDataCallBack fPlayBackDataCallBack = StreamManager.playbackKeyAndFPlayBackDataCallBackMap.get(playbackKey);

        cleanupPlaybackResources(playbackKey, rtpServerParam, playbackHandle, fPlayBackDataCallBack);

        StreamManager.playbackKeyAndLatchMap.remove(playbackKey);
        log.info("停止回放, deviceId:{}, channel:{}", deviceId, channel);
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
                                         Long playbackHandle, FPlayBackDataCallBack fPlayBackDataCallBack) {
        try {
            if (playbackHandle != null && playbackHandle != -1) {
                client.hCNetSDK.NET_DVR_StopPlayBack(playbackHandle.intValue());
            }
        } catch (Exception e) {
            log.error("[海康设备] 停止回放失败, playbackKey:{}", playbackKey, e);
        }

        try {
            if (fPlayBackDataCallBack != null) {
                fPlayBackDataCallBack.close();
            }
        } catch (Exception e) {
            log.error("[海康设备] 关闭回放回调失败, playbackKey:{}", playbackKey, e);
        }

        StreamManager.playbackKeyAndPlaybackHandleMap.remove(playbackKey);
        StreamManager.playbackKeyAndFPlayBackDataCallBackMap.remove(playbackKey);
        StreamManager.playbackKeyAndRtpServerParamMap.remove(playbackKey);

        if (rtpServerParam != null) {
            cleanupPlaybackZlmResources(playbackKey, rtpServerParam);
        }
    }

    /**
     * 清理回放zlm资源
     *
     * @param playbackKey
     * @param rtpServerParam
     */
    private void cleanupPlaybackZlmResources(String playbackKey, RtpServerParam rtpServerParam) {
        try {
            log.info("[海康设备] 清理回放zlm资源, playbackKey:{}, ssrc:{}", playbackKey, rtpServerParam.getSsrc());
            remoteZlmService.releaseSsrc(rtpServerParam.getMediaServerId(), rtpServerParam.getSsrc(), SecurityConstants.INNER);
            remoteZlmService.closeRTPServer(rtpServerParam.getMediaServerId(), rtpServerParam, SecurityConstants.INNER);
        } catch (Exception e) {
            log.error("[海康设备] 清理回放zlm资源失败, playbackKey:{}", playbackKey, e);
        }
    }
}
