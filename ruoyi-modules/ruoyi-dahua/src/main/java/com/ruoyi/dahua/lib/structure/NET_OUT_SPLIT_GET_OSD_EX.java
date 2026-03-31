package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_GetSplitOSDEx 接口输出参数(获取解码窗口输出OSD信息)
 * @date 2022/06/22 20:38:56
 */
public class NET_OUT_SPLIT_GET_OSD_EX extends NetSDKLib.SdkStructure {
    public int              dwSize;
    /**
     * OSD数量
     */
    public int              nOSDNum;
    /**
     * OSD信息
     */
    public NET_SPLIT_OSD[]  stuOSD = new NET_SPLIT_OSD[256];

    public NET_OUT_SPLIT_GET_OSD_EX() {
        for (int i = 0; i < stuOSD.length; i++) {
            stuOSD[i] = new NET_SPLIT_OSD();
        }
        this.dwSize = this.size();
    }
}

