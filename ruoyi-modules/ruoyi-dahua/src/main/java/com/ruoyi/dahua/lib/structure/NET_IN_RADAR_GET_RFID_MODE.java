package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 260611
 * @description ： CLIENT_GetRadarRFIDMode接口入参
 * @since ： Created in 2022/02/11 10:22
 */

public class NET_IN_RADAR_GET_RFID_MODE extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;

    public NET_IN_RADAR_GET_RFID_MODE() {
        this.dwSize = this.size();
    }
}

