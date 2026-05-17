package com.ruoyi.qs.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.qs.api.RemoteQsDeviceAlarmService;
import com.ruoyi.qs.api.domain.QsDeviceAlarm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 设备告警服务降级处理
 *
 * @author ruoyi
 */
@Component
public class RemoteQsDeviceAlarmFallbackFactory implements FallbackFactory<RemoteQsDeviceAlarmService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteQsDeviceAlarmFallbackFactory.class);

    @Override
    public RemoteQsDeviceAlarmService create(Throwable throwable) {
        log.error("设备告警服务调用失败:{}", throwable.getMessage());

        return new RemoteQsDeviceAlarmService() {
            @Override
            public R<Long> add(QsDeviceAlarm qsDeviceAlarm, String inner) {
                return R.fail("新增设备告警失败:" + throwable.getMessage());
            }

            @Override
            public R<List<QsDeviceAlarm>> list(QsDeviceAlarm qsDeviceAlarm, String inner) {
                return R.fail("查询设备告警列表失败:" + throwable.getMessage());
            }

            @Override
            public R<QsDeviceAlarm> getInfo(Long id, String inner) {
                return R.fail("获取设备告警详细信息失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> edit(QsDeviceAlarm qsDeviceAlarm, String inner) {
                return R.fail("修改设备告警失败:" + throwable.getMessage());
            }
        };
    }
}
