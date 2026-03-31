package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * NET_ELEMENT_INFO 播报元素信息
*/
public class NET_ELEMENT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 播放事件类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_VOICE_BROADCAST_EVENT_TYPE}
    */
    public int              emEventType;
    /**
     * 播放的内容类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_VOICE_BROADCAST_TYPE}
    */
    public int              emType;
    /**
     * 使能播报元素
    */
    public int              bEnable;
    /**
     * 语音播放内容数组个数
    */
    public int              nVoiceBroadcastNum;
    /**
     * 播放内容,Type是"Text"时表示播放内容,Type是"File"时表示播放的文件名
    */
    public byte[]           szContent = new byte[128];
    /**
     * 语音播放内容数组,	本字段优先级高于Type和Content,参见结构体定义 {@link NET_VOICE_BROADCAST_INFO}
    */
    public NET_VOICE_BROADCAST_INFO[] stuVoiceBroadcast = new NET_VOICE_BROADCAST_INFO[16];
    /**
     * 循环播放间隔,单位s, 0表示只播放一次
    */
    public int              nInterval;
    /**
     * 循环播放持续时间,单位s
    */
    public int              nDuration;
    /**
     * 保留字节
    */
    public byte[]           szResvered = new byte[1024];

    public NET_ELEMENT_INFO() {
        for(int i = 0; i < stuVoiceBroadcast.length; i++){
            stuVoiceBroadcast[i] = new NET_VOICE_BROADCAST_INFO();
        }
    }
}

