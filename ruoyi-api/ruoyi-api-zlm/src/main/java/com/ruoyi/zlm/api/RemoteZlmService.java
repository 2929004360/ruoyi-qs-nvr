package com.ruoyi.zlm.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.zlm.api.factory.RemoteZlmFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * zlm接口 服务
 *
 * @FileName RemoteZlmService
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@FeignClient(contextId = "remoteZlmService", value = ServiceNameConstants.ZLM_SERVICE, fallbackFactory = RemoteZlmFallbackFactory.class)
public interface RemoteZlmService {

}
