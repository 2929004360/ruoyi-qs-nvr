package com.ruoyi.gb28181.controller;

import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.service.IDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/device")
public class Gb28181Controller {

    @Autowired
    private IDeviceService deviceService;

    /**
     * 获取所有国标设备
     *
     * @return 设备列表
     */
    @GetMapping("/getAllDevices")
    public AjaxResult getAllDevices() {
        return AjaxResult.success(deviceService.getAllDevices());
    }

    /**
     * 根据国标设备获取所有通道
     *
     * @param gbDeviceId 设备编号
     * @return 通道列表
     */
    @GetMapping("/getChannelsByDeviceId/{gbDeviceId}")
    public AjaxResult getChannelsByDeviceId(@PathVariable String gbDeviceId) {
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            return AjaxResult.error("gb28181 设备不存在 deviceId:" + gbDeviceId);
        }
        return AjaxResult.success(deviceService.getChannelsByDeviceId(gbDeviceId));
    }
}
