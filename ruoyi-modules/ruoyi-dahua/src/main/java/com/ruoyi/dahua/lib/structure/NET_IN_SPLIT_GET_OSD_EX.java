package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_GetSplitOSDEx 接口输入参数(获取解码窗口输出OSD信息)
 * @date 2022/06/22 09:56:20
 */
public class NET_IN_SPLIT_GET_OSD_EX extends NetSDKLib.SdkStructure {
    public int              dwSize;
    /**
     * 通道号
     */
    public int              nChannel;
    /**
     * 窗口号
     */
    public int              nWindow;

    public NET_IN_SPLIT_GET_OSD_EX() {
        this.dwSize = this.size();
    }
}

