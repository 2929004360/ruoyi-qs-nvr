package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description Mini雷达探测区域配置, 对应配置项 NET_EM_CFG_MINIRADAR_REGION_DETECT
 * @date 2022/08/04 10:13:30
 */
public class NET_CFG_MINIRADAR_REGION_DETECT_INFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 前方探测距离，单位米，扩大100倍
     */
    public int              nFrontDecDis;
    /**
     * 前方探测距离，单位米，扩大100倍
     */
    public int              nBehindDecDis;
    /**
     * 左侧探测距离，单位米，扩大100倍
     */
    public int              nLeftDecDis;
    /**
     * 右侧探测距离，单位米，扩大100倍
     */
    public int              nRightDecDis;

    public NET_CFG_MINIRADAR_REGION_DETECT_INFO() {
        this.dwSize = this.size();
    }
}

