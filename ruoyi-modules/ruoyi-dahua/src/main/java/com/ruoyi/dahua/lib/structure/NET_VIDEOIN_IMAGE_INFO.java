package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * 视频输入图像属性配置
 * 对应枚举：NET_EM_CFG_VIDEOIN_IMAGE_OPT
 *
 * @author 47040
 * @since Created at 2026-05-17
 */
public class NET_VIDEOIN_IMAGE_INFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int dwSize;
    /**
     * 亮度
     */
    public int nBrightness;
    /**
     * 对比度
     */
    public int nContrast;
    /**
     * 饱和度
     */
    public int nSaturation;
    /**
     * 色度
     */
    public int nChroma;
    /**
     * 锐度
     */
    public int nSharpness;
    /**
     * 色调
     */
    public int nHue;
    /**
     * 增益
     */
    public int nGain;
    /**
     * 黑白模式
     * 0-彩色
     * 1-黑白
     */
    public int nBlackWhiteMode;

    public NET_VIDEOIN_IMAGE_INFO() {
        dwSize = this.size();
    }
}
