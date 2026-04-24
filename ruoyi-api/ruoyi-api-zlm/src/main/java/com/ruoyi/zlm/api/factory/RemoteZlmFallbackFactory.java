package com.ruoyi.zlm.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.gb28181.api.domain.SsrcTransaction;
import com.ruoyi.zlm.api.RemoteZlmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

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
        };
    }
}
