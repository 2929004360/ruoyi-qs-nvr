package com.ruoyi.haikang.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.haikang.service.IHaiKangService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * 海康sdk Controller
 *
 * @FileName HaiKangController
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@Validated
@RestController
@RequestMapping("/device")
public class HaiKangController extends BaseController {

    @Autowired
    private IHaiKangService haiKangService;


    /**
     * 海康设备查询录像
     */
    @GetMapping("/getRecMonth/{deviceId}/{channelId}")
    public R<ArrayList<HashMap<String, Object>>> getRecMonth(@PathVariable("deviceId") Long deviceId, @PathVariable("channelId") int channelId, @NotBlank(message = "开始时间不能为空") String startTime, @NotBlank(message = "结束时间不能为空") String endTime) {
        return R.ok(haiKangService.queryRecord(deviceId, channelId, startTime, endTime));
    }
}
