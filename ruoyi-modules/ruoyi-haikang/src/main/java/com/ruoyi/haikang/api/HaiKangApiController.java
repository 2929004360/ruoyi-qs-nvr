package com.ruoyi.haikang.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.annotation.InnerAuth;
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
@RequestMapping("/api/haikang")
public class HaiKangApiController {

    @Autowired
    private IHaiKangService haiKangService;

    /**
     * 登录设备，支持 V40 和 V30 版本，功能一致。
     *
     * @param loginDevice 海康设备登录信息
     * @return 登录成功返回用户ID，失败返回-1
     */
    @InnerAuth
    @PostMapping("/loginDevice")
    public R<Integer> loginDevice(@RequestBody LoginDevice loginDevice) {
        return R.ok(haiKangService.loginDevice(
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
    @InnerAuth
    @PostMapping("/logoutDevice/{ip}")
    public R<Void> logoutDevice(@PathVariable String ip) {
        haiKangService.logoutDevice(ip);
        return R.ok();
    }

    /**
     * 获取设备登录的用户ID
     *
     * @param ip 设备ip
     * @return
     */
    @InnerAuth
    @PostMapping("/getUserId/{ip}")
    public R<Integer> getUserId(@PathVariable String ip) {
        return R.ok(haiKangService.getUserId(ip));
    }
}
