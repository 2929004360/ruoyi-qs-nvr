package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 260611
 * @description ： 雷达设备列表配置, 对应配置项NET_EM_CFG_RADAR_DEVLIST
 * @since ： Created in 2022/02/11 10:22
 */

public class NET_CFG_RADAR_DEVLIST_INFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 雷达设备个数, 即stuRadarDevList 数组有效元素个数
     */
    public int              nRadarDevNum;
    /**
     * 雷达设备列表信息
     */
    public NET_RADAR_DEVICE_INFO[] stuRadarDevList = (NET_RADAR_DEVICE_INFO[]) new NET_RADAR_DEVICE_INFO().toArray(10);

    public NET_CFG_RADAR_DEVLIST_INFO() {
        this.dwSize = this.size();
    }
}

