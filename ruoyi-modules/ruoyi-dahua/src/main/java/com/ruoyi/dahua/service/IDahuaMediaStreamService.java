package com.ruoyi.dahua.service;

import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.qs.api.domain.QsDevice;

/**
 * @FileName IDahuaMediaStreamService
 * @Description
 * @Author fengcheng
 * @date 2026-04-09
 **/
public interface IDahuaMediaStreamService {

    /**
     * 开始播放
     *
     * @param lLong
     * @param device
     * @param streamKey
     * @param rtpServerParam
     */
    void startPlay(NetSDKLib.LLong lLong, QsDevice device, String streamKey, RtpServerParam rtpServerParam);

    /**
     * 停止播放
     *
     * @param lLong
     * @param id
     * @param channel
     * @param streamKey
     */
    void stopPlay(NetSDKLib.LLong lLong, Long id, Integer channel, String streamKey);
}
