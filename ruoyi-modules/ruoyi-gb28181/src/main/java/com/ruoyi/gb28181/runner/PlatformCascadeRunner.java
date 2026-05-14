package com.ruoyi.gb28181.runner;

import com.ruoyi.gb28181.task.platform.PlatformCascadeTaskManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 平台级联启动器
 *
 * @author ruoyi
 */
@Component
@Slf4j
public class PlatformCascadeRunner implements CommandLineRunner, DisposableBean {

    @Autowired
    private PlatformCascadeTaskManager platformCascadeTaskManager;

    @Override
    public void run(String... args) throws Exception {
        log.info("[平台级联] 开始初始化平台级联任务");
        try {
            platformCascadeTaskManager.startAll();
            log.info("[平台级联] 平台级联任务初始化成功");
        } catch (Exception e) {
            log.error("[平台级联] 平台级联任务初始化失败", e);
        }
    }

    @Override
    public void destroy() throws Exception {
        log.info("[平台级联] 正在停止所有平台级联任务");
        try {
            platformCascadeTaskManager.stopAll();
            log.info("[平台级联] 所有平台级联任务已停止");
        } catch (Exception e) {
            log.error("[平台级联] 停止平台级联任务失败", e);
        }
    }
}
