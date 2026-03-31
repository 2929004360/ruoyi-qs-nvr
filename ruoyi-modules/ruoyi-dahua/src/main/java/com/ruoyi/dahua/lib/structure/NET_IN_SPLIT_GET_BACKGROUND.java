package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 获取视频输出背景图输入参数
*/
public class NET_IN_SPLIT_GET_BACKGROUND extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 视频输出通道号
    */
    public int              nChannel;

    public NET_IN_SPLIT_GET_BACKGROUND() {
        this.dwSize = this.size();
    }
}

