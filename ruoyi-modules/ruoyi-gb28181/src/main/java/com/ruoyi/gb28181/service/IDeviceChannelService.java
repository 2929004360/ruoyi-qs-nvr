package com.ruoyi.gb28181.service;

import com.ruoyi.gb28181.domain.Device;
import com.ruoyi.gb28181.domain.DeviceChannel;

import java.util.List;

/**
 * 国标通道业务类
 * @author lin
 */
public interface IDeviceChannelService {

    /**
     * 根据设备id清楚设备通道
     *
     * @param deviceId
     */
    void cleanChannelsForDevice(int deviceId);

    /**
     * 批量添加设备通道
     *
     * @param device
     * @param channels
     * @return
     */
    void updateChannels(Device device, List<DeviceChannel> channels);
}
