package com.ruoyi.zlm.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.zlm.api.domain.ZlmCloudRecord;
import com.ruoyi.zlm.api.factory.RemoteZlmCloudRecordFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * zlm接口云端接口 服务
 *
 * @FileName RemoteZlmCloudRecordService
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@FeignClient(contextId = "remoteZlmCloudRecordService", value = ServiceNameConstants.ZLM_SERVICE, fallbackFactory = RemoteZlmCloudRecordFallbackFactory.class)
public interface RemoteZlmCloudRecordService {

    /**
     * 定时查询待删除的录像文件
     *
     * @param inner 请求来源
     * @return
     */
    @GetMapping("/api/cloudRecord/task")
    R<Void> task(@RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 查询云端录像列表
     *
     * @param zlmCloudRecord 云端录像
     * @param inner 请求来源
     * @return
     */
    @PostMapping("/api/cloudRecord/list")
    R<List<ZlmCloudRecord>> selectZlmCloudRecordList(@RequestBody ZlmCloudRecord zlmCloudRecord, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);
}
