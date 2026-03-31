package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_GetLaserDistance 接口出参
 * @date 2023/04/21 09:16:51
 */
public class NET_OUT_GET_LASER_DISTANCE extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 目标距离 单位:米
     */
    public int              unDistance;

    public NET_OUT_GET_LASER_DISTANCE() {
        this.dwSize = this.size();
    }
}

