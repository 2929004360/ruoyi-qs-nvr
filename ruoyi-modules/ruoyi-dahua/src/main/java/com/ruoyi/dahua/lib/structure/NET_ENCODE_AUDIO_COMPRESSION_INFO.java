package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description
 * @date 2021/8/4 14:24
 */
public class NET_ENCODE_AUDIO_COMPRESSION_INFO extends NetSDKLib.SdkStructure {
    public int              dwSize;
    public int              bAudioEnable;                         // 音频使能
    /** 参考{@link com.ruoyi.dahua.lib.enumeration.NET_EM_FORMAT_TYPE}*/
    public int              emFormatType;                         // 码流类型,设置和获取时都需要设置值
    /** 参考{@link NetSDKLib.NET_EM_AUDIO_FORMAT} */
    public   int            emCompression;                        // 音频压缩模式

    public NET_ENCODE_AUDIO_COMPRESSION_INFO( ) {
        this.dwSize = this.size();
    }
}

