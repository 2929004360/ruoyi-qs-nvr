package com.ruoyi.dahua.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.dahua.api.domain.DahuaDevice;
import com.ruoyi.dahua.runner.DahuaCommandLineRunnerImpl;
import com.ruoyi.dahua.service.IDaHuaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 大华sdk Controller
 *
 * @FileName DaHuaController
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
@RestController
@RequestMapping("/device")
public class DaHuaController extends BaseController {

    @Autowired
    private IDaHuaService daHuaService;

    @Autowired
    private DahuaCommandLineRunnerImpl commandLineRunnerimpl;

    /**
     * 获取自动注册设备列表
     */
    @GetMapping("/list")
    public AjaxResult list() {
        // 自动注册设备列表
        List<DahuaDevice> registerDeviceList = commandLineRunnerimpl.getRegisterDeviceList();
        return success(registerDeviceList);
    }
}
