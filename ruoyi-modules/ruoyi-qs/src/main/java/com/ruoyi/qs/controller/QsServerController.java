package com.ruoyi.qs.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.qs.common.SystemAllInfo;
import com.ruoyi.qs.domain.DeviceStats;
import com.ruoyi.qs.service.IQsDeviceService;
import com.ruoyi.qs.service.IRedisCatchStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务控制
 *
 * @FileName QsServerController
 * @Description
 * @Author fengcheng
 * @date 2026-04-15
 **/
@Slf4j
@RestController
@RequestMapping("/server")
public class QsServerController extends BaseController {

    @Autowired
    private IRedisCatchStorageService redisCatchStorageService;

    @Autowired
    private IQsDeviceService qsDeviceService;

    /**
     * 获取系统信息
     *
     * @return
     */
    @GetMapping(value = "/system/info")
    public AjaxResult getSystemInfo() {
        SystemAllInfo systemAllInfo = redisCatchStorageService.getSystemInfo();
        return success(systemAllInfo);
    }

    /**
     * 获取设备统计信息
     *
     * @return
     */
    @GetMapping(value = "/system/deviceStatist")
    public AjaxResult getDeviceStatistics() {
        DeviceStats deviceStats = qsDeviceService.getDeviceStatistics();
        return success(deviceStats);
    }

}
