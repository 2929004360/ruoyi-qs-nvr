package com.ruoyi.gb28181.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.gb28181.api.bean.RecordInfo;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceChannel;
import com.ruoyi.gb28181.api.utils.DateUtil;
import com.ruoyi.gb28181.config.UserSetting;
import com.ruoyi.gb28181.service.IDeviceService;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/device")
public class Gb28181Controller {

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private UserSetting userSetting;

    /**
     * 获取所有国标设备
     *
     * @return 设备列表
     */
    @GetMapping("/getAllDevices")
    public AjaxResult getAllDevices() {
        return AjaxResult.success(deviceService.getAllDevices());
    }

    /**
     * 根据国标设备获取所有通道
     *
     * @param gbDeviceId 设备编号
     * @return 通道列表
     */
    @GetMapping("/getChannelsByDeviceId/{gbDeviceId}")
    public AjaxResult getChannelsByDeviceId(@PathVariable String gbDeviceId) {
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            return AjaxResult.error("gb28181 设备不存在 deviceId:" + gbDeviceId);
        }
        return AjaxResult.success(deviceService.getChannelsByDeviceId(gbDeviceId));
    }

    /**
     * 查询录像文件列表
     */
    @GetMapping("/queryRecord/{deviceId}/{channelId}")
    public DeferredResult<R<RecordInfo>> queryRecord(
            @PathVariable String deviceId,
            @PathVariable String channelId,
            @RequestParam @NotBlank(message = "开始时间不能为空") String startTime,
            @RequestParam @NotBlank(message = "结束时间不能为空") String endTime) {

        if (log.isDebugEnabled()) {
            log.debug(String.format("录像信息查询 API调用，deviceId：%s ，startTime：%s， endTime：%s", deviceId, startTime, endTime));
        }
        DeferredResult<R<RecordInfo>> result = new DeferredResult<>(Long.valueOf(userSetting.getRecordInfoTimeout()), TimeUnit.MILLISECONDS);
        if (!DateUtil.verification(startTime, DateUtil.formatter)) {
            throw new ServiceException("startTime格式为" + DateUtil.PATTERN);
        }
        if (!DateUtil.verification(endTime, DateUtil.formatter)) {
            throw new ServiceException("endTime格式为" + DateUtil.PATTERN);
        }

        Device device = deviceService.getDeviceByDeviceId(deviceId);
        if (device == null) {
            throw new ServiceException(deviceId + " 不存在");
        }

        DeviceChannel channel = deviceService.getDeviceChannelByChannelId(deviceId, channelId);
        if (channel == null) {
            throw new ServiceException(channelId + " 不存在");
        }

        deviceService.queryRecord(device, channel, startTime, endTime, (code, msg, data) -> {
            R<RecordInfo> wvpResult = R.ok();
            wvpResult.setMsg(msg);
            wvpResult.setData(data);
            result.setResult(wvpResult);
        });
        result.onTimeout(() -> {
            R<RecordInfo> wvpResult = R.fail();
            wvpResult.setMsg("timeout");
            result.setResult(wvpResult);
        });
        return result;
    }
}
