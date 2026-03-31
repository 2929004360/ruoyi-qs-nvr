package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description 时间预案播放语音控制能力 CFG_VOICE_PLAY_PLAN dhconfig.h
 * @date 2021/12/7 15:07
 */
public class CFG_VOICE_PLAY_PLAN extends NetSDKLib.SdkStructure {
    public int              bSupport;                             // 是否支持时间预案语音播报
    public int              nVoiceTaskNum;                        // 支持时间预案的语音个数
}

