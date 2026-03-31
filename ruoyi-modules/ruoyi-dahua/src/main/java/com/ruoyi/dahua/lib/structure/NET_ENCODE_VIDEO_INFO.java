package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.NET_EM_FORMAT_TYPE;
import com.ruoyi.dahua.lib.enumeration.NET_EM_IMAGE_QUALITY;
import com.ruoyi.dahua.lib.enumeration.NET_EM_VIDEO_COMPRESSION;

/**
 * 主(辅)码流视频格式(f6/f5/bin)
 *
 * @author 47040
 * @since Created at 2021/5/26 22:08
 */
public class NET_ENCODE_VIDEO_INFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 码流类型枚举值,设置和获取时都需要设置值 {@link NET_EM_FORMAT_TYPE}
     */
    public int              emFormatType;
    /**
     * 视频使能
     */
    public int              bVideoEnable;
    /**
     * 视频压缩格式 枚举值 {@link NET_EM_VIDEO_COMPRESSION}
     */
    public int              emCompression;
    /**
     * 视频宽度
     */
    public int              nWidth;
    /**
     * 视频高度
     */
    public int              nHeight;
    /**
     * 码流控制模式 枚举值 {@link NetSDKLib.NET_EM_BITRATE_CONTROL}
     */
    public int              emBitRateControl;
    /**
     * 视频码流(kbps)
     */
    public int              nBitRate;
    /**
     * 视频帧率
     */
    public float            nFrameRate;
    /**
     * I帧间隔(1-100)，比如50表示每49个B帧或P帧，设置一个I帧。
     */
    public int              nIFrameInterval;
    /**
     * 图像质量 枚举值 {@link NET_EM_IMAGE_QUALITY}
     */
    public int              emImageQuality;
    /**
     * 图像组，即I帧间隔 1-150，比如50表示每49个B帧或P帧，设置一个I帧。0表示自动调整I帧间隔
    */
    public int              nAiGOP;

    public NET_ENCODE_VIDEO_INFO() {
        dwSize = this.size();
    }
}

