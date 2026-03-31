package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 接口 CLIENT_AddSoftTourPoint 的输出参数
 * @origin autoTool
 * @date 2023/10/16 19:45:45
 */
public class NET_OUT_ADD_SOFT_TOUR_POINT_INFO extends NetSDKLib.SdkStructure {
    /**
     * / 此结构体大小,必须赋值
     */
    public int              dwSize;

    public NET_OUT_ADD_SOFT_TOUR_POINT_INFO() {
        this.dwSize = this.size();
    }
}

