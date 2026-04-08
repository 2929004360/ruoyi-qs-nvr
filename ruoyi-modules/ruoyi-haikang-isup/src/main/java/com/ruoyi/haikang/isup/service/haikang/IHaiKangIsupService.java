package com.ruoyi.haikang.isup.service.haikang;

import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.isup.api.domain.HaiKangIsupDeviceInfo;

/**
 * 海康isup 服务接口
 * @FileName IHaiKangIsupService
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
public interface IHaiKangIsupService {

    /**
     * 获取设备信息
     *
     * @param lUserID 用户id
     * @return
     */
    HaiKangIsupDeviceInfo getDevInfo(Integer lUserID);

    /**
     * 开始播放
     *
     * @param rtpServerParam
     */
    void startPlay(RtpServerParam rtpServerParam);

    /**
     * 停止播放
     *
     * @param id 设备id
     */
    void stopPlay(Long id);
}
