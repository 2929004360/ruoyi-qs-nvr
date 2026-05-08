package com.ruoyi.jt1078.server.service;

import com.ruoyi.jt1078.protocol.t1078.T1205;
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

    /**
     * 存储录像列表
     *
     * @param mobileNo 手机号
     * @param recordList 录像列表
     */
    void setRecordList(String mobileNo, T1205 recordList);

    /**
     * 获取录像列表
     *
     * @param mobileNo 手机号
     * @return
     */
    T1205 getRecordList(String mobileNo);
}
