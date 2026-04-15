package com.ruoyi.qs.service.impl;

import com.ruoyi.qs.common.SystemAllInfo;
import com.ruoyi.qs.common.VideoManagerConstants;
import com.ruoyi.qs.service.IRedisCatchStorageService;
import com.ruoyi.qs.utils.DateUtil;
import com.ruoyi.qs.utils.SystemInfoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName RedisCatchStorageSericeImpl
 * @Description
 * @Author fengcheng
 * @date 2026-04-15
 **/
@Slf4j
@Component
public class RedisCatchStorageServiceImpl implements IRedisCatchStorageService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 获取系统信息
     *
     * @return
     */
    @Override
    public SystemAllInfo getSystemInfo() {
        String cpuKey = VideoManagerConstants.SYSTEM_INFO_CPU_PREFIX;
        String memKey = VideoManagerConstants.SYSTEM_INFO_MEM_PREFIX;
        String netKey = VideoManagerConstants.SYSTEM_INFO_NET_PREFIX;
        String diskKey = VideoManagerConstants.SYSTEM_INFO_DISK_PREFIX;
        SystemAllInfo systemAllInfo = new SystemAllInfo();
        systemAllInfo.setCpu(redisTemplate.opsForList().range(cpuKey, 0, -1));
        systemAllInfo.setMem(redisTemplate.opsForList().range(memKey, 0, -1));
        systemAllInfo.setNet(redisTemplate.opsForList().range(netKey, 0, -1));

        systemAllInfo.setDisk(redisTemplate.opsForValue().get(diskKey));
        systemAllInfo.setNetTotal(SystemInfoUtils.getNetworkTotal());
        return systemAllInfo;
    }

    @Override
    public void addCpuInfo(double cpuInfo) {
        String key = VideoManagerConstants.SYSTEM_INFO_CPU_PREFIX;
        Map<String, String> infoMap = new HashMap<>();
        infoMap.put("time", DateUtil.getNow());
        infoMap.put("data", String.valueOf(cpuInfo));
        redisTemplate.opsForList().rightPush(key, infoMap);
        // 每秒一个，最多只存30个
        Long size = redisTemplate.opsForList().size(key);
        if (size != null && size >= 30) {
            for (int i = 0; i < size - 30; i++) {
                redisTemplate.opsForList().leftPop(key);
            }
        }
    }

    @Override
    public void addMemInfo(double memInfo) {
        String key = VideoManagerConstants.SYSTEM_INFO_MEM_PREFIX;
        Map<String, String> infoMap = new HashMap<>();
        infoMap.put("time", DateUtil.getNow());
        infoMap.put("data", String.valueOf(memInfo));
        redisTemplate.opsForList().rightPush(key, infoMap);
        // 每秒一个，最多只存30个
        Long size = redisTemplate.opsForList().size(key);
        if (size != null && size >= 30) {
            for (int i = 0; i < size - 30; i++) {
                redisTemplate.opsForList().leftPop(key);
            }
        }
    }

    @Override
    public void addNetInfo(Map<String, Double> networkInterfaces) {
        String key = VideoManagerConstants.SYSTEM_INFO_NET_PREFIX;
        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("time", DateUtil.getNow());
        for (String netKey : networkInterfaces.keySet()) {
            infoMap.put(netKey, networkInterfaces.get(netKey));
        }
        redisTemplate.opsForList().rightPush(key, infoMap);
        // 每秒一个，最多只存30个
        Long size = redisTemplate.opsForList().size(key);
        if (size != null && size >= 30) {
            for (int i = 0; i < size - 30; i++) {
                redisTemplate.opsForList().leftPop(key);
            }
        }
    }

    @Override
    public void addDiskInfo(List<Map<String, Object>> diskInfo) {
        String key = VideoManagerConstants.SYSTEM_INFO_DISK_PREFIX;
        redisTemplate.opsForValue().set(key, diskInfo);
    }
}
