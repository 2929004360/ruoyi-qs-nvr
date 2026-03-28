package com.ruoyi.haikang.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.haikang.api.domain.LoginDevice;
import com.ruoyi.haikang.service.IHaiKangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 海康sdk Controller
 *
 * @FileName HaiKangController
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@RestController
@RequestMapping("/haikang")
public class HaiKangController extends BaseController {

    @Autowired
    private IHaiKangService haiKangService;


    /**
     * 登录设备，支持 V40 和 V30 版本，功能一致。
     *
     * @param loginDevice 海康设备登录信息
     * @return 登录成功返回用户ID，失败返回-1
     */
    @PostMapping("/loginDevice")
    public AjaxResult loginDevice(@RequestBody LoginDevice loginDevice) {
        return success(haiKangService.loginDevice(
                loginDevice.getIpAddress(),
                loginDevice.getPort(),
                loginDevice.getUserName(),
                loginDevice.getPassword()));
    }

    /**
     * 设备注销
     *
     * @param ip 设备ip
     */
    @PostMapping("/logoutDevice/{ip}")
    public AjaxResult logoutDevice(@PathVariable String ip) {
        haiKangService.logoutDevice(ip);
        return success();
    }

    /**
     * 获取设备登录的用户ID
     *
     * @param ip 设备ip
     * @return
     */
    @PostMapping("/getUserId/{ip}")
    public AjaxResult getUserId(@PathVariable String ip) {
        return success(haiKangService.getUserId(ip));
    }
}
