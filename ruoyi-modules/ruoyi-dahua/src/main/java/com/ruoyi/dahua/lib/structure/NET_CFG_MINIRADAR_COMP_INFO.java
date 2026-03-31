package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description Mini雷达角度补偿配置, 对应配置项 NET_EM_CFG_MINIRADAR_COMP_INFO
 * @date 2022/08/04 10:13:30
 */
public class NET_CFG_MINIRADAR_COMP_INFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 横滚角，单位度，扩大100倍
     */
    public int              nRollAngle;
    /**
     * 俯仰角，单位度，扩大100倍
     */
    public int              nPitchAngle;
    /**
     * 横滚角，单位度，扩大100倍
     */
    public int              nYawAngle;

    public NET_CFG_MINIRADAR_COMP_INFO() {
        this.dwSize = this.size();
    }
}

