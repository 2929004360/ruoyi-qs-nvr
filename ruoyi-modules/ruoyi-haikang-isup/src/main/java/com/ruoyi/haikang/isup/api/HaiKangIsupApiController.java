package com.ruoyi.haikang.isup.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.haikang.isup.api.domain.HaiKangIsupDeviceInfo;
import com.ruoyi.haikang.isup.api.domain.HaiKangIsupPresetInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ruoyi.haikang.isup.callBack.FRegisterCallBack;
import com.ruoyi.haikang.isup.service.haikang.IHaiKangIsupService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 海康isup api Controller
 *
 * @FileName HaiKangIsupApiController
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 */
@Validated
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
     * 开始回放
     *
     * @param rtpServerParam 回放参数
     * @return
     */
    @InnerAuth
    @PostMapping("/startPlayback")
    public R<Void> startPlayback(@RequestBody RtpServerParam rtpServerParam) {
        haiKangIsupService.startPlayback(rtpServerParam);
        return R.ok();
    }

    /**
     * 停止回放
     *
     * @param id 设备id
     */
    @InnerAuth
    @GetMapping("/stopPlayback/{id}")
    public R<Void> stopPlayback(@PathVariable Long id) {
        haiKangIsupService.stopPlayback(id);
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

    /**
     * 获取预置点列表
     *
     * @param deviceId
     * @param channelId
     * @return
     */
    @InnerAuth
    @GetMapping("/getPresetList/{deviceId}/{channelId}")
    public R<List<HaiKangIsupPresetInfo>> getPresetList(@PathVariable("deviceId") Long deviceId,
                                                        @PathVariable("channelId") Integer channelId) {
        return R.ok(haiKangIsupService.getPresetList(deviceId, channelId));
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

    /**
     * 重启海康设备
     *
     * @param deviceId 设备id
     * @return
     */
    @InnerAuth
    @GetMapping("/restartDevice/{deviceId}")
    public R<Void> restartDevice(@PathVariable("deviceId") Long deviceId) {
        haiKangIsupService.restartDevice(deviceId);
        return R.ok();
    }

    /**
     * 获取海康设备时间
     *
     * @param deviceId 设备id
     * @return
     */
    @InnerAuth
    @GetMapping("/getDevTime/{deviceId}")
    public R<String> getDevTime(@PathVariable("deviceId") Long deviceId) {
        return R.ok(haiKangIsupService.getDevTime(deviceId));
    }

    /**
     * 设置海康设备时间
     *
     * @param deviceId 设备id
     * @param time     时间，格式：yyyy-MM-dd HH:mm:ss
     * @return
     */
    @InnerAuth
    @GetMapping("/setDevTime/{deviceId}")
    public R<Void> setDevTime(@PathVariable("deviceId") Long deviceId, String time) {
        haiKangIsupService.setDevTime(deviceId, time);
        return R.ok();
    }
}
