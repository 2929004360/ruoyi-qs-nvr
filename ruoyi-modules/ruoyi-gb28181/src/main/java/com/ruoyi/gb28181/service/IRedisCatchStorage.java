package com.ruoyi.gb28181.service;

import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceChannel;

import java.util.List;

public interface IRedisCatchStorage {

    /**
     * 查询设备信息
     *
     * @param deviceId 设备编号
     * @return 设备信息
     */
    Device getDevice(String deviceId);

    /**
     * 将device信息写入redis
     *
     * @param device
     */
    void updateDevice(Device device);

    /**
     * 计数器。为cseq进行计数
     *
     * @return
     */
    Long getCSEQ();

    /**
     * 根据设备id清除设备通道
     *
     * @param deviceId
     */
    void cleanChannelsForDevice(int deviceId);

    /**
     * 查询全部通道刷新
     *
     * @param deviceId
     * @return
     */
    List<DeviceChannel> queryAllChannelsForRefresh(String deviceId);

    /**
     * 批量添加设备通道
     *
     * @param deviceId
     * @param subList
     */
    void batchAdd(String deviceId, List<DeviceChannel> subList);

    /**
     * 批量修改设备通道
     *
     * @param deviceId
     * @param subList
     */
    void batchUpdate(String deviceId, List<DeviceChannel> subList);

    /**
     * 获取所有设备
     *
     * @return 设备列表
     */
    List<Device> getAllDevices();
}
