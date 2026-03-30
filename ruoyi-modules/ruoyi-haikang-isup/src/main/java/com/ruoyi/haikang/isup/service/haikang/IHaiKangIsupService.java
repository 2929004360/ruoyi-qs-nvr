package com.ruoyi.haikang.isup.service.haikang;

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
}
