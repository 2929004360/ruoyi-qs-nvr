package com.ruoyi.jt1078.server.service.impl;

import com.ruoyi.jt1078.commons.constants.VideoManagerConstants;
import com.ruoyi.jt1078.server.model.entity.DeviceDO;
import com.ruoyi.jt1078.server.service.IRedisCatchStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class RedisCatchStorageImpl implements IRedisCatchStorage {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 新增设备
     *
     * @param device 设备信息
     */
    @Override
    public void addDevice(DeviceDO device) {
        String key = VideoManagerConstants.DEVICE_PREFIX;
        redisTemplate.opsForHash().put(key, device.getMobileNo(), device);
    }

    /**
     * 获取设备
     *
     * @param mobileNo 手机号
     * @return
     */
    @Override
    public DeviceDO getDevice(String mobileNo) {
        String key = VideoManagerConstants.DEVICE_PREFIX;
        Object object = redisTemplate.opsForHash().get(key, mobileNo);

        if (object == null) {
            return null;
        }
        return (DeviceDO) object;
    }

    /**
     * 修改设备
     *
     * @param device 设备信息
     */
    @Override
    public void updateDevice(DeviceDO device) {
        String key = VideoManagerConstants.DEVICE_PREFIX;
        redisTemplate.opsForHash().put(key, device.getMobileNo(), device);
    }

    /**
     * 获取所有设备
     *
     * @return
     */
    @Override
    public List<DeviceDO> getAllDevice() {
        String key = VideoManagerConstants.DEVICE_PREFIX;
        List<Object> values = redisTemplate.opsForHash().values(key);
        if (values.isEmpty()) {
            return new ArrayList<>();
        }
        List<DeviceDO> deviceList = new ArrayList<>();

        for (Object obj : values) {
            if (obj instanceof DeviceDO) {
                deviceList.add((DeviceDO) obj);
            }
        }
        return deviceList;
    }
}
