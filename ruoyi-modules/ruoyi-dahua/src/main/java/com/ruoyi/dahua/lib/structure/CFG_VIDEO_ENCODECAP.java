package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * 视频输入前端能力集
 *
 * @author 47040
 * @since Created at 2021/5/25 22:26
 */
public class CFG_VIDEO_ENCODECAP extends NetSDKLib.SdkStructure {
    /**
     * CIF P帧最大值 单位Kbits, 默认值 40
     */
    public int              nMaxCIFFrame;
    /**
     * IF P帧最小值 单位Kbits, 默认值 7
     */
    public int              nMinCIFFrame;
    /**
     * 支持的最大编码音频数，用于双音频,默认 1
     */
    public int              nMaxEncodeAudios;
    /**
     * 保留字节，用于字节对齐
     */
    public byte[]           byReserved = new byte[4];
}

