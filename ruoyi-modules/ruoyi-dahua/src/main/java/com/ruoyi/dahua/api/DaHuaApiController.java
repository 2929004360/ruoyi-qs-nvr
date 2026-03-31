package com.ruoyi.dahua.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.dahua.api.domain.DahuaDevice;
import com.ruoyi.dahua.api.domain.LoginDevice;
import com.ruoyi.dahua.service.IDaHuaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 大华sdk Controller
 *
 * @FileName DaHuaController
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
@RestController
@RequestMapping("/api/dahua")
public class DaHuaApiController {

    @Autowired
    private IDaHuaService daHuaService;

    /**
     * 大华设备登录
     *
     * @param loginDevice
     */
    @InnerAuth
    @PostMapping("/loginDevice")
    public R<Void> loginDevice(@RequestBody LoginDevice loginDevice) {
        daHuaService.loginDevice(loginDevice.getIpAddress(),
                loginDevice.getPort(),
                loginDevice.getUserName(),
                loginDevice.getPassword(),
                loginDevice.getDeviceId(),
                loginDevice.getOnlineType()
        );
        return R.ok();
    }

    /**
     * 查询是否登录
     *
     * @param ip 设备ip
     */
    @InnerAuth
    @PostMapping("/isUserId/{ip}")
    public R<Boolean> isUserId(@PathVariable String ip) {
        return R.ok(daHuaService.isUserId(ip));
    }

    /**
     * 大华设备获取时间
     *
     * @param ip 设备ip
     */
    @InnerAuth
    @PostMapping("/getTime/{ip}")
    public R<String> getTime(@PathVariable String ip) {
        return R.ok(daHuaService.getTime(ip));
    }

    /**
     * 获取大华主动上线设备
     *
     * @param ip 设备ip
     */
    @InnerAuth
    @PostMapping("/getDahuaDevice/{ip}")
    public R<DahuaDevice> getDahuaDevice(@PathVariable String ip) {
        return R.ok(daHuaService.getDahuaDevice(ip));
    }

    /**
     * 获取大华主动上线设备
     *
     * @param ip 设备ip
     */
    @InnerAuth
    @PostMapping("/logoutDevice/{ip}")
    public R<Boolean> logoutDevice(@PathVariable String ip) {
        return R.ok(daHuaService.logoutDevice(ip));
    }
}
