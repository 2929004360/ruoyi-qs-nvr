package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 音频文件路径和能力集
*/
public class NET_SPEAK_CAPS extends NetSDKLib.SdkStructure
{
    /**
     * 音频格式,参见结构体定义 {@link NET_SPEAK_AUDIO_FORMAT}
    */
    public NET_SPEAK_AUDIO_FORMAT[] stuAudioFormats = new NET_SPEAK_AUDIO_FORMAT[32];
    /**
     * 音频格式个数
    */
    public int              nAudioFormatNum;
    /**
     * 音频文件路径个数
    */
    public int              nAudioPlayPathNum;
    /**
     * 音频文件路径,参见结构体定义 {@link NET_SPEAK_AUDIO_PLAY_PATH}
    */
    public NET_SPEAK_AUDIO_PLAY_PATH[] stuAudioPlayPaths = new NET_SPEAK_AUDIO_PLAY_PATH[8];
    /**
     * 预留
    */
    public byte[]           byReserved = new byte[1024];

    public NET_SPEAK_CAPS() {
        for(int i = 0; i < stuAudioFormats.length; i++){
            stuAudioFormats[i] = new NET_SPEAK_AUDIO_FORMAT();
        }
        for(int i = 0; i < stuAudioPlayPaths.length; i++){
            stuAudioPlayPaths[i] = new NET_SPEAK_AUDIO_PLAY_PATH();
        }
    }
}

