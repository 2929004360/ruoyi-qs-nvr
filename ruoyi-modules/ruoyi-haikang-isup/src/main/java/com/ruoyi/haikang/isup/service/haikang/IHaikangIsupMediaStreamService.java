package com.ruoyi.haikang.isup.service.haikang;

import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.qs.api.domain.QsDevice;

import java.io.File;

/**
 * @FileName IHaikangIsupMediaStreamService
 * @Description
 * @Author fengcheng
 * @date 2026-04-08
 **/
public interface IHaikangIsupMediaStreamService {

    /**
     * 开始播放
     *
     * @param lUserID
     * @param device
     * @param streamKey
     * @param rtpServerParam
     */
    void startPlay(Integer lUserID, QsDevice device, String streamKey, RtpServerParam rtpServerParam);

    /**
     * 停止播放
     *
     * @param lUserID
     * @param id
     * @param channel
     * @param streamKey
     */
    void stopPlay(Integer lUserID, Long id, Integer channel, String streamKey);

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
     * @param id
     * @param channel
     * @param playbackKey
     */
    void stopPlayback(Integer lUserID, Long id, Integer channel, String playbackKey);

    /**
     * 清理回放资源
     *
     * @param playbackKey
     * @param rtpServerParam
     */
    void cleanupPlaybackResources(String playbackKey, RtpServerParam rtpServerParam);

    /**
     * 按时间下载录像
     *
     * @param lUserID
     * @param device
     * @param channelId
     * @param startTime
     * @param endTime
     * @param filePath
     * @return
     */
    File downloadRecordByTime(Integer lUserID, QsDevice device, Integer channelId, String startTime, String endTime, String filePath);
}
