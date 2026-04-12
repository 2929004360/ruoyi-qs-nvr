package com.ruoyi.qs.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 视频监控设备服务降级处理
 *
 * @FileName RemoteQsDeviceFallbackFactory
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@Component
public class RemoteQsDeviceFallbackFactory implements FallbackFactory<RemoteQsDeviceService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteQsDeviceFallbackFactory.class);

    @Override
    public RemoteQsDeviceService create(Throwable throwable) {
        log.error("视频监控设备服务调用失败:{}", throwable.getMessage());

        return new RemoteQsDeviceService() {
            @Override
            public R<List<QsDevice>> list(QsDevice qsDevice, String source) {
                return R.fail("查询视频监控设备失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> updateQsDeviceStatusList(Set<Long> onlineDeviceSet, String deviceStatus, String inner) {
                return R.fail("更新设备在线状态失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> updateQsDevice(QsDevice qsDevice, String inner) {
                return R.fail("修改视频监控设备失败:" + throwable.getMessage());
            }

            @Override
            public R<QsDevice> getQsDeviceStream(String stream, String inner) {
                return R.fail("根据流id获取视频监控设备失败:" + throwable.getMessage());
            }

            @Override
            public R<QsDevice> getQsDeviceInfo(Long id, String inner) {
                return R.fail("获取视频监控设备详细信息失败:" + throwable.getMessage());
            }

            @Override
            public R<QsDevice> task(String inner) {
                return R.fail("设备状态任务失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> cleanRecordPlanId(Long planId, String inner) {
                return R.fail("设备清理设备计划id失败:" + throwable.getMessage());
            }

            @Override
            public R<List<QsDevice>> queryByIds(List<Long> startDeviceIdList, String inner) {
                return R.fail("根据设备id集合查询设备信息失败:" + throwable.getMessage());
            }
        };
    }
}
