package com.ruoyi.haikang.isup.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.haikang.isup.api.domain.HaiKangIsupDeviceInfo;
import com.ruoyi.haikang.isup.callBack.FRegisterCallBack;
import com.ruoyi.haikang.isup.service.haikang.IHaiKangIsupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 海康isup api Controller
 *
 * @FileName HaiKangIsupApiController
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
@RestController
@RequestMapping("/api/haikang/isup")
public class HaiKangIsupApiController {

    @Autowired
    private IHaiKangIsupService haiKangIsupService;

    /**
     * 获取设备登录的用户ID
     *
     * @param ip 设备ip
     * @return
     */
    @InnerAuth
    @PostMapping("/getUserId/{ip}")
    public R<Integer> getUserId(@PathVariable String ip) {
        return R.ok(FRegisterCallBack.lUserIDMap.get(ip));
    }

    /**
     * 获取设备信息
     *
     * @param ip 设备ip
     * @return
     */
    @InnerAuth
    @PostMapping("/getDevInfo/{ip}")
    public R<HaiKangIsupDeviceInfo> getDevInfo(@PathVariable String ip) {
        Integer lUserID = FRegisterCallBack.lUserIDMap.get(ip);
        if(lUserID == null){
            return R.fail("设备未登录");
        }
        return R.ok(haiKangIsupService.getDevInfo(lUserID));
    }
}
