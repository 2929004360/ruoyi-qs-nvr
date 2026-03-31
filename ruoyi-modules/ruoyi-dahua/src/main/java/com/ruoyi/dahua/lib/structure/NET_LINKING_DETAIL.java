package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 云台联动灯光类型
 * @origin autoTool
 * @date 2023/10/19 17:27:54
 */
public class NET_LINKING_DETAIL extends NetSDKLib.SdkStructure {
    /**
     * /闪烁灯光信息
     */
    public NET_FILCKER_LIGHTING stuFilckerLighting = new NET_FILCKER_LIGHTING();
    /**
     * /常亮灯光信息
     */
    public NET_KEEP_LIGHTING stuKeepLighting = new NET_KEEP_LIGHTING();
    /**
     * / 保留字节
     */
    public byte[]           byReserved = new byte[128];

    public NET_LINKING_DETAIL() {
    }
}

