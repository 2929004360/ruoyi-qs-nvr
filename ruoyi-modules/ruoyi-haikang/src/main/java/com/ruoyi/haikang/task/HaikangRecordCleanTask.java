package com.ruoyi.haikang.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 海康录像临时文件清理任务
 */
@Slf4j
@Component
public class HaikangRecordCleanTask {

    @Value("${file.path}")
    private String filePath;

    /**
     * 每天半夜12点执行，删除超过1天的临时录像文件
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanOldRecordFiles() {
        log.info("开始清理海康临时录像文件...");
        try {
            String recordBasePath = filePath + "/haikang/record";
            File recordDir = new File(recordBasePath);
            
            if (recordDir.exists() && recordDir.isDirectory()) {
                File[] deviceDirs = recordDir.listFiles();
                if (deviceDirs != null) {
                    long currentTime = System.currentTimeMillis();
                    long oneDayMs = 24 * 60 * 60 * 1000; // 1天
                    
                    int deletedCount = 0;
                    for (File deviceDir : deviceDirs) {
                        if (deviceDir.isDirectory()) {
                            File[] timestampDirs = deviceDir.listFiles();
                            if (timestampDirs != null) {
                                for (File timestampDir : timestampDirs) {
                                    try {
                                        long timestamp = Long.parseLong(timestampDir.getName());
                                        if (currentTime - timestamp > oneDayMs) {
                                            // 删除整个目录
                                            deleteDirectory(timestampDir);
                                            deletedCount++;
                                        }
                                    } catch (NumberFormatException e) {
                                        // 不是时间戳的目录，跳过
                                    }
                                }
                            }
                        }
                    }
                    log.info("海康临时录像文件清理完成，共删除 {} 个目录", deletedCount);
                }
            }
        } catch (Exception e) {
            log.error("海康临时录像文件清理异常", e);
        }
    }

    /**
     * 递归删除目录
     */
    private void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteDirectory(child);
                }
            }
        }
        dir.delete();
    }
}