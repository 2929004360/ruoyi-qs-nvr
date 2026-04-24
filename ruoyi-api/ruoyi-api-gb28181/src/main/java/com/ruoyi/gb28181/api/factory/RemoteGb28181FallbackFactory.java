package com.ruoyi.gb28181.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.gb28181.api.RemoteGb28181Service;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * gb28181服务降级处理
 *
 * @FileName RemoteGb28181FallbackFactory
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
@Component
public class RemoteGb28181FallbackFactory implements FallbackFactory<RemoteGb28181Service> {

    private static final Logger log = LoggerFactory.getLogger(RemoteGb28181FallbackFactory.class);

    @Override
    public RemoteGb28181Service create(Throwable throwable) {
        log.error("gb28181服务调用失败:{}", throwable.getMessage());
        return new RemoteGb28181Service() {
            @Override
            public R<Device> getDeviceByDeviceId(String gbDeviceId, String inner) {
                return R.fail("gb28181 根据设备id获取设备失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> playStreamCmd(RtpServerParam rtpServer, String inner) {
                return R.fail("gb28181 请求预览视频流失败:" + throwable.getMessage());
            }

            @Override
            public R<DeviceChannel> getDeviceChannelByChannelId(String gbDeviceId, String gbChannelId, String inner) {
                return R.fail("gb28181 根据设备id和通道获取设备通道:" + throwable.getMessage());
            }

            @Override
            public R<Void> streamByeCmd(RtpServerParam rtpServer, String inner) {
                return R.fail("gb28181 请求停止预览视频流失败:" + throwable.getMessage());
            }
        };
    }
}
