package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 音频格式
*/
public class NET_SPEAK_AUDIO_FORMAT extends NetSDKLib.SdkStructure
{
    /**
     * 音频编码格式,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_SPEAK_AUDIO_TYPE}
    */
    public int              emFormat;
    /**
     * 预留
    */
    public byte[]           byReserved = new byte[1020];

    public NET_SPEAK_AUDIO_FORMAT() {
    }
}

