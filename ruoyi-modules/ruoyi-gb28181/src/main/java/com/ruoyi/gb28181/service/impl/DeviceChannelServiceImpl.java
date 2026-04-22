package com.ruoyi.gb28181.service.impl;

import com.ruoyi.gb28181.domain.Device;
import com.ruoyi.gb28181.domain.DeviceChannel;
import com.ruoyi.gb28181.service.IDeviceChannelService;
import com.ruoyi.gb28181.service.IRedisCatchStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lin
 */
@Slf4j
@Service
public class DeviceChannelServiceImpl implements IDeviceChannelService {

    @Autowired
    private IRedisCatchStorage redisCatchStorage;

    /**
     * 根据设备id清楚设备通道
     *
     * @param deviceId
     */
    @Override
    public void cleanChannelsForDevice(int deviceId) {
        redisCatchStorage.cleanChannelsForDevice(deviceId);
    }

    /**
     * 批量添加设备通道
     *
     * @param device
     * @param channels
     * @return
     */
    @Override
    public void updateChannels(Device device, List<DeviceChannel> channels) {
        if (CollectionUtils.isEmpty(channels)) {
            return;
        }
        List<DeviceChannel> deviceChannelsInRedis = redisCatchStorage.queryAllChannelsForRefresh(device.getDeviceId());

        redisCatchStorage.batchAdd(device.getDeviceId(), mergeWithNewChannels(deviceChannelsInRedis, channels));
    }

    /**
     * 合并数据库通道与新拉取的通道列表。
     * <p>
     * 规则：
     * - 以 deviceId 作为唯一标识。
     * - 数据库中已存在的 deviceId 保持不变（不被新数据覆盖）。
     * - 新数据中独有的 deviceId 被追加到结果中。
     *
     * @param channels          数据库中的通道列表（旧数据）
     * @param deviceChannelList 新拉取的通道列表（新数据）
     * @return 合并后的新列表：[数据库所有数据 + 新增的通道]
     */
    public static List<DeviceChannel> mergeWithNewChannels(
            List<DeviceChannel> channels,
            List<DeviceChannel> deviceChannelList) {

        // 处理 null 情况
        if (channels == null) channels = Collections.emptyList();
        if (deviceChannelList == null) deviceChannelList = Collections.emptyList();

        // 1. 提取数据库中所有已存在的 deviceId
        Set<String> existingDeviceIds = channels.stream()
                .map(DeviceChannel::getDeviceId)
                .collect(Collectors.toSet());

        // 2. 创建结果列表，先放入所有数据库中的数据
        List<DeviceChannel> result = new ArrayList<>(channels);

        // 3. 遍历新数据，只添加 deviceId 不存在于数据库中的项
        for (DeviceChannel newChannel : deviceChannelList) {
            if (!existingDeviceIds.contains(newChannel.getDeviceId())) {
                result.add(newChannel);
            }
        }

        return result;
    }
}
