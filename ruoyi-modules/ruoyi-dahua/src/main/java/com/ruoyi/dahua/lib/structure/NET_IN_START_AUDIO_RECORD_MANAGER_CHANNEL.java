package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_StartAudioRecordManagerChannel 接口输入参数
*/
public class NET_IN_START_AUDIO_RECORD_MANAGER_CHANNEL extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 音频通道号
    */
    public int              nChannel;
    /**
     * 码流类型,视频伴音的音频类型:"Main","Extra1",Extra2" "Extra3";Mic的音频类型："TalkBack"
    */
    public byte[]           szStream = new byte[16];
    /**
     * 录像文件路径
    */
    public byte[]           szPath = new byte[256];

    public NET_IN_START_AUDIO_RECORD_MANAGER_CHANNEL() {
        this.dwSize = this.size();
    }
}

