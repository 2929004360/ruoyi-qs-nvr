package com.ruoyi.qs.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.qs.api.RemoteQsDeviceSnapshotService;
import com.ruoyi.qs.api.domain.QsDeviceSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 设备抓图服务降级处理
 *
 * @author ruoyi
 */
@Component
public class RemoteQsDeviceSnapshotFallbackFactory implements FallbackFactory<RemoteQsDeviceSnapshotService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteQsDeviceSnapshotFallbackFactory.class);

    @Override
    public RemoteQsDeviceSnapshotService create(Throwable throwable) {
        log.error("设备抓图服务调用失败:{}", throwable.getMessage());

        return new RemoteQsDeviceSnapshotService() {
            @Override
            public R<Long> add(QsDeviceSnapshot qsDeviceSnapshot, String inner) {
                return R.fail("新增设备抓图失败:" + throwable.getMessage());
            }

            @Override
            public R<List<QsDeviceSnapshot>> list(QsDeviceSnapshot qsDeviceSnapshot, String inner) {
                return R.fail("查询设备抓图列表失败:" + throwable.getMessage());
            }

            @Override
            public R<QsDeviceSnapshot> getInfo(Long id, String inner) {
                return R.fail("获取设备抓图详细信息失败:" + throwable.getMessage());
            }
        };
    }
}
