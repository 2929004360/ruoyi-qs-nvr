package com.ruoyi.zlm.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.service.IMediaServerService;
import com.ruoyi.zlm.session.SSRCFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * zlm接口
 *
 * @FileName ZlmApiController
 * @Description
 * @Author fengcheng
 * @date 2026-04-01
 **/
@Slf4j
@RestController
@RequestMapping("/api/zlm")
public class ZlmApiController {

    @Autowired
    private IMediaServerService mediaServerService;

    @Autowired
    private SSRCFactory ssrcFactory;

    @DeleteMapping("/sessionManagerPut/{id}/{ssrc}")
    R<Void> releaseSsrc(@PathVariable String mediaServerId, @PathVariable String ssrc) {
        ssrcFactory.releaseSsrc(mediaServerId, ssrc);
        return R.ok();
    }

    /**
     * 关闭rtp服务
     *
     * @param mediaServerId
     * @param rtpServer
     */
    @PostMapping("/closeRTPServer/{mediaServerId}")
    R<Void> closeRTPServer(@PathVariable String mediaServerId, @RequestBody RtpServerParam rtpServer) {
        ZlmMediaServer mediaServer = mediaServerService.getOneFromDatabase(mediaServerId);
        mediaServerService.closeRTPServer(mediaServer, rtpServer.getStream());
        return R.ok();
    }

    /**
     * 连接rtp服务
     *
     * @param mediaServerId
     * @param address
     * @param port
     * @param stream
     * @return
     */
    @PostMapping("/connectRtpServer/{mediaServerId}")
    R<Boolean> connectRtpServer(@PathVariable String mediaServerId, @RequestParam String address, @RequestParam int port, @RequestParam String stream) {
        ZlmMediaServer mediaServer = mediaServerService.getOneFromDatabase(mediaServerId);
        Boolean b = mediaServerService.connectRtpServer(mediaServer, address, port, stream);
        return R.ok(b);
    }
}
