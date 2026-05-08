package com.ruoyi.dahua.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.dahua.api.domain.DahuaDevice;
import com.ruoyi.dahua.runner.DahuaCommandLineRunnerImpl;
import com.ruoyi.dahua.service.IDaHuaService;

import jakarta.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
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

    /**
     * 大华设备查询录像
     */
    @GetMapping("/queryRecord/{id}/{channelId}")
    public R<ArrayList<HashMap<String, Object>>> queryRecord(@PathVariable Long id, @PathVariable int channelId, @NotBlank(message = "开始时间不能为空") String startTime, @NotBlank(message = "结束时间不能为空") String endTime) {
        return R.ok(daHuaService.queryRecord(id, channelId, startTime, endTime));
    }
}
