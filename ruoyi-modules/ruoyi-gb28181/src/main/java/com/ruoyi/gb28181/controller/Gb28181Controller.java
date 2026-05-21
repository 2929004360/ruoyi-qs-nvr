package com.ruoyi.gb28181.controller;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.gb28181.api.bean.RecordInfo;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceChannel;
import com.ruoyi.gb28181.api.domain.DeviceInfo;
import com.ruoyi.gb28181.api.domain.DeviceStatus;
import com.ruoyi.gb28181.api.utils.DateUtil;
import com.ruoyi.gb28181.common.ErrorCode;
import com.ruoyi.gb28181.config.UserSetting;
import com.ruoyi.gb28181.service.IDeviceService;
import com.ruoyi.gb28181.service.ISIPCommander;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/device")
public class Gb28181Controller {

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private UserSetting userSetting;

    @Autowired
    private ISIPCommander sipCommander;

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

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

    /**
     * 刷新设备状态和通道
     */
    @PostMapping("/refresh/{gbDeviceId}")
    public AjaxResult refreshDevice(@PathVariable String gbDeviceId) {
        log.info("刷新设备状态和通道：{}", gbDeviceId);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            return AjaxResult.error("设备不存在");
        }
        deviceService.refreshDevice(device);
        return AjaxResult.success("刷新成功");
    }

    /**
     * 远程重启设备
     */
    @PostMapping("/reboot/{gbDeviceId}")
    public AjaxResult rebootDevice(@PathVariable String gbDeviceId) {
        log.info("远程重启设备：{}", gbDeviceId);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            return AjaxResult.error("设备不存在");
        }
        try {
            sipCommander.rebootDevice(device);
            return AjaxResult.success("重启命令已发送");
        } catch (Exception e) {
            log.error("远程重启设备失败：{}", e.getMessage(), e);
            return AjaxResult.error("远程重启设备失败：" + e.getMessage());
        }
    }

    /**
     * 录像控制
     */
    @PostMapping("/record/cmd")
    public AjaxResult recordCmd(@RequestParam String gbDeviceId, @RequestParam String channelId, @RequestParam String recordCmd, @RequestParam(required = false) Integer streamNumber) {
        log.info("录像控制：设备编号={}, 通道编号={}, 录像命令={}, 码流类型={}", gbDeviceId, channelId, recordCmd, streamNumber);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            return AjaxResult.error("设备不存在");
        }
        try {
            sipCommander.recordCmd(device, channelId, recordCmd, streamNumber);
            return AjaxResult.success("录像控制命令已发送");
        } catch (Exception e) {
            log.error("录像控制失败：{}", e.getMessage(), e);
            return AjaxResult.error("录像控制失败：" + e.getMessage());
        }
    }

    /**
     * 查询设备状态
     */
    @GetMapping("/status/{deviceId}")
    public DeferredResult<AjaxResult> queryDeviceStatus(@PathVariable String deviceId) throws InvalidArgumentException, ParseException, SipException {
        if (log.isDebugEnabled()) {
            log.debug("设备状态查询API调用");
        }
        Device device = deviceService.getDeviceByDeviceId(deviceId);
        if (device == null) {
            DeferredResult<AjaxResult> errorResult = new DeferredResult<>();
            errorResult.setResult(AjaxResult.error("设备不存在"));
            return errorResult;
        }
        DeferredResult<AjaxResult> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[设备状态查询] 开始调用 SIP 命令, 设备 ID: {}", device.getDeviceId());
        sipCommander.deviceStatusQuery(device, (code, msg, data) -> {
            log.info("[设备状态查询] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
            if (code == ErrorCode.SUCCESS.getCode()) {
                deferredResult.setResult(AjaxResult.success(data));
            } else {
                deferredResult.setResult(AjaxResult.error(msg));
            }
        });

        deferredResult.onTimeout(() -> {
            log.warn("[获取设备状态] 超时, {}", device.getDeviceId());
            deferredResult.setResult(AjaxResult.error("获取设备状态超时"));
        });
        return deferredResult;
    }

    /**
     * 查询设备信息
     */
    @GetMapping("/info/{deviceId}")
    public DeferredResult<AjaxResult> queryDeviceInfo(@PathVariable String deviceId) throws InvalidArgumentException, ParseException, SipException {
        if (log.isDebugEnabled()) {
            log.debug("设备信息查询API调用");
        }
        Device device = deviceService.getDeviceByDeviceId(deviceId);
        if (device == null) {
            DeferredResult<AjaxResult> errorResult = new DeferredResult<>();
            errorResult.setResult(AjaxResult.error("设备不存在"));
            return errorResult;
        }
        DeferredResult<AjaxResult> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[设备信息查询] 开始调用 SIP 命令, 设备 ID: {}", device.getDeviceId());
        sipCommander.deviceInfoQuery(device, (code, msg, data) -> {
            log.info("[设备信息查询] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
            if (code == ErrorCode.SUCCESS.getCode()) {
                deferredResult.setResult(AjaxResult.success(data));
            } else {
                deferredResult.setResult(AjaxResult.error(msg));
            }
        });

        deferredResult.onTimeout(() -> {
            log.warn("[获取设备信息] 超时, {}", device.getDeviceId());
            deferredResult.setResult(AjaxResult.error("获取设备信息超时"));
        });
        return deferredResult;
    }

    /**
     * 查询目录
     */
    @GetMapping("/catalog/{deviceId}")
    public DeferredResult<AjaxResult> queryCatalog(
            @PathVariable String deviceId,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        if (log.isDebugEnabled()) {
            log.debug(String.format("目录查询 API调用，deviceId：%s，startTime：%s， endTime：%s", deviceId, startTime, endTime));
        }
        Device device = deviceService.getDeviceByDeviceId(deviceId);
        if (device == null) {
            DeferredResult<AjaxResult> errorResult = new DeferredResult<>();
            errorResult.setResult(AjaxResult.error("设备不存在"));
            return errorResult;
        }
        DeferredResult<AjaxResult> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[目录查询] 开始调用 SIP 命令, 设备 ID: {}, startTime: {}, endTime: {}", deviceId, startTime, endTime);
        deviceService.queryCatalog(device, startTime, endTime, (code, msg, data) -> {
            log.info("[目录查询] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
            if (code == ErrorCode.SUCCESS.getCode()) {
                deferredResult.setResult(AjaxResult.success(data));
            } else {
                deferredResult.setResult(AjaxResult.error(msg));
            }
        });

        deferredResult.onTimeout(() -> {
            log.warn("[目录查询] 超时, {}", deviceId);
            deferredResult.setResult(AjaxResult.error("目录查询超时"));
        });
        return deferredResult;
    }

    /**
     * 目录订阅
     *
     * @param qsDeviceId QsDevice主键ID
     * @return 操作结果
     */
    @GetMapping("/subscribe/catalog/{qsDeviceId}")
    public AjaxResult subscribeCatalog(@PathVariable Long qsDeviceId) {
        log.info("[目录订阅API] 开始订阅设备, QsDeviceId: {}", qsDeviceId);
        try {
            // 先通过 QsDeviceId 查询 QsDevice 获取 gbDeviceId
            com.ruoyi.qs.api.domain.QsDevice qsDevice = remoteQsDeviceService.getQsDeviceInfo(
                    qsDeviceId,
                    SecurityConstants.INNER
            ).getData();
            if (qsDevice == null || qsDevice.getGbDeviceId() == null) {
                return AjaxResult.error("设备不存在或未配置国标设备ID: " + qsDeviceId);
            }
            
            // 通过 gbDeviceId 获取 GB28181 设备
            Device device = deviceService.getDeviceByDeviceId(qsDevice.getGbDeviceId());
            if (device == null) {
                return AjaxResult.error("国标设备不存在: " + qsDevice.getGbDeviceId());
            }

            deviceService.subscribeCatalog(device, qsDeviceId);
            return AjaxResult.success("目录订阅请求已发送");
        } catch (Exception e) {
            log.error("[目录订阅API] 订阅失败: {}", qsDeviceId, e);
            return AjaxResult.error("目录订阅失败: " + e.getMessage());
        }
    }

    /**
     * 取消目录订阅
     *
     * @param qsDeviceId QsDevice主键ID
     * @return 操作结果
     */
    @GetMapping("/unsubscribe/catalog/{qsDeviceId}")
    public AjaxResult unsubscribeCatalog(@PathVariable Long qsDeviceId) {
        log.info("[目录订阅API] 取消订阅设备, QsDeviceId: {}", qsDeviceId);
        try {
            // 先通过 QsDeviceId 查询 QsDevice 获取 gbDeviceId
            com.ruoyi.qs.api.domain.QsDevice qsDevice = remoteQsDeviceService.getQsDeviceInfo(
                    qsDeviceId,
                    SecurityConstants.INNER
            ).getData();
            if (qsDevice == null || qsDevice.getGbDeviceId() == null) {
                return AjaxResult.error("设备不存在或未配置国标设备ID: " + qsDeviceId);
            }
            
            // 通过 gbDeviceId 获取 GB28181 设备
            Device device = deviceService.getDeviceByDeviceId(qsDevice.getGbDeviceId());
            if (device == null) {
                return AjaxResult.error("国标设备不存在: " + qsDevice.getGbDeviceId());
            }

            deviceService.unsubscribeCatalog(device, qsDeviceId);
            return AjaxResult.success("取消目录订阅请求已发送");
        } catch (Exception e) {
            log.error("[目录订阅API] 取消订阅失败: {}", qsDeviceId, e);
            return AjaxResult.error("取消目录订阅失败: " + e.getMessage());
        }
    }

}
