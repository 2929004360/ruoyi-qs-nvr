package com.ruoyi.job.task;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 设备状态任务
 *
 * @FileName QsDeviceTask
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@Component("qsDeviceTask")
public class QsDeviceTask {

    private static final Logger log = LoggerFactory.getLogger(QsDeviceTask.class);

    private final RemoteQsDeviceService remoteQsDeviceService;

    public QsDeviceTask(RemoteQsDeviceService remoteQsDeviceService) {
        this.remoteQsDeviceService = remoteQsDeviceService;
    }

    public void task() {
        try {
            log.info("开始执行设备状态任务");
            remoteQsDeviceService.task(SecurityConstants.INNER);
            log.info("设备状态任务执行完成");
        } catch (Exception e) {
            log.error("设备状态任务执行异常", e);
        }
    }
}
