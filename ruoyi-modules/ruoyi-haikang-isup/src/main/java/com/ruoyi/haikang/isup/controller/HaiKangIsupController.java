package com.ruoyi.haikang.isup.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.haikang.isup.callBack.FRegisterCallBack;
import org.springframework.web.bind.annotation.GetMapping;
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
@RestController
@RequestMapping("device")
public class HaiKangIsupController extends BaseController {

    /**
     * 获取设备列表
     */
    @GetMapping("/list")
    public AjaxResult deviceList() {
        return success(FRegisterCallBack.deviceList);
    }
}
