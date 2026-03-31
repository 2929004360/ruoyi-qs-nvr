package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 获取视频输出背景图输出参数
*/
public class NET_OUT_SPLIT_GET_BACKGROUND extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 使能
    */
    public int              bEnable;
    /**
     * 背景图名称
    */
    public byte[]           szFileName = new byte[256];

    public NET_OUT_SPLIT_GET_BACKGROUND() {
        this.dwSize = this.size();
    }
}

