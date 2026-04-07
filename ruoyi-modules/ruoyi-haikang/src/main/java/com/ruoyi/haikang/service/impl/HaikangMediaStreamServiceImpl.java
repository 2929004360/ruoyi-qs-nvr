package com.ruoyi.haikang.service.impl;

import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.haikang.api.domain.RtpServerParam;
import com.ruoyi.haikang.callback.FRealDataForRtpOverTcpCallback;
import com.ruoyi.haikang.manager.StreamManager;
import com.ruoyi.haikang.net.Client;
import com.ruoyi.haikang.net.HCNetSDK;
import com.ruoyi.haikang.service.IHaikangMediaStreamService;
import com.ruoyi.qs.api.domain.QsDevice;
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

        try {
            FRealDataForRtpOverTcpCallback fRealDataCallBack = new FRealDataForRtpOverTcpCallback(
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
            long ret = client.hCNetSDK.NET_DVR_RealPlay_V40(lUserID, netDvrPreviewinfo, fRealDataCallBack, Pointer.NULL);
            if (ret == -1) {
                throw new ServiceException("开始sdk播放视频失败! 错误码：" + client.hCNetSDK.NET_DVR_GetLastError());
            }

            StreamManager.streamKeyAndRealHandleMap.put(streamKey, ret);
            StreamManager.streamKeyAndFRealDataForRtpOverTcpCallbackMap.put(streamKey, fRealDataCallBack);

            // 阻塞,调用 latch.countDown()
            latch.await();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
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
        Long ret = StreamManager.streamKeyAndRealHandleMap.remove(streamKey);
        if (ret != null && ret != -1) {
            client.hCNetSDK.NET_DVR_StopRealPlay(Math.toIntExact(ret));
        }

        FRealDataForRtpOverTcpCallback fRealDataCallBack = StreamManager.streamKeyAndFRealDataForRtpOverTcpCallbackMap.remove(streamKey);
        if (fRealDataCallBack != null) {
            fRealDataCallBack.close();
        }

        CountDownLatch latch = latchMap.remove(streamKey);
        if (latch != null) {
            latch.countDown(); // 唤醒 preview
        }

        log.info("停止预览，设备id：{}，通道号：{}", deviceId, channelId);
    }
}
