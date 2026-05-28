package com.ruoyi.gb28181.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.gb28181.api.bean.RecordInfo;
import com.ruoyi.gb28181.api.bean.ErrorCallback;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceChannel;
import com.ruoyi.gb28181.api.domain.DeviceConfig;
import com.alibaba.fastjson2.JSONObject;
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
     * 查询设备配置
     *
     * @param deviceId   设备国标编号
     * @param configType 配置类型，多个用/分隔
     * @return 操作结果
     */
    @GetMapping("/config/{deviceId}")
    public DeferredResult<AjaxResult> queryDeviceConfig(@PathVariable String deviceId, @RequestParam String configType) throws InvalidArgumentException, ParseException, SipException {
        log.info("[设备配置查询API] 开始查询, 设备 ID: {}, 配置类型: {}", deviceId, configType);
        Device device = deviceService.getDeviceByDeviceId(deviceId);
        if (device == null) {
            DeferredResult<AjaxResult> errorResult = new DeferredResult<>();
            errorResult.setResult(AjaxResult.error("设备不存在"));
            return errorResult;
        }
        DeferredResult<AjaxResult> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[设备配置查询] 开始调用 SIP 命令, 设备 ID: {}, 配置类型: {}", device.getDeviceId(), configType);
        sipCommander.deviceConfigQuery(device, null, configType, (code, msg, data) -> {
            log.info("[设备配置查询] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
            if (code == ErrorCode.SUCCESS.getCode()) {
                deferredResult.setResult(AjaxResult.success(data));
            } else {
                deferredResult.setResult(AjaxResult.error(msg));
            }
        });

        deferredResult.onTimeout(() -> {
            log.warn("[设备配置查询] 超时, 设备 ID: {}, 配置类型: {}", deviceId, configType);
            deferredResult.setResult(AjaxResult.error("获取设备配置超时"));
        });
        return deferredResult;
    }

    /**
     * 修改设备配置
     *
     * @param deviceId 设备国标编号
     * @param deviceConfig 设备配置
     * @return 操作结果
     */
    @PostMapping("/config/{deviceId}")
    public DeferredResult<AjaxResult> updateDeviceConfig(@PathVariable String deviceId, @RequestBody DeviceConfig deviceConfig) throws InvalidArgumentException, ParseException, SipException {
        log.info("[设备配置修改API] 开始修改, 设备 ID: {}", deviceId);
        Device device = deviceService.getDeviceByDeviceId(deviceId);
        if (device == null) {
            DeferredResult<AjaxResult> errorResult = new DeferredResult<>();
            errorResult.setResult(AjaxResult.error("设备不存在"));
            return errorResult;
        }
        DeferredResult<AjaxResult> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[设备配置修改] 开始调用 SIP 命令, 设备 ID: {}", device.getDeviceId());
        sipCommander.deviceConfigCmd(device, null, deviceConfig, (code, msg, data) -> {
            log.info("[设备配置修改] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
            if (code == ErrorCode.SUCCESS.getCode()) {
                deferredResult.setResult(AjaxResult.success(data));
            } else {
                deferredResult.setResult(AjaxResult.error(msg));
            }
        });

        deferredResult.onTimeout(() -> {
            log.warn("[设备配置修改] 超时, 设备 ID: {}", deviceId);
            deferredResult.setResult(AjaxResult.error("修改设备配置超时"));
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

    /**
     * 查询看守位
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @return 看守位信息
     */
    @GetMapping("/homePosition/{gbDeviceId}")
    public DeferredResult<AjaxResult> queryHomePosition(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId) {
        log.info("[看守位查询API] 开始查询, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<AjaxResult> errorResult = new DeferredResult<>();
            errorResult.setResult(AjaxResult.error("设备不存在"));
            return errorResult;
        }
        DeferredResult<AjaxResult> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[看守位查询] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}", device.getDeviceId(), channelId);
        try {
            sipCommander.homePositionQuery(device, channelId, (code, msg, data) -> {
                log.info("[看守位查询] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(AjaxResult.success(data));
                } else {
                    deferredResult.setResult(AjaxResult.error(msg));
                }
            });
        } catch (Exception e) {
            log.error("[看守位查询] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(AjaxResult.error("看守位查询失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[看守位查询] 超时, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
            deferredResult.setResult(AjaxResult.error("获取看守位超时"));
        });
        return deferredResult;
    }

    /**
     * 设置看守位
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param deviceConfig 看守位配置
     * @return 操作结果
     */
    @PostMapping("/homePosition/{gbDeviceId}")
    public DeferredResult<AjaxResult> updateHomePosition(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestBody DeviceConfig deviceConfig) {
        log.info("[看守位设置API] 开始设置, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<AjaxResult> errorResult = new DeferredResult<>();
            errorResult.setResult(AjaxResult.error("设备不存在"));
            return errorResult;
        }
        DeferredResult<AjaxResult> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[看守位设置] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}", device.getDeviceId(), channelId);
        try {
            sipCommander.homePositionCmd(device, channelId, deviceConfig, (code, msg, data) -> {
                log.info("[看守位设置] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(AjaxResult.success(data));
                } else {
                    deferredResult.setResult(AjaxResult.error(msg));
                }
            });
        } catch (Exception e) {
            log.error("[看守位设置] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(AjaxResult.error("看守位设置失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[看守位设置] 超时, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
            deferredResult.setResult(AjaxResult.error("设置看守位超时"));
        });
        return deferredResult;
    }

    /**
     * 查询巡航轨迹列表
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @return 巡航轨迹列表
     */
    @GetMapping("/cruiseTrackList/{gbDeviceId}")
    public DeferredResult<AjaxResult> queryCruiseTrackList(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId) {
        log.info("[巡航轨迹列表查询API] 开始查询, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<AjaxResult> errorResult = new DeferredResult<>();
            errorResult.setResult(AjaxResult.error("设备不存在"));
            return errorResult;
        }
        DeferredResult<AjaxResult> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[巡航轨迹列表查询] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}", device.getDeviceId(), channelId);
        try {
            sipCommander.cruiseTrackListQuery(device, channelId, (code, msg, data) -> {
                log.info("[巡航轨迹列表查询] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(AjaxResult.success(data));
                } else {
                    deferredResult.setResult(AjaxResult.error(msg));
                }
            });
        } catch (Exception e) {
            log.error("[巡航轨迹列表查询] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(AjaxResult.error("巡航轨迹列表查询失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[巡航轨迹列表查询] 超时, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
            deferredResult.setResult(AjaxResult.error("查询巡航轨迹列表超时"));
        });
        return deferredResult;
    }

    /**
     * 查询巡航轨迹
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param number 轨迹编号：0-第一条轨迹，1-第二条轨迹
     * @return 巡航轨迹信息
     */
    @GetMapping("/cruiseTrack/{gbDeviceId}")
    public DeferredResult<AjaxResult> queryCruiseTrack(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestParam Integer number) {
        log.info("[巡航轨迹查询API] 开始查询, 设备 ID: {}, 通道 ID: {}, 轨迹编号: {}", gbDeviceId, channelId, number);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<AjaxResult> errorResult = new DeferredResult<>();
            errorResult.setResult(AjaxResult.error("设备不存在"));
            return errorResult;
        }
        DeferredResult<AjaxResult> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[巡航轨迹查询] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}, 轨迹编号: {}", device.getDeviceId(), channelId, number);
        try {
            sipCommander.cruiseTrackQuery(device, channelId, number, (code, msg, data) -> {
                log.info("[巡航轨迹查询] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(AjaxResult.success(data));
                } else {
                    deferredResult.setResult(AjaxResult.error(msg));
                }
            });
        } catch (Exception e) {
            log.error("[巡航轨迹查询] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(AjaxResult.error("巡航轨迹查询失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[巡航轨迹查询] 超时, 设备 ID: {}, 通道 ID: {}, 轨迹编号: {}", gbDeviceId, channelId, number);
            deferredResult.setResult(AjaxResult.error("查询巡航轨迹超时"));
        });
        return deferredResult;
    }

    /**
     * 开始巡航
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId  通道国标编号（可选）
     * @param cruiseId   巡航组号(0-255)
     * @return 操作结果
     */
    @GetMapping("/cruise/start/{gbDeviceId}")
    public AjaxResult startCruise(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestParam Integer cruiseId) {
        log.info("[开始巡航API] 开始, 设备 ID: {}, 通道 ID: {}, 巡航组号: {}", gbDeviceId, channelId, cruiseId);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            return AjaxResult.error("设备不存在");
        }
        if (cruiseId == null || cruiseId < 0 || cruiseId > 255) {
            return AjaxResult.error("巡航组号必须为0-255之间的数字");
        }
        try {
            sipCommander.frontEndCmd(device, channelId, 0x88, cruiseId, 0, 0);
            log.info("[开始巡航] 成功, 设备 ID: {}, 通道 ID: {}, 巡航组号: {}", gbDeviceId, channelId, cruiseId);
            return AjaxResult.success("开始巡航成功");
        } catch (Exception e) {
            log.error("[开始巡航] 失败: {}", e.getMessage(), e);
            return AjaxResult.error("开始巡航失败: " + e.getMessage());
        }
    }

    /**
     * 停止巡航
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId  通道国标编号（可选）
     * @param cruiseId   巡航组号(0-255)
     * @return 操作结果
     */
    @GetMapping("/cruise/stop/{gbDeviceId}")
    public AjaxResult stopCruise(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestParam Integer cruiseId) {
        log.info("[停止巡航API] 开始, 设备 ID: {}, 通道 ID: {}, 巡航组号: {}", gbDeviceId, channelId, cruiseId);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            return AjaxResult.error("设备不存在");
        }
        if (cruiseId == null || cruiseId < 0 || cruiseId > 255) {
            return AjaxResult.error("巡航组号必须为0-255之间的数字");
        }
        try {
            sipCommander.frontEndCmd(device, channelId, 0, 0, 0, 0);
            log.info("[停止巡航] 成功, 设备 ID: {}, 通道 ID: {}, 巡航组号: {}", gbDeviceId, channelId, cruiseId);
            return AjaxResult.success("停止巡航成功");
        } catch (Exception e) {
            log.error("[停止巡航] 失败: {}", e.getMessage(), e);
            return AjaxResult.error("停止巡航失败: " + e.getMessage());
        }
    }

    /**
     * PTZ精准状态查询
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @return PTZ精准状态信息
     */
    @GetMapping("/ptzPosition/{gbDeviceId}")
    public DeferredResult<AjaxResult> queryPTZPosition(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId) {
        log.info("[PTZ精准状态查询API] 开始查询, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<AjaxResult> errorResult = new DeferredResult<>();
            errorResult.setResult(AjaxResult.error("设备不存在"));
            return errorResult;
        }
        DeferredResult<AjaxResult> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[PTZ精准状态查询] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}", device.getDeviceId(), channelId);
        try {
            sipCommander.ptzPositionQuery(device, channelId, (code, msg, data) -> {
                log.info("[PTZ精准状态查询] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(AjaxResult.success(data));
                } else {
                    deferredResult.setResult(AjaxResult.error(msg));
                }
            });
        } catch (Exception e) {
            log.error("[PTZ精准状态查询] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(AjaxResult.error("PTZ精准状态查询失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[PTZ精准状态查询] 超时, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
            deferredResult.setResult(AjaxResult.error("查询PTZ精准状态超时"));
        });
        return deferredResult;
    }

    /**
     * 存储卡状态查询
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @return 存储卡状态信息
     */
    @GetMapping("/sdCardStatus/{gbDeviceId}")
    public DeferredResult<AjaxResult> querySDCardStatus(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId) {
        log.info("[存储卡状态查询API] 开始查询, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<AjaxResult> errorResult = new DeferredResult<>();
            errorResult.setResult(AjaxResult.error("设备不存在"));
            return errorResult;
        }
        DeferredResult<AjaxResult> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[存储卡状态查询] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}", device.getDeviceId(), channelId);
        try {
            sipCommander.sdCardStatusQuery(device, channelId, (code, msg, data) -> {
                log.info("[存储卡状态查询] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(AjaxResult.success(data));
                } else {
                    deferredResult.setResult(AjaxResult.error(msg));
                }
            });
        } catch (Exception e) {
            log.error("[存储卡状态查询] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(AjaxResult.error("存储卡状态查询失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[存储卡状态查询] 超时, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
            deferredResult.setResult(AjaxResult.error("查询存储卡状态超时"));
        });
        return deferredResult;
    }

    /**
     * 报警复位控制
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param alarmMethod 报警方式（可选），0-全部，1-电话报警，2-设备报警，3-短信报警，4-GPS报警，5-视频报警，6-设备故障报警，7-其他报警
     * @param alarmType 报警类型（可选）
     * @return 报警复位控制结果
     */
    @PostMapping("/alarmReset/{gbDeviceId}")
    public DeferredResult<AjaxResult> alarmResetControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestParam(required = false) String alarmMethod, @RequestParam(required = false) String alarmType) {
        log.info("[报警复位控制API] 开始控制, 设备 ID: {}, 通道 ID: {}, 报警方式: {}, 报警类型: {}", gbDeviceId, channelId, alarmMethod, alarmType);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<AjaxResult> errorResult = new DeferredResult<>();
            errorResult.setResult(AjaxResult.error("设备不存在"));
            return errorResult;
        }
        DeferredResult<AjaxResult> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[报警复位控制] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}, 报警方式: {}, 报警类型: {}", device.getDeviceId(), channelId, alarmMethod, alarmType);
        try {
            sipCommander.alarmResetControl(device, channelId, alarmMethod, alarmType, (code, msg, data) -> {
                log.info("[报警复位控制] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(AjaxResult.success(data));
                } else {
                    deferredResult.setResult(AjaxResult.error(msg));
                }
            });
        } catch (Exception e) {
            log.error("[报警复位控制] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(AjaxResult.error("报警复位控制失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[报警复位控制] 超时, 设备 ID: {}, 通道 ID: {}, 报警方式: {}, 报警类型: {}", gbDeviceId, channelId, alarmMethod, alarmType);
            deferredResult.setResult(AjaxResult.error("报警复位控制超时"));
        });
        return deferredResult;
    }

    /**
     * 强制关键帧控制
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @return 强制关键帧控制结果
     */
    @PostMapping("/iFrame/{gbDeviceId}")
    public DeferredResult<AjaxResult> iFrameControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId) {
        log.info("[强制关键帧控制API] 开始控制, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<AjaxResult> errorResult = new DeferredResult<>();
            errorResult.setResult(AjaxResult.error("设备不存在"));
            return errorResult;
        }
        DeferredResult<AjaxResult> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[强制关键帧控制] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}", device.getDeviceId(), channelId);
        try {
            sipCommander.iFrameControl(device, channelId, (code, msg, data) -> {
                log.info("[强制关键帧控制] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(AjaxResult.success(data));
                } else {
                    deferredResult.setResult(AjaxResult.error(msg));
                }
            });
        } catch (Exception e) {
            log.error("[强制关键帧控制] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(AjaxResult.error("强制关键帧控制失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[强制关键帧控制] 超时, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
            deferredResult.setResult(AjaxResult.error("强制关键帧控制超时"));
        });
        return deferredResult;
    }

    /**
     * 看守位控制
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param deviceConfig 设备配置，包含看守位配置
     * @return 看守位控制结果
     */
    @PostMapping("/homePosition/control/{gbDeviceId}")
    public DeferredResult<AjaxResult> homePositionControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestBody DeviceConfig deviceConfig) {
        log.info("[看守位控制API] 开始控制, 设备 ID: {}, 通道 ID: {}, 配置: {}", gbDeviceId, channelId, deviceConfig != null ? deviceConfig.getHomePosition() : null);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<AjaxResult> errorResult = new DeferredResult<>();
            errorResult.setResult(AjaxResult.error("设备不存在"));
            return errorResult;
        }
        DeferredResult<AjaxResult> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[看守位控制] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}, 配置: {}", device.getDeviceId(), channelId, deviceConfig != null ? deviceConfig.getHomePosition() : null);
        try {
            sipCommander.homePositionControl(device, channelId, deviceConfig, (code, msg, data) -> {
                log.info("[看守位控制] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(AjaxResult.success(data));
                } else {
                    deferredResult.setResult(AjaxResult.error(msg));
                }
            });
        } catch (Exception e) {
            log.error("[看守位控制] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(AjaxResult.error("看守位控制失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[看守位控制] 超时, 设备 ID: {}, 通道 ID: {}, 配置: {}", gbDeviceId, channelId, deviceConfig != null ? deviceConfig.getHomePosition() : null);
            deferredResult.setResult(AjaxResult.error("看守位控制超时"));
        });
        return deferredResult;
    }

    /**
     * PTZ精准控制
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param ptzPreciseCtrl PTZ精准控制参数
     * @return PTZ精准控制结果
     */
    @PostMapping("/ptzPrecise/{gbDeviceId}")
    public DeferredResult<AjaxResult> ptzPreciseControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestBody JSONObject ptzPreciseCtrl) {
        log.info("[PTZ精准控制API] 开始控制, 设备 ID: {}, 通道 ID: {}, 参数: {}", gbDeviceId, channelId, ptzPreciseCtrl);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<AjaxResult> errorResult = new DeferredResult<>();
            errorResult.setResult(AjaxResult.error("设备不存在"));
            return errorResult;
        }
        DeferredResult<AjaxResult> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[PTZ精准控制] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}, 参数: {}", device.getDeviceId(), channelId, ptzPreciseCtrl);
        try {
            sipCommander.ptzPreciseControl(device, channelId, ptzPreciseCtrl, (code, msg, data) -> {
                log.info("[PTZ精准控制] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(AjaxResult.success(data));
                } else {
                    deferredResult.setResult(AjaxResult.error(msg));
                }
            });
        } catch (Exception e) {
            log.error("[PTZ精准控制] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(AjaxResult.error("PTZ精准控制失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[PTZ精准控制] 超时, 设备 ID: {}, 通道 ID: {}, 参数: {}", gbDeviceId, channelId, ptzPreciseCtrl);
            deferredResult.setResult(AjaxResult.error("PTZ精准控制超时"));
        });
        return deferredResult;
    }

    /**
     * 设备软件升级控制
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param firmware 设备固件版本
     * @param fileURL 升级文件的完整路径
     * @param manufacturer 设备厂商
     * @param sessionID 会话ID
     * @return 设备软件升级控制结果
     */
    @PostMapping("/deviceUpgrade/{gbDeviceId}")
    public DeferredResult<AjaxResult> deviceUpgradeControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId,
        @RequestParam String firmware, @RequestParam String fileURL, @RequestParam String manufacturer,
        @RequestParam String sessionID) {
        log.info("[设备软件升级控制API] 开始控制, 设备 ID: {}, 通道 ID: {}, 固件版本: {}, 文件URL: {}, 厂商: {}, 会话ID: {}", 
            gbDeviceId, channelId, firmware, fileURL, manufacturer, sessionID);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<AjaxResult> errorResult = new DeferredResult<>();
            errorResult.setResult(AjaxResult.error("设备不存在"));
            return errorResult;
        }
        DeferredResult<AjaxResult> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[设备软件升级控制] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}, 固件版本: {}, 文件URL: {}, 厂商: {}, 会话ID: {}", 
            device.getDeviceId(), channelId, firmware, fileURL, manufacturer, sessionID);
        try {
            sipCommander.deviceUpgradeControl(device, channelId, firmware, fileURL, manufacturer, sessionID, (code, msg, data) -> {
                log.info("[设备软件升级控制] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(AjaxResult.success(data));
                } else {
                    deferredResult.setResult(AjaxResult.error(msg));
                }
            });
        } catch (Exception e) {
            log.error("[设备软件升级控制] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(AjaxResult.error("设备软件升级控制失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[设备软件升级控制] 超时, 设备 ID: {}, 通道 ID: {}, 固件版本: {}, 文件URL: {}, 厂商: {}, 会话ID: {}", 
                gbDeviceId, channelId, firmware, fileURL, manufacturer, sessionID);
            deferredResult.setResult(AjaxResult.error("设备软件升级控制超时"));
        });
        return deferredResult;
    }

    /**
     * 存储卡格式化控制
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param sdCardId SD卡编号（0表示所有存储卡）
     * @return 存储卡格式化控制结果
     */
    @PostMapping("/formatSDCard/{gbDeviceId}")
    public DeferredResult<AjaxResult> formatSDCardControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId,
        @RequestParam Integer sdCardId) {
        log.info("[存储卡格式化控制API] 开始控制, 设备 ID: {}, 通道 ID: {}, SD卡编号: {}", 
            gbDeviceId, channelId, sdCardId);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<AjaxResult> errorResult = new DeferredResult<>();
            errorResult.setResult(AjaxResult.error("设备不存在"));
            return errorResult;
        }
        DeferredResult<AjaxResult> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[存储卡格式化控制] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}, SD卡编号: {}", 
            device.getDeviceId(), channelId, sdCardId);
        try {
            sipCommander.formatSDCardControl(device, channelId, sdCardId, (code, msg, data) -> {
                log.info("[存储卡格式化控制] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(AjaxResult.success(data));
                } else {
                    deferredResult.setResult(AjaxResult.error(msg));
                }
            });
        } catch (Exception e) {
            log.error("[存储卡格式化控制] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(AjaxResult.error("存储卡格式化控制失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[存储卡格式化控制] 超时, 设备 ID: {}, 通道 ID: {}, SD卡编号: {}", 
                gbDeviceId, channelId, sdCardId);
            deferredResult.setResult(AjaxResult.error("存储卡格式化控制超时"));
        });
        return deferredResult;
    }

    /**
     * 目标跟踪控制
     *
     * @param gbDeviceId 设备国标编号（球机通道）
     * @param channelId 通道国标编号（可选，指球机通道）
     * @param targetTrack 跟踪类型：Auto/Manual/Stop
     * @param deviceId2 目标设备编码（可选，指全景相机中的全景通道ID）
     * @param targetArea 目标区域（可选，手动跟踪时需要）
     * @return 目标跟踪控制结果
     */
    @PostMapping("/targetTrack/{gbDeviceId}")
    public DeferredResult<AjaxResult> targetTrackControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId,
        @RequestParam String targetTrack, @RequestParam(required = false) String deviceId2,
        @RequestBody(required = false) JSONObject targetArea) {
        log.info("[目标跟踪控制API] 开始控制, 设备 ID: {}, 通道 ID: {}, 跟踪类型: {}, 全景通道 ID: {}, 目标区域: {}", 
            gbDeviceId, channelId, targetTrack, deviceId2, targetArea);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<AjaxResult> errorResult = new DeferredResult<>();
            errorResult.setResult(AjaxResult.error("设备不存在"));
            return errorResult;
        }
        DeferredResult<AjaxResult> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[目标跟踪控制] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}, 跟踪类型: {}, 全景通道 ID: {}, 目标区域: {}", 
            device.getDeviceId(), channelId, targetTrack, deviceId2, targetArea);
        try {
            sipCommander.targetTrackControl(device, channelId, targetTrack, deviceId2, targetArea, (code, msg, data) -> {
                log.info("[目标跟踪控制] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(AjaxResult.success(data));
                } else {
                    deferredResult.setResult(AjaxResult.error(msg));
                }
            });
        } catch (Exception e) {
            log.error("[目标跟踪控制] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(AjaxResult.error("目标跟踪控制失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[目标跟踪控制] 超时, 设备 ID: {}, 通道 ID: {}, 跟踪类型: {}, 全景通道 ID: {}, 目标区域: {}", 
                gbDeviceId, channelId, targetTrack, deviceId2, targetArea);
            deferredResult.setResult(AjaxResult.error("目标跟踪控制超时"));
        });
        return deferredResult;
    }

}
