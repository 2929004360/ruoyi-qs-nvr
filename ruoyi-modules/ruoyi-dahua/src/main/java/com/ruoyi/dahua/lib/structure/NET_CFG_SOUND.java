package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 声音配置
*/
public class NET_CFG_SOUND extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 报警语音提示文件路径
    */
    public byte[]           szAlarmSoundPath = new byte[256];
    /**
     * 报警持续时间,单位：秒
    */
    public int              nAlarmSoundDuration;
    /**
     * 报警语音提示音量,范围[0, 100]
    */
    public int              nAlarmSoundVolume;

    public NET_CFG_SOUND() {
        this.dwSize = this.size();
    }
}

