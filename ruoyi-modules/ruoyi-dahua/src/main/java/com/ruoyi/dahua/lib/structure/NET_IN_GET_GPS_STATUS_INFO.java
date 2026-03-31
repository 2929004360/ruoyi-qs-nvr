package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description CLIENT_GetGpsStatus 接口入参
 * @origin autoTool
 * @date 2023/09/27 10:21:42
 */
public class NET_IN_GET_GPS_STATUS_INFO extends NetSDKLib.SdkStructure {
    /**
     * / 此结构体大小,必须赋值
     */
    public int              dwSize;

    public NET_IN_GET_GPS_STATUS_INFO() {
        this.dwSize = this.size();
    }
}

