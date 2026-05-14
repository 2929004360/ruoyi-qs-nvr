package com.ruoyi.zlm.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.domain.Gb28181PlatformPlay;
import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.api.factory.RemoteZlmFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    /**
     * 开始发送RTP流到指定地址
     *
     * @param mediaServerId
     * @param param
     * @param inner
     * @return
     */
    @PostMapping("/api/zlm/startSendRtp/{mediaServerId}")
    R<?> startSendRtp(@PathVariable String mediaServerId, @RequestBody Map<String, Object> param, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 停止发送RTP流
     *
     * @param mediaServerId
     * @param param
     * @param inner
     * @return
     */
    @PostMapping("/api/zlm/stopSendRtp/{mediaServerId}")
    R<?> stopSendRtp(@PathVariable String mediaServerId, @RequestBody Map<String, Object> param, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 获取默认的媒体服务器
     *
     * @param inner
     * @return
     */
    @GetMapping("/api/zlm/getDefaultMediaServer")
    R<ZlmMediaServer> getDefaultMediaServer(@RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 从数据库中获取指定id的媒体服务器
     *
     * @param id
     * @param inner
     * @return
     */
    @GetMapping("/api/zlm/getOneFromDatabase/{id}")
    R<ZlmMediaServer> getOneFromDatabase(@PathVariable String id, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 处理上级平台点播
     *
     * @param platformPlay
     * @param inner
     * @return
     */
    @PostMapping("/api/zlm/gb28181PlatformPlay")
    R<Void> gb28181PlatformPlay(@RequestBody Gb28181PlatformPlay platformPlay, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);
}
