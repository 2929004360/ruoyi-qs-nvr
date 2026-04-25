package com.ruoyi.jt1078.server.service;

import com.ruoyi.jt1078.server.model.entity.DeviceDO;

import java.util.List;

public interface IRedisCatchStorage {
    /**
     * 新增设备
     *
     * @param device 设备信息
     */
    void addDevice(DeviceDO device);

    /**
     * 获取设备
     *
     * @param mobileNo 手机号
     * @return
     */
    DeviceDO getDevice(String mobileNo);

    /**
     * 修改设备
     *
     * @param device 设备信息
     */
    void updateDevice(DeviceDO device);

    /**
     * 获取所有设备
     *
     * @return
     */
    List<DeviceDO> getAllDevice();
}
