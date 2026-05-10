package com.ruoyi.haikang.isup.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.haikang.isup.callBack.FRegisterCallBack;
import com.ruoyi.haikang.isup.service.haikang.IHaiKangIsupService;

import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 海康isup Controller
 *
 * @FileName HaiKangIsupController
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@Validated
@RestController
@RequestMapping("device")
public class HaiKangIsupController extends BaseController {

    @Autowired
    private IHaiKangIsupService haiKangIsupService;

    /**
     * 获取设备列表
     */
    @GetMapping("/list")
    public AjaxResult deviceList() {
        return success(FRegisterCallBack.deviceList);
    }

    /**
     * 海康设备查询录像
     *
     * @param deviceId  设备id
     * @param channelId 通道id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @GetMapping("/getRecMonth/{deviceId}/{channelId}")
    public R<ArrayList<HashMap<String, Object>>> getRecMonth(@PathVariable("deviceId") Long deviceId,
                                                              @PathVariable("channelId") Integer channelId,
                                                              @NotBlank(message = "开始时间不能为空") String startTime,
                                                              @NotBlank(message = "结束时间不能为空") String endTime) {
        return R.ok(haiKangIsupService.queryRecord(deviceId, channelId, startTime, endTime));
    }
}
