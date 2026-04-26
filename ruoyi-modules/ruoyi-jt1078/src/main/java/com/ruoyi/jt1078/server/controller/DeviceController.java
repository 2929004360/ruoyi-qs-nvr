package com.ruoyi.jt1078.server.controller;

import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.jt1078.server.model.entity.DeviceDO;
import com.ruoyi.jt1078.server.service.IRedisCatchStorage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
public class DeviceController {

    private final IRedisCatchStorage redisCatchStorage;

    @Operation(summary = "获取全部设备")
    @GetMapping("allList")
    public AjaxResult getAllDevice() {
        return AjaxResult.success(redisCatchStorage.getAllDevice());
    }
}
