package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetPicMapMarkList 接口输入参数
*/
public class NET_IN_GET_PIC_MAP_MARK_LIST extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 通道号列表个数
    */
    public int              nChannelCount;
    /**
     * 通道号列表
    */
    public int[]            nChannels = new int[1024];

    public NET_IN_GET_PIC_MAP_MARK_LIST() {
        this.dwSize = this.size();
    }
}

