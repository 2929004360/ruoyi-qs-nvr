package com.ruoyi.dahua.service.impl;

import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.dahua.callback.FRealDatarTPCallback;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.manager.StreamManager;
import com.ruoyi.dahua.service.IDahuaMediaStreamService;
import com.ruoyi.qs.api.domain.QsDevice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        try {
            NetSDKLib.NET_IN_REALPLAY_BY_DATA_TYPE inParam = new NetSDKLib.NET_IN_REALPLAY_BY_DATA_TYPE();
            NetSDKLib.NET_OUT_REALPLAY_BY_DATA_TYPE outParam = new NetSDKLib.NET_OUT_REALPLAY_BY_DATA_TYPE();
            //这块需要动态传入获取视频流的通道id
            inParam.nChannelID = device.getChannel();

            if ("1".equals(device.getStreamType())) {
                //码流类型，0为实时预览
                inParam.rType = 2;
            } else if ("2".equals(device.getStreamType())) {
                //码流类型，0为实时预览
                inParam.rType = 3;
            } else {
                //码流类型，0为实时预览
                inParam.rType = 0;
            }

            //回调的数据类型
            inParam.emDataType = 1;
            //开始预览
            NetSDKLib.LLong lRealHandle = DaHuaServiceImpl.netsdk.CLIENT_RealPlayByDataType(lLong, inParam, outParam, 3000);

            if (lRealHandle.longValue() != 0) {

                FRealDatarTPCallback fRealDataCallBack = new FRealDatarTPCallback(
                        rtpServerParam.getIp(),
                        rtpServerParam.getPort(),
                        rtpServerParam.getSsrc()
                );

                //你的接收回调的类里的方法 注：这里使用的是官方demo中的RealplayEx类下的接口
                DaHuaServiceImpl.netsdk.CLIENT_SetRealDataCallBackEx(lRealHandle, fRealDataCallBack, null, 31);

                StreamManager.streamKeyAndRealHandleMap.put(streamKey, lRealHandle);
                StreamManager.streamKeyAndFRealDatarTPCallbackMap.put(streamKey, fRealDataCallBack);

                // 阻塞,调用 latch.countDown()
                latch.await();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
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
        // 停止预览
        NetSDKLib.LLong realHandle = StreamManager.streamKeyAndRealHandleMap.remove(streamKey);
        FRealDatarTPCallback fRealDatarTPCallback = StreamManager.streamKeyAndFRealDatarTPCallbackMap.remove(streamKey);
        if (realHandle != null && realHandle.longValue() != 0) {
            DaHuaServiceImpl.netsdk.CLIENT_StopRealPlayEx(realHandle);
        }

        if(fRealDatarTPCallback != null){
            fRealDatarTPCallback.close();
        }

        CountDownLatch latch = latchMap.get(streamKey);
        if (latch != null) {
            latch.countDown(); // 唤醒 preview
            log.info("结束预览实例: {}", streamKey);
        }

        latchMap.remove(streamKey);

        log.info("停止预览，设备id：{}，通道号：{}", id, channel);
    }
}
