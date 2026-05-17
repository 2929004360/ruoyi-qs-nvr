package com.ruoyi.dahua.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 大华设备视频参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DahuaVideoParam implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 码流类型: 0-主码流, 1-辅码流1, 2-辅码流2
     */
    private Integer formatType;

    /**
     * 视频使能: 0-关闭, 1-开启
     */
    private Integer videoEnable;

    /**
     * 视频压缩格式
     */
    private Integer compression;

    /**
     * 视频宽度
     */
    private Integer width;

    /**
     * 视频高度
     */
    private Integer height;

    /**
     * 码流控制模式
     */
    private Integer bitRateControl;

    /**
     * 视频码流(kbps)
     */
    private Integer bitRate;

    /**
     * 视频帧率
     */
    private Float frameRate;

    /**
     * I帧间隔
     */
    private Integer iframeInterval;

    /**
     * 图像质量
     */
    private Integer imageQuality;
}
