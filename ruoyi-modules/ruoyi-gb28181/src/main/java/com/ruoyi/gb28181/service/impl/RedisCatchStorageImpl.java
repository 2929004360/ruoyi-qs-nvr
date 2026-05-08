package com.ruoyi.gb28181.service.impl;

import com.ruoyi.gb28181.common.VideoManagerConstants;
import com.ruoyi.gb28181.config.UserSetting;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceChannel;
import com.ruoyi.gb28181.service.IRedisCatchStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("rawtypes")
@Slf4j
@Component
public class RedisCatchStorageImpl implements IRedisCatchStorage {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private UserSetting userSetting;

    /**
     * 查询设备信息
     *
     * @param deviceId 设备编号
     * @return 设备信息
     */
    @Override
    public Device getDevice(String deviceId) {
        String key = VideoManagerConstants.DEVICE_PREFIX;
        Object object = redisTemplate.opsForHash().get(key, deviceId);

        if (object == null) {
            return null;
        }
        return (Device) object;
    }

    /**
     * 将device信息写入redis
     *
     * @param device
     */
    @Override
    public void updateDevice(Device device) {
        String key = VideoManagerConstants.DEVICE_PREFIX;
        redisTemplate.opsForHash().put(key, device.getDeviceId(), device);
    }

    /**
     * 计数器。为cseq进行计数
     *
     * @return
     */
    @Override
    public Long getCSEQ() {
        String key = VideoManagerConstants.SIP_CSEQ_PREFIX + userSetting.getServerId();

        Long result = redisTemplate.opsForValue().increment(key, 1L);
        if (result != null && result > Integer.MAX_VALUE) {
            redisTemplate.opsForValue().set(key, 1);
            result = 1L;
        }
        return result;
    }

    /**
     * 根据设备id清除设备通道
     *
     * @param deviceId
     */
    @Override
    public void cleanChannelsForDevice(int deviceId) {
        String key = VideoManagerConstants.DEVICE_CHANNEL_PREFIX;
        redisTemplate.opsForHash().delete(key, deviceId);
    }

    /**
     * 查询全部通道刷新
     *
     * @param deviceId
     * @return
     */
    @Override
    public List<DeviceChannel> queryAllChannelsForRefresh(String deviceId) {
        String key = VideoManagerConstants.DEVICE_CHANNEL_PREFIX;
        Object object = redisTemplate.opsForHash().get(key, deviceId);
        if (object == null) {
            return new ArrayList<>();
        }
        return (List<DeviceChannel>) object;
    }

    /**
     * 批量添加设备通道
     *
     * @param deviceId
     * @param subList
     */
    @Override
    public void batchAdd(String deviceId, List<DeviceChannel> subList) {
        String key = VideoManagerConstants.DEVICE_CHANNEL_PREFIX;
        redisTemplate.opsForHash().put(key, deviceId, subList);
    }

    /**
     * 批量修改设备通道
     *
     * @param deviceId
     * @param subList
     */
    @Override
    public void batchUpdate(String deviceId, List<DeviceChannel> subList) {
        String key = VideoManagerConstants.DEVICE_CHANNEL_PREFIX;
        redisTemplate.opsForHash().put(key, deviceId, subList);
    }

    /**
     * 获取所有设备
     *
     * @return 设备列表
     */
    @Override
    public List<Device> getAllDevices() {
        String key = VideoManagerConstants.DEVICE_PREFIX;
        List<Object> values = redisTemplate.opsForHash().values(key);
        List<Device> devices = new ArrayList<>();
        for (Object value : values) {
            if (value instanceof Device) {
                devices.add((Device) value);
            }
        }
        return devices;
    }

    @Override
    public void setRecordList(String deviceId, String channelId, Object recordList) {
        String key = VideoManagerConstants.REDIS_RECORD_INFO_RES_PRE + deviceId + "_" + channelId;
        redisTemplate.opsForValue().set(key, recordList, 5, TimeUnit.MINUTES);
    }

    @Override
    public Object getRecordList(String deviceId, String channelId) {
        String key = VideoManagerConstants.REDIS_RECORD_INFO_RES_PRE + deviceId + "_" + channelId;
        return redisTemplate.opsForValue().get(key);
    }
}
