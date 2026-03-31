package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description Mini雷达探测参数配置, 对应配置项 NET_EM_CFG_MINIRADAR_DECT_PARAM
 * @date 2022/08/04 10:13:29
 */
public class NET_CFG_MINIRADAR_DECT_PARAM_INFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 灵敏度
     */
    public int              nSensitivity;
    /**
     * 上报间隔，单位秒
     */
    public int              nReportInterval;

    public NET_CFG_MINIRADAR_DECT_PARAM_INFO() {
        this.dwSize = this.size();
    }
}

