package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_SetSplitOSDEx 接口输出参数(设置解码窗口输出OSD信息)
 * @date 2022/06/22 09:56:20
 */
public class NET_OUT_SPLIT_SET_OSD_EX extends NetSDKLib.SdkStructure {
    public int              dwSize;

    public NET_OUT_SPLIT_SET_OSD_EX() {
        this.dwSize = this.size();
    }
}

