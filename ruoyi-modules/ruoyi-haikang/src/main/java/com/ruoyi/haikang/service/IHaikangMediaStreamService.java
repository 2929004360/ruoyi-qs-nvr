package com.ruoyi.haikang.service;

import com.ruoyi.haikang.api.domain.RtpServerParam;
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
}
