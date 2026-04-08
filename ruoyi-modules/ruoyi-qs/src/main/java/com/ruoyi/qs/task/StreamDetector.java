package com.ruoyi.qs.task;

import com.ruoyi.qs.api.domain.QsDevice;
import lombok.Data;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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
            CompletableFuture<StreamResult> future = CompletableFuture.supplyAsync(() -> {
                return detectSingle(device.getId(), device.getLiveAddress());
            }, taskExecutor).orTimeout(15, TimeUnit.SECONDS); // 兜底超时

            futures.add(future);
        }

        // 等待所有任务完成
        for (CompletableFuture<StreamResult> future : futures) {
            try {
                results.add(future.join());
            } catch (Exception e) {

            }
        }
        return results;
    }

    /**
     * 单个流检测逻辑
     */
    public StreamResult detectSingle(Long id, String url) {
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
            return new StreamResult(id, "OFFLINE");
        } finally {
            try {
                grabber.stop();
                grabber.release();
            } catch (Exception e) {

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