package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_SetSplitOSDEx接口输入参数(设置解码窗口输出OSD信息)
 * @date 2022/06/22 20:38:57
 */
public class NET_IN_SPLIT_SET_OSD_EX extends NetSDKLib.SdkStructure {
    public int              dwSize;
    /**
     * 通道号
     */
    public int              nChannel;
    /**
     * 窗口号
     */
    public int              nWindow;
    /**
     * OSD数量
     */
    public int              nOSDNum;
    /**
     * OSD信息
     */
    public NET_SPLIT_OSD[]  stuOSD = new NET_SPLIT_OSD[256];

    public NET_IN_SPLIT_SET_OSD_EX() {
        for (int i = 0; i < stuOSD.length; i++) {
            stuOSD[i] = new NET_SPLIT_OSD();
        }
        this.dwSize = this.size();
    }
}

