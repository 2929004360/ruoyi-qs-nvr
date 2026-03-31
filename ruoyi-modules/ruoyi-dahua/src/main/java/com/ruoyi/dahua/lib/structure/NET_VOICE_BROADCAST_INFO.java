package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * NET_VOICE_BROADCAST_INFO 语音播放内容信息
*/
public class NET_VOICE_BROADCAST_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 播放内容,Type是"Text"时表示播放内容,Type是"File"时表示播放的文件名
    */
    public byte[]           szContent = new byte[128];
    /**
     * 播放的内容类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_VOICE_BROADCAST_TYPE}
    */
    public int              emType;
    /**
     * 保留字节
    */
    public byte[]           szResvered = new byte[204];

    public NET_VOICE_BROADCAST_INFO() {
    }
}

