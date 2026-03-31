package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetDevCaps NET_REMOTE_SPEAK_CAPS 入参
*/
public class NET_IN_REMOTE_SPEAK_CAPS extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 通道数
    */
    public int              nChannelNum;
    /**
     * 通道列表
    */
    public int[]            nChannels = new int[128];

    public NET_IN_REMOTE_SPEAK_CAPS() {
        this.dwSize = this.size();
    }
}

