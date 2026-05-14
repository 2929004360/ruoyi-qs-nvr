package com.ruoyi.qs.service;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.gb28181.api.RemoteGb28181Service;
import com.ruoyi.qs.api.domain.QsGb28181Platform;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 国标平台同步服务
 * 用于处理设备和通道变化时的目录推送
 */
@Slf4j
@Service
public class Gb28181PlatformSyncService {

    @Autowired
    @Lazy
    private IQsGb28181PlatformService qsGb28181PlatformService;

    @Autowired
    private RemoteGb28181Service remoteGb28181Service;

    // 防抖延迟时间（毫秒）
    private static final long DEBOUNCE_DELAY = 3000L;

    // 定时任务调度器
    private ScheduledThreadPoolExecutor scheduler;

    // 标记是否有待执行的任务
    private final AtomicBoolean hasPendingTask = new AtomicBoolean(false);

    @PostConstruct
    public void init() {
        scheduler = new ScheduledThreadPoolExecutor(1);
        log.info("[国标平台同步] 初始化完成，防抖延迟时间: {}ms", DEBOUNCE_DELAY);
    }

    /**
     * 触发目录推送（带防抖）
     * 短时间内多次触发只会执行最后一次
     */
    public void triggerPushCatalog() {
        log.info("[国标平台同步] 触发目录推送请求");

        // 如果已经有待执行的任务，取消它
        if (hasPendingTask.get()) {
            log.info("[国标平台同步] 取消之前的待执行任务，防抖生效");
        }

        // 设置待执行标记
        hasPendingTask.set(true);

        // 延迟执行
        scheduler.schedule(() -> {
            try {
                log.info("[国标平台同步] 防抖延迟结束，开始执行推送");
                pushCatalogToAllOnlinePlatforms();
            } finally {
                hasPendingTask.set(false);
            }
        }, DEBOUNCE_DELAY, TimeUnit.MILLISECONDS);

        log.info("[国标平台同步] 目录推送已加入调度队列，将在 {}ms 后执行", DEBOUNCE_DELAY);
    }

    /**
     * 推送目录到所有在线平台
     */
    @Async
    public void pushCatalogToAllOnlinePlatforms() {
        long startTime = System.currentTimeMillis();
        log.info("[国标平台同步] ========== 开始推送目录 ==========");

        try {
            // 查询所有启用的平台
            QsGb28181Platform queryPlatform = new QsGb28181Platform();
            queryPlatform.setEnable(1);
            List<QsGb28181Platform> platformList = qsGb28181PlatformService.selectQsGb28181PlatformList(queryPlatform);

            if (platformList == null || platformList.isEmpty()) {
                log.info("[国标平台同步] 没有启用的平台，跳过推送");
                return;
            }

            log.info("[国标平台同步] 找到 {} 个启用的平台", platformList.size());

            int onlineCount = 0;
            int successCount = 0;
            int failCount = 0;

            // 为每个在线的平台推送目录
            for (QsGb28181Platform platform : platformList) {
                // 只推送在线的平台
                if (Integer.valueOf(1).equals(platform.getStatus())) {
                    onlineCount++;
                    long platformStartTime = System.currentTimeMillis();
                    
                    try {
                        log.info("[国标平台同步] [{}/{}] 正在推送目录到平台: [{}] ID: {}", 
                                onlineCount, platformList.size(), platform.getName(), platform.getId());
                        
                        remoteGb28181Service.pushCatalog(platform.getId(), SecurityConstants.INNER);
                        
                        long platformEndTime = System.currentTimeMillis();
                        log.info("[国标平台同步] [{}/{}] 推送目录到平台: [{}] 成功！耗时: {}ms", 
                                onlineCount, platformList.size(), platform.getName(), (platformEndTime - platformStartTime));
                        successCount++;
                    } catch (Exception e) {
                        long platformEndTime = System.currentTimeMillis();
                        log.error("[国标平台同步] [{}/{}] 推送目录到平台: [{}] 失败！耗时: {}ms", 
                                onlineCount, platformList.size(), platform.getName(), (platformEndTime - platformStartTime), e);
                        failCount++;
                    }
                } else {
                    log.debug("[国标平台同步] 平台: [{}] 状态为离线，跳过推送", platform.getName());
                }
            }

            long endTime = System.currentTimeMillis();
            log.info("[国标平台同步] ========== 推送目录完成 ==========");
            log.info("[国标平台同步] 总耗时: {}ms", (endTime - startTime));
            log.info("[国标平台同步] 启用平台数: {}，在线平台数: {}", platformList.size(), onlineCount);
            log.info("[国标平台同步] 推送成功: {} 个，推送失败: {} 个", successCount, failCount);

        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            log.error("[国标平台同步] ========== 推送目录异常 ==========");
            log.error("[国标平台同步] 总耗时: {}ms", (endTime - startTime));
            log.error("[国标平台同步] 推送目录异常", e);
        }
    }
}
