package com.ruoyi.jt1078.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.jt1078.api.RemoteJt1078Service;
import com.ruoyi.jt1078.api.domain.Jt1078Device;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * jt1078服务降级处理
 *
 * @author fengcheng
 */
@Slf4j
@Component
public class RemoteJt1078FallbackFactory implements FallbackFactory<RemoteJt1078Service> {

    @Override
    public RemoteJt1078Service create(Throwable throwable) {
        log.error("jt1078服务调用失败:{}", throwable.getMessage());
        return new RemoteJt1078Service() {
            @Override
            public R<Jt1078Device> getDeviceByMobileNo(String mobileNo, String inner) {
                return R.fail("获取jt1078设备失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> playStreamCmd(RtpServerParam rtpServer, String inner) {
                return R.fail("jt1078请求预览视频流失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> streamByeCmd(RtpServerParam rtpServer, String inner) {
                return R.fail("jt1078停止视频流失败:" + throwable.getMessage());
            }

            @Override
            public R<List<Jt1078Device>> getAllDevices(String inner) {
                return R.fail("jt1078获取全部设备失败:" + throwable.getMessage());
            }
        };
    }
}
