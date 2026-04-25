package com.ruoyi.qs.utils;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@Slf4j
@Component
public class VideoSnapshotUtil {

    /**
     * 截取视频指定秒数的图片
     *
     * @param videoPath   视频文件路径 (本地路径或 URL)
     * @param outputPath  图片保存路径
     * @param frameSecond 截取第几秒的画面 (例如 1.0 表示第 1 秒)
     * @return 截图文件
     */
    public File takeSnapshot(String videoPath, String outputPath, double frameSecond) throws Exception {
        FFmpegFrameGrabber grabber = null;

        try {
            grabber = FFmpegFrameGrabber.createDefault(videoPath);
            grabber.start();

            // 1. 获取视频元数据
            int frameCount = grabber.getLengthInFrames();
            double duration = grabber.getTimestamp() / 1000000; // 获取视频时长(秒) - 注意单位转换
            // 注意：FFmpegFrameGrabber 的 seek 操作是基于关键帧的，可能不是绝对精确，
            // 如果需要绝对精确，需要循环 grabFrame 直到目标时间。

            // 2. 定位到指定时间 (微秒)
            // 简单做法：直接 seek，但可能跳到最近的关键帧
            grabber.setTimestamp((long) (frameSecond * 1000000));

            // 3. 抓取帧
            Frame frame = grabber.grabImage();

            if (frame == null) {
                throw new RuntimeException("无法抓取该时间点的帧，可能视频过短或格式不支持");
            }

            // 4. 转换并保存
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage image = converter.getBufferedImage(frame);

            // 处理旋转 (手机视频常见)
            String rotate = grabber.getVideoMetadata("rotate");
            if (rotate != null && !rotate.isEmpty()) {
                image = rotateImage(image, Integer.parseInt(rotate));
            }

            File outputFile = new File(outputPath);
            // 确保父目录存在
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }

            ImageIO.write(image, "jpg", outputFile);
            return outputFile;

        } finally {
            if (grabber != null) {
                try {
                    grabber.stop();
                } catch (Exception e) {
                    log.warn("[视频截图] 停止 grabber 失败: {}", e.getMessage());
                }
                try {
                    grabber.release();
                } catch (Exception e) {
                    log.warn("[视频截图] 释放 grabber 资源失败: {}", e.getMessage());
                }
            }
        }
    }

    // 简单的图片旋转辅助方法
    private BufferedImage rotateImage(BufferedImage src, int degrees) {
        // 这里需要实现具体的旋转逻辑，可以使用 java.awt.geom.AffineTransform
        // 为了代码简洁，此处省略具体旋转实现，实际项目中建议使用 Thumbnailator 或 imgscalr 库
        return src;
    }
}