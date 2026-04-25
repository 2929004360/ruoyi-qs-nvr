package com.ruoyi.qs.utils;

import com.ruoyi.qs.api.domain.QsDevice;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class StreamDetector {

    /**
     * 批量检测流状态
     */
    public List<StreamResult> batchDetect(List<QsDevice> qsDeviceList, ThreadPoolTaskExecutor taskExecutor) {
        List<StreamResult> results = new ArrayList<>();
        List<CompletableFuture<StreamResult>> futures = new ArrayList<>();

        for (QsDevice device : qsDeviceList) {
            // 使用自定义线程池异步执行
            CompletableFuture<StreamResult> future = CompletableFuture.supplyAsync(() ->
                    detectSingle(device.getId(), device.getLiveAddress()), taskExecutor).orTimeout(15, TimeUnit.SECONDS); // 兜底超时

            futures.add(future);
        }

        // 等待所有任务完成
        for (CompletableFuture<StreamResult> future : futures) {
            try {
                results.add(future.join());
            } catch (Exception e) {
                log.error("[流检测失败] 批量检测时发生异常: {}", e.getMessage());
            }
        }
        return results;
    }

    /**
     * 单个流检测逻辑
     */
    public StreamResult detectSingle(Long id, String url) {
        if (url == null || url.isEmpty()) {
            return new StreamResult(id, "OFFLINE");
        }

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(url);

        try {
            // --- 核心配置 ---
            grabber.setOption("stimeout", "3000000"); // 3秒超时
            grabber.setOption("reconnect", "0");      // 禁用重连
            grabber.setOption("protocol_whitelist", "file,http,https,tcp,tls,ws,wss,crypto,udp,rtp,rtcp,rtmp,rtmpt,subfile,pipe,data");
            grabber.setOption("user_agent", "Mozilla/5.0 (compatible; StreamChecker)");

            grabber.start();

            if (grabber.getFormat() != null) {
                return new StreamResult(id, "ON");
            }

            return new StreamResult(id, "OFFLINE");

        } catch (Exception e) {
            log.debug("[流检测] 设备ID: {}, URL: {}, 检测结果: OFFLINE, 原因: {}", id, url, e.getMessage());
            return new StreamResult(id, "OFFLINE");
        } finally {
            try {
                if (grabber != null) {
                    grabber.stop();
                    grabber.release();
                }
            } catch (Exception e) {
                log.debug("[流检测] 释放资源失败: {}", e.getMessage());
            }
        }
    }

    // 结果封装类
    @Data
    public static class StreamResult {
        public Long id;
        public String status;

        public StreamResult(Long id, String status) {
            this.id = id;
            this.status = status;
        }
    }
}