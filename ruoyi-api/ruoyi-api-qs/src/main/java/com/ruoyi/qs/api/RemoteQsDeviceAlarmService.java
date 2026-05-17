package com.ruoyi.qs.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.qs.api.domain.QsDeviceAlarm;
import com.ruoyi.qs.api.factory.RemoteQsDeviceAlarmFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备告警 服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteQsDeviceAlarmService", value = ServiceNameConstants.QS_SERVICE, fallbackFactory = RemoteQsDeviceAlarmFallbackFactory.class)
public interface RemoteQsDeviceAlarmService {

    /**
     * 新增设备告警
     *
     * @param qsDeviceAlarm 设备告警
     * @param inner         请求来源
     * @return 告警记录ID
     */
    @PostMapping("/api/alarm/add")
    R<Long> add(@RequestBody QsDeviceAlarm qsDeviceAlarm, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 查询设备告警列表
     *
     * @param qsDeviceAlarm 设备告警
     * @param inner         请求来源
     * @return 告警列表
     */
    @PostMapping("/api/alarm/list")
    R<List<QsDeviceAlarm>> list(@RequestBody QsDeviceAlarm qsDeviceAlarm, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 获取设备告警详细信息
     *
     * @param id    告警ID
     * @param inner 请求来源
     * @return 告警详细信息
     */
    @GetMapping("/api/alarm/getInfo/{id}")
    R<QsDeviceAlarm> getInfo(@PathVariable("id") Long id, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 修改设备告警
     *
     * @param qsDeviceAlarm 设备告警
     * @param inner         请求来源
     * @return 结果
     */
    @PutMapping("/api/alarm/edit")
    R<Boolean> edit(@RequestBody QsDeviceAlarm qsDeviceAlarm, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);
}
