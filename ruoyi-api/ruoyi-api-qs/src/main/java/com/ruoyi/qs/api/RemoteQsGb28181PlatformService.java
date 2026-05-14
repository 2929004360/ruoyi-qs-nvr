package com.ruoyi.qs.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.qs.api.domain.QsGb28181Platform;
import com.ruoyi.qs.api.domain.QsGb28181PlatformChannel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 国标平台 服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteQsGb28181PlatformService", value = ServiceNameConstants.QS_SERVICE)
public interface RemoteQsGb28181PlatformService {

    /**
     * 查询启用的平台列表
     *
     * @param platform 平台查询条件
     * @param source   请求来源
     * @return
     */
    @PostMapping("/api/gb28181/platform/list")
    R<List<QsGb28181Platform>> selectPlatformList(@RequestBody QsGb28181Platform platform, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 根据ID查询平台
     *
     * @param id     平台ID
     * @param source 请求来源
     * @return
     */
    @GetMapping("/api/gb28181/platform/{id}")
    R<QsGb28181Platform> selectPlatformById(@PathVariable Long id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 查询平台通道列表
     *
     * @param channel 通道查询条件
     * @param source  请求来源
     * @return
     */
    @PostMapping("/api/gb28181/platformChannel/list")
    R<List<QsGb28181PlatformChannel>> selectPlatformChannelList(@RequestBody QsGb28181PlatformChannel channel, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 更新平台
     *
     * @param platform 平台信息
     * @param source   请求来源
     * @return
     */
    @PutMapping("/api/gb28181/platform")
    R<Boolean> updatePlatform(@RequestBody QsGb28181Platform platform, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
