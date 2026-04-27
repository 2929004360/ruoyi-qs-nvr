package com.ruoyi.gb28181.service.impl;

import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceChannel;
import com.ruoyi.gb28181.service.IDeviceChannelService;
import com.ruoyi.gb28181.service.IRedisCatchStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
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

        redisCatchStorage.batchAdd(device.getDeviceId(), mergeWithNewChannels(deviceChannelsInRedis, channels, device.isOnLine()));
    }

    /**
     * 合并数据库通道与新拉取的通道列表。
     * <p>
     * 规则：
     * - 以 deviceId 作为唯一标识。
     * - 数据库中已存在的 deviceId：更新除 status 以外的其他数据，保留原有的 status
     * - 新数据中独有的 deviceId 被追加到结果中
     * - 对于新通道且设备在线，状态默认为 "ON"
     *
     * @param channels          数据库中的通道列表（旧数据）
     * @param deviceChannelList 新拉取的通道列表（新数据）
     * @param isDeviceOnline    设备是否在线
     * @return 合并后的新列表
     */
    public static List<DeviceChannel> mergeWithNewChannels(
            List<DeviceChannel> channels,
            List<DeviceChannel> deviceChannelList,
            boolean isDeviceOnline) {

        // 1. 如果新拉取的数据是 null 或空，说明没有新数据，业务上通常意味着要清空旧缓存
        if (deviceChannelList == null || deviceChannelList.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. 如果旧数据为空，直接返回新数据，对于设备在线的情况给新通道默认状态为 "ON"
        if (channels == null || channels.isEmpty()) {
            List<DeviceChannel> result = new ArrayList<>(deviceChannelList);
            if (isDeviceOnline) {
                for (DeviceChannel channel : result) {
                    if (channel.getStatus() == null) {
                        channel.setStatus("ON");
                    }
                }
            }
            return result;
        }

        // 3. 正常合并逻辑
        // 先把旧通道映射为 map，方便查找
        Map<String, DeviceChannel> existingChannelMap = channels.stream()
                .collect(Collectors.toMap(DeviceChannel::getDeviceId, channel -> channel));

        List<DeviceChannel> result = new ArrayList<>();

        // 遍历新数据，进行合并
        for (DeviceChannel newChannel : deviceChannelList) {
            DeviceChannel existingChannel = existingChannelMap.get(newChannel.getDeviceId());
            if (existingChannel != null) {
                // 已存在的通道：保留原 status，更新其他字段
                String originalStatus = existingChannel.getStatus();
                
                // 把新数据的值复制到旧对象上（除了 status）
                // 这里我们创建一个新对象来合并
                DeviceChannel mergedChannel = newChannel;
                mergedChannel.setStatus(originalStatus);
                
                // 保留原有的 id
                mergedChannel.setId(existingChannel.getId());
                
                result.add(mergedChannel);
                
                // 从 map 中移除，剩下的就是在旧列表中有但在新列表中没有的
                existingChannelMap.remove(newChannel.getDeviceId());
            } else {
                // 新通道：添加，如果设备在线且状态为空，默认设为 "ON"
                if (isDeviceOnline && newChannel.getStatus() == null) {
                    newChannel.setStatus("ON");
                }
                result.add(newChannel);
            }
        }

        // 对于旧列表中有但新列表中没有的通道，我们保留它们
        result.addAll(existingChannelMap.values());

        return result;
    }
}
