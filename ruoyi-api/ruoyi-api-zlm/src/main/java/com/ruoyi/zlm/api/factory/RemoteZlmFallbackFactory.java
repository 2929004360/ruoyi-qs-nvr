package com.ruoyi.zlm.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.zlm.api.RemoteZlmService;
import com.ruoyi.zlm.api.domain.Gb28181PlatformPlay;
import com.ruoyi.zlm.api.domain.Gb28181PlatformPlayback;
import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * zlm接口服务降级处理
 *
 * @FileName RemoteZlmFallbackFactory
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@Component
public class RemoteZlmFallbackFactory implements FallbackFactory<RemoteZlmService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteZlmFallbackFactory.class);

    @Override
    public RemoteZlmService create(Throwable throwable) {
        log.error("zlm服务调用失败:{}", throwable.getMessage());

        return new RemoteZlmService() {
            @Override
            public R<Void> releaseSsrc(String mediaServerId, String ssrc, String inner) {
                return R.fail("zlm接口服务调用失败，releaseSsrc:" + throwable.getMessage());
            }

            @Override
            public R<Void> closeRTPServer(String mediaServerId, RtpServerParam rtpServer, String inner) {
                return R.fail("zlm接口服务调用失败，closeRTPServer:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> connectRtpServer(String mediaServerId, String address, int port, String stream, String inner) {
                return R.fail("zlm接口服务调用失败，connectRtpServer:" + throwable.getMessage());
            }

            @Override
            public R<?> startSendRtp(String mediaServerId, Map<String, Object> param, String inner) {
                return R.fail("zlm接口服务调用失败，startSendRtp:" + throwable.getMessage());
            }

            @Override
            public R<?> stopSendRtp(String mediaServerId, Map<String, Object> param, String inner) {
                return R.fail("zlm接口服务调用失败，stopSendRtp:" + throwable.getMessage());
            }

            @Override
            public R<ZlmMediaServer> getDefaultMediaServer(String inner) {
                return R.fail("zlm接口服务调用失败，getDefaultMediaServer:" + throwable.getMessage());
            }

            @Override
            public R<ZlmMediaServer> getOneFromDatabase(String id, String inner) {
                return R.fail("zlm接口服务调用失败，getOneFromDatabase:" + throwable.getMessage());
            }

            @Override
            public R<Void> gb28181PlatformPlay(Gb28181PlatformPlay platformPlay, String inner) {
                return R.fail("zlm接口服务调用失败，gb28181PlatformPlay: " + throwable.getMessage());
            }

            @Override
            public R<Void> gb28181PlatformPlayback(Gb28181PlatformPlayback platformPlayback, String inner) {
                return R.fail("zlm接口服务调用失败，gb28181PlatformPlayback: " + throwable.getMessage());
            }

            @Override
            public R<Void> stopPlayback(Long deviceId, String deviceType, String stream, String inner) {
                return R.fail("zlm接口服务调用失败，stopPlayback: " + throwable.getMessage());
            }
        };
    }
}
