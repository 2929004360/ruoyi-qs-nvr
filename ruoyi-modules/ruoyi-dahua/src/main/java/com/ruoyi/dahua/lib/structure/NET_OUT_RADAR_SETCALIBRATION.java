package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 雷球校准配置出参(对应 EM_RADAR_OPERATE_TYPE_SETCALIBRATION)
 * @date 2022/08/04 10:13:33
 */
public class NET_OUT_RADAR_SETCALIBRATION extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;

    public NET_OUT_RADAR_SETCALIBRATION() {
        this.dwSize = this.size();
    }
}

