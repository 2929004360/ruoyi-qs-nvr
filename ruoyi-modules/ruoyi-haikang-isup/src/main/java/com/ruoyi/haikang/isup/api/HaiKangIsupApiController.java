package com.ruoyi.haikang.isup.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.haikang.isup.api.domain.HaiKangIsupDeviceInfo;
import com.ruoyi.haikang.isup.callBack.FRegisterCallBack;
import com.ruoyi.haikang.isup.service.haikang.IHaiKangIsupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        if (lUserID == null) {
            return R.fail("设备未登录");
        }
        return R.ok(haiKangIsupService.getDevInfo(lUserID));
    }

    /**
     * 开始播放
     *
     * @param rtpServerParam
     * @return
     */
    @InnerAuth
    @PostMapping("/startPlay")
    public R<Void> startPlay(@RequestBody RtpServerParam rtpServerParam) {
        haiKangIsupService.startPlay(rtpServerParam);
        return R.ok();
    }

    /**
     * 停止播放
     *
     * @param id 设备id
     */
    @InnerAuth
    @GetMapping("/stopPlay/{id}")
    public R<Void> stopPlay(@PathVariable Long id) {
        haiKangIsupService.stopPlay(id);
        return R.ok();
    }

    /**
     * 开始云台控制
     */
    @InnerAuth
    @GetMapping("/startPtz/{deviceId}/{channelId}")
    public R<Void> startPtz(@PathVariable("deviceId") Long deviceId,
                            @PathVariable("channelId") Integer channelId,
                            int PTZCmd,
                            int speed
    ) {
        haiKangIsupService.startPtz(deviceId, channelId, PTZCmd, speed);
        return R.ok();
    }

    /**
     * 结束云台控制
     *
     * @return
     */
    @InnerAuth
    @GetMapping("/endPtz/{deviceId}/{channelId}")
    public R<Void> endPtz(@PathVariable("deviceId") Long deviceId,
                          @PathVariable("channelId") Integer channelId,
                          int PTZCmd,
                          int speed) {
        haiKangIsupService.endPtz(deviceId, channelId, PTZCmd, speed);
        return R.ok();
    }

    /**
     * 设置预置点
     *
     * @param deviceId
     * @param channelId
     * @param presetIndex
     * @return
     */
    @InnerAuth
    @GetMapping("/setPreset/{deviceId}/{channelId}")
    public R<Void> setPreset(@PathVariable("deviceId") Long deviceId,
                             @PathVariable("channelId") Integer channelId,
                             int presetIndex) {
        haiKangIsupService.setPreset(deviceId, channelId, presetIndex);
        return R.ok();
    }

    /**
     * 清除预置点
     *
     * @param deviceId
     * @param channelId
     * @param presetIndex
     * @return
     */
    @InnerAuth
    @GetMapping("/clearPreset/{deviceId}/{channelId}")
    public R<Void> clearPreset(@PathVariable("deviceId") Long deviceId,
                               @PathVariable("channelId") Integer channelId,
                               int presetIndex) {
        haiKangIsupService.clearPreset(deviceId, channelId, presetIndex);
        return R.ok();
    }

    /**
     * 调用预置点
     *
     * @param deviceId
     * @param channelId
     * @param presetIndex
     * @return
     */
    @InnerAuth
    @GetMapping("/gotoPreset/{deviceId}/{channelId}")
    public R<Void> gotoPreset(@PathVariable("deviceId") Long deviceId,
                              @PathVariable("channelId") Integer channelId,
                              int presetIndex) {
        haiKangIsupService.gotoPreset(deviceId, channelId, presetIndex);
        return R.ok();
    }

    /**
     * 辅助设备控制（灯光、雨刮、风扇等）
     *
     * @param deviceId
     * @param channelId
     * @param operation
     * @param isStart
     * @return
     */
    @InnerAuth
    @GetMapping("/cameraAuxControl/{deviceId}/{channelId}")
    public R<Void> cameraAuxControl(@PathVariable("deviceId") Long deviceId,
                                    @PathVariable("channelId") Integer channelId,
                                    String operation,
                                    boolean isStart) {
        haiKangIsupService.cameraAuxControl(deviceId, channelId, operation, isStart);
        return R.ok();
    }

    /**
     * 巡航控制
     *
     * @param deviceId
     * @param channelId
     * @param operation
     * @param param
     * @return
     */
    @InnerAuth
    @GetMapping("/cruiseControl/{deviceId}/{channelId}")
    public R<Void> cruiseControl(@PathVariable("deviceId") Long deviceId,
                                 @PathVariable("channelId") Integer channelId,
                                 String operation,
                                 Integer param) {
        haiKangIsupService.cruiseControl(deviceId, channelId, operation, param);
        return R.ok();
    }
}
