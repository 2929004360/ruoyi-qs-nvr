package com.ruoyi.qs.service;

import com.ruoyi.qs.common.SystemAllInfo;

import java.util.List;
import java.util.Map;

/**
 * @FileName IRedisCatchStorage
 * @Description
 * @Author fengcheng
 * @date 2026-04-15
 **/
public interface IRedisCatchStorageService {

    /**
     * 获取系统信息
     *
     * @return
     */
    SystemAllInfo getSystemInfo();

    void addCpuInfo(double cpuInfo);

    void addMemInfo(double memInfo);

    void addNetInfo(Map<String, Double> networkInterfaces);

    void addDiskInfo(List<Map<String, Object>> diskInfo);
}
