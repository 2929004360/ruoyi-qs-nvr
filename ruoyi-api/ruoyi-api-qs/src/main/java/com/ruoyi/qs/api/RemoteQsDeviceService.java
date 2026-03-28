package com.ruoyi.qs.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.api.factory.RemoteQsDeviceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

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
     * @param source 请求来源
     * @return
     */
    @PostMapping("/api/device/allList")
    public R<List<QsDevice>> list(QsDevice qsDevice,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
