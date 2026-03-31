package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.CFG_EM_PREVIEW_MODE;

import static com.ruoyi.dahua.lib.constant.SDKStructureFieldLenth.CFG_MAX_PREVIEW_MODE_SPLIT_TYPE_NUM;
import static com.ruoyi.dahua.lib.constant.SDKStructureFieldLenth.CFG_MAX_VIDEO_CHANNEL_NUM;

/**
 * 视频编码能力
 *
 * @author 47040
 * @since Created at 2021/5/25 22:24
 */
public class CFG_ENCODECAP extends NetSDKLib.SdkStructure {
    /**
     * 实际通道数
     */
    public int              nChannelNum;
    /**
     * 各通道编码能力信息数组
     */
    public CFG_VIDEO_ENCODECAP[] stuVideoEncodeCap = new CFG_VIDEO_ENCODECAP[CFG_MAX_VIDEO_CHANNEL_NUM];
    /**
     * 多画面预览工作模式 枚举值 {@link CFG_EM_PREVIEW_MODE}
     */
    public int              emPreviewMode;
    /**
     * 有效的多画面预览窗口分割种类数
     */
    public int              nSplitModeNum;
    /**
     * 多画面预览窗口分割数信息, 可以为1, 4, 6, 8, 9, 16, 25, 36
     */
    public int[]            anSplitMode = new int[CFG_MAX_PREVIEW_MODE_SPLIT_TYPE_NUM];
    /**
     * -1表示默认[1, 4, 8, 9, 16, …模拟通道数量], 为小于模拟通道数的N的平方数, 如果模拟通道大于8, 也包含8
     * 支持的音频编码采样率
     */
    public int[]            nAudioFrequence = new int[16];
    /**
     * 支持的音频编码采样率的实际个数
     */
    public int              nAudioFrequenceCount;
    /**
     * 最大辅码流路数
    */
    public int              nMaxExtraStream;

    public CFG_ENCODECAP() {
        for (int i = 0; i < stuVideoEncodeCap.length; i++) {
            stuVideoEncodeCap[i] = new CFG_VIDEO_ENCODECAP();
        }
    }
}

