package com.ruoyi.qs.task;

import com.ruoyi.qs.service.IRedisCatchStorageService;
import com.ruoyi.qs.utils.SystemInfoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 获取系统信息写入redis
 */
@Slf4j
@Component
public class SystemInfoTimerTask {

    @Autowired
    private IRedisCatchStorageService redisCatchStorageService;

    @Scheduled(fixedRate = 2000)   //每1秒执行一次
    public void execute() {
        try {
            double cpuInfo = SystemInfoUtils.getCpuInfo();
            redisCatchStorageService.addCpuInfo(cpuInfo);
            double memInfo = SystemInfoUtils.getMemInfo();
            redisCatchStorageService.addMemInfo(memInfo);
            Map<String, Double> networkInterfaces = SystemInfoUtils.getNetworkInterfaces();
            redisCatchStorageService.addNetInfo(networkInterfaces);
            List<Map<String, Object>> diskInfo = SystemInfoUtils.getDiskInfo();
            redisCatchStorageService.addDiskInfo(diskInfo);
        } catch (InterruptedException e) {
            log.error("[获取系统信息失败] {}", e.getMessage());
        }
    }
}
