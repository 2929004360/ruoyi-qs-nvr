package com.ruoyi.qs.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.api.factory.RemoteQsDeviceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 视频监控设备 服务
 *
 * @FileName RemoteQsDeviceService
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@FeignClient(contextId = "remoteQsDeviceService", value = ServiceNameConstants.QS_SERVICE, fallbackFactory = RemoteQsDeviceFallbackFactory.class)
public interface RemoteQsDeviceService {

    /**
     * 查询视频监控设备
     *
     * @param qsDevice 视频监控设备
     * @param source   请求来源
     * @return
     */
    @PostMapping("/api/device/allList")
    public R<List<QsDevice>> list(QsDevice qsDevice, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 更新设备在线状态
     *
     * @param onlineDeviceSet 在线设备集合
     * @param deviceStatus    设备状态
     * @param inner           请求来源
     * @return
     */
    @PostMapping("/api/device/updateDeviceStatusList/{deviceStatus}")
    public R<Boolean> updateQsDeviceStatusList(@RequestBody Set<Long> onlineDeviceSet, @PathVariable String deviceStatus, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);


    /**
     * 修改视频监控设备
     *
     * @param qsDevice 视频监控设备
     * @param inner    请求来源
     * @return
     */
    @PutMapping("/api/device/updateQsDevice")
    public R<Boolean> updateQsDevice(@RequestBody QsDevice qsDevice, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 根据流id获取视频监控设备
     *
     * @param stream 流id
     * @param inner  请求来源
     * @return
     */
    @GetMapping("/api/device/getQsDeviceStream/{stream}")
    R<QsDevice> getQsDeviceStream(@PathVariable String stream, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 获取视频监控设备详细信息
     *
     * @param id    设备id
     * @param inner 请求来源
     * @return
     */
    @GetMapping("/api/device/getQsDeviceInfo/{id}")
    R<QsDevice> getQsDeviceInfo(@PathVariable Long id, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 设备状态任务
     *
     * @param inner 请求来源
     * @return
     */
    @GetMapping("/api/device/task")
    R<QsDevice> task(@RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 清理设备计划id
     *
     * @param planId 设备id
     * @param inner 请求来源
     */
    @GetMapping("/api/device/cleanRecordPlanId/{planId}")
    R<Void> cleanRecordPlanId(@PathVariable Long planId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 根据设备id集合查询设备信息
     *
     * @param startDeviceIdList 设备id集合
     * @param inner 请求来源
     * @return
     */
    @GetMapping("/api/device/queryByIds/{startDeviceIdList}")
    R<List<QsDevice>> queryByIds(@PathVariable List<Long> startDeviceIdList,@RequestHeader(SecurityConstants.FROM_SOURCE) String inner);
}
