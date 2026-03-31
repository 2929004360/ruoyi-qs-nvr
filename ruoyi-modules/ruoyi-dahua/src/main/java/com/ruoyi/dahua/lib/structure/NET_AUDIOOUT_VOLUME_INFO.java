package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 音频输出音量配置
 * @date 2022/05/09
 */
public class NET_AUDIOOUT_VOLUME_INFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     *  音频输出音量
     */
    public int              nVolume;

    public NET_AUDIOOUT_VOLUME_INFO(){
        this.dwSize = this.size();
    }
}

