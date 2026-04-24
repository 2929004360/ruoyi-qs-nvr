package com.ruoyi.zlm.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.zlm.api.factory.RemoteZlmFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/api/zlm/sessionManagerPut/{mediaServerId}/{ssrc}")
    R<Void> releaseSsrc(@PathVariable String mediaServerId, @PathVariable String ssrc, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 关闭rtp服务
     *
     * @param mediaServerId
     * @param rtpServer
     * @param inner
     */
    @PostMapping("/api/zlm/closeRTPServer/{mediaServerId}")
    R<Void> closeRTPServer(@PathVariable String mediaServerId, @RequestBody RtpServerParam rtpServer, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 连接rtp服务
     *
     * @param mediaServerId
     * @param address
     * @param port
     * @param stream
     * @param inner
     * @return
     */
    @PostMapping("/api/zlm/connectRtpServer/{mediaServerId}")
    R<Boolean> connectRtpServer(@PathVariable String mediaServerId, @RequestParam String address, @RequestParam int port, @RequestParam String stream, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);
}
