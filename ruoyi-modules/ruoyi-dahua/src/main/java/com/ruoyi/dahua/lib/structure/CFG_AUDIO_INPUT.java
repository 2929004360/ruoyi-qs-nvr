package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 视频输入配置
*/
public class CFG_AUDIO_INPUT extends NetSDKLib.SdkStructure
{
    /**
     * 输入音频源. 如果音频通道的输入是由多路合成，用|分割., 例："Mic|LineIn|Remote" 表示此音频通道由Mic、LineIn和远程通道的音频输入构成., "Coaxial" 同轴口音频, "BNC" 本地BNC口音频, "HDCVI_BNC" 远程HDCVI设备音频, "LineIn" 线性输入, "Mic" 麦克风输入, "MicOut" 麦克输出, "Remote" 远程通道(仅对画中画通道有意义，表示画中画主画面为远程通道时，将当前远程通道的音频作为音频输入)
    */
    public byte[]           szAudioSource = new byte[256];

    public CFG_AUDIO_INPUT() {
    }
}

