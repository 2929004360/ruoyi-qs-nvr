package com.ruoyi.zlm.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.domain.Gb28181PlatformPlay;
import com.ruoyi.zlm.api.domain.StreamInfo;
import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.service.ErrorCallback;
import com.ruoyi.zlm.service.IDevicePlayService;
import com.ruoyi.zlm.service.IMediaServerService;
import com.ruoyi.zlm.session.SSRCFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    private IDevicePlayService devicePlayService;

    @Autowired
    private SSRCFactory ssrcFactory;

    @DeleteMapping("/sessionManagerPut/{mediaServerId}/{ssrc}")
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

    /**
     * 开始发送RTP流到指定地址
     *
     * @param mediaServerId
     * @param param
     * @return
     */
    @PostMapping("/startSendRtp/{mediaServerId}")
    R<?> startSendRtp(@PathVariable String mediaServerId, @RequestBody Map<String, Object> param) {
        ZlmMediaServer mediaServer = mediaServerService.getOneFromDatabase(mediaServerId);
        return R.ok(mediaServerService.startSendRtp(mediaServer, param));
    }

    /**
     * 停止发送RTP流
     *
     * @param mediaServerId
     * @param param
     * @return
     */
    @PostMapping("/stopSendRtp/{mediaServerId}")
    R<?> stopSendRtp(@PathVariable String mediaServerId, @RequestBody Map<String, Object> param) {
        ZlmMediaServer mediaServer = mediaServerService.getOneFromDatabase(mediaServerId);
        return R.ok(mediaServerService.stopSendRtp(mediaServer, param));
    }

    /**
     * 获取默认的媒体服务器
     *
     * @return
     */
    @GetMapping("/getDefaultMediaServer")
    R<ZlmMediaServer> getDefaultMediaServer() {
        ZlmMediaServer mediaServer = mediaServerService.getDefaultMediaServer();
        return R.ok(mediaServer);
    }

    /**
     * 从数据库中获取指定id的媒体服务器
     *
     * @param id
     * @return
     */
    @GetMapping("/getOneFromDatabase/{id}")
    R<ZlmMediaServer> getOneFromDatabase(@PathVariable String id) {
        ZlmMediaServer mediaServer = mediaServerService.getOneFromDatabase(id);
        return R.ok(mediaServer);
    }

    /**
     * 处理上级平台点播
     *
     * @param platformPlay
     * @return
     */
    @PostMapping("/gb28181PlatformPlay")
    R<Void> gb28181PlatformPlay(@RequestBody Gb28181PlatformPlay platformPlay) {
        log.info("[处理上级平台点播] 设备: {}, 目标地址: {}:{}, SSRC: {}", 
                platformPlay.getQsDevice().getDeviceName(), 
                platformPlay.getDstUrl(), 
                platformPlay.getDstPort(), 
                platformPlay.getSsrc());

        try {
            final ZlmMediaServer zlmMediaServer = mediaServerService.getDefaultMediaServer();
            if (zlmMediaServer == null) {
                log.error("[未找到默认的媒体服务器]");
                return R.fail("未找到默认的媒体服务器");
            }
            
            final String ssrc = platformPlay.getSsrc();
            final String dstUrl = platformPlay.getDstUrl();
            final int dstPort = platformPlay.getDstPort();

            devicePlayService.play(platformPlay.getQsDevice(), false, new ErrorCallback<StreamInfo>() {
                @Override
                public void run(int code, String msg, StreamInfo streamInfo) {
                    log.info("[设备播放回调] code: {}, msg: {}, streamInfo: {}", code, msg, streamInfo);
                    
                    if (code == 0 && streamInfo != null) {
                        try {
                            Map<String, Object> params = new HashMap<>();
                            params.put("secret", zlmMediaServer.getSecret());
                            params.put("vhost", "__defaultVhost__");
                            params.put("app", streamInfo.getApp());
                            params.put("stream", streamInfo.getStream());
                            params.put("ssrc", ssrc);
                            params.put("dst_url", dstUrl);
                            params.put("dst_port", dstPort);
                            params.put("is_udp", platformPlay.getIsUdp() != null ? platformPlay.getIsUdp() : 1);
                            params.put("use_ps", 1);
                            params.put("pt", 96);
                            
                            log.info("[准备调用startSendRtp] params: {}", params);
                            R<?> startSendRtpResult = startSendRtp(zlmMediaServer.getId(), params);
                            log.info("[调用startSendRtp结果] result: {}", startSendRtpResult.getData());
                        } catch (Exception e) {
                            log.error("[调用startSendRtp失败] error: ", e);
                        }
                    } else {
                        log.error("[设备播放失败] code: {}, msg: {}", code, msg);
                    }
                }
            });
        } catch (Exception e) {
            log.error("[处理上级平台点播失败] error: ", e);
            return R.fail("处理失败: " + e.getMessage());
        }

        return R.ok();
    }
}
