package com.ruoyi.haikang.service;

import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.callback.FPlayBackDataCallBack;
import com.ruoyi.haikang.callback.FRealDataForRtpOverTcpCallback;
import com.ruoyi.qs.api.domain.QsDevice;

/**
 * @FileName IHaikangMediaStreamService
 * @Description
 * @Author fengcheng
 * @date 2026-01-15
 **/
public interface IHaikangMediaStreamService {

    /**
     * 播放视频
     *
     * @param lUserID
     * @param device
     * @param streamKey
     * @param rtpServerParam
     */
    void startPlay(Integer lUserID, QsDevice device, String streamKey, RtpServerParam rtpServerParam);


    /**
     * 结束播放视频
     *
     * @param deviceId
     * @param channelId
     * @param streamKey
     */
    void endPlay(Long deviceId, int channelId, String streamKey);

    /**
     * 统一资源清理方法
     */
    void cleanupResources(String streamKey, RtpServerParam rtpServerParam, Long realHandle, FRealDataForRtpOverTcpCallback callback);

    /**
     * 开始回放
     *
     * @param lUserID
     * @param device
     * @param playbackKey
     * @param rtpServerParam
     */
    void startPlayback(Integer lUserID, QsDevice device, String playbackKey, RtpServerParam rtpServerParam);

    /**
     * 停止回放
     *
     * @param lUserID
     * @param deviceId
     * @param channel
     * @param playbackKey
     */
    void stopPlayback(Integer lUserID, Long deviceId, Integer channel, String playbackKey);

    /**
     * 统一回放资源清理方法
     *
     * @param playbackKey
     * @param rtpServerParam
     * @param playbackHandle
     * @param fPlayBackDataCallBack
     */
    void cleanupPlaybackResources(String playbackKey, RtpServerParam rtpServerParam, Long playbackHandle, FPlayBackDataCallBack fPlayBackDataCallBack);
}
