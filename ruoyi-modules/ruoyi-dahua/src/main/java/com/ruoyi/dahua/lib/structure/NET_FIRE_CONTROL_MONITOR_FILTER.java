package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421557
 * @description 烟火检测查询过滤条件
 * @origin autoTool
 * @date 2023/10/23 10:56:03
 */
public class NET_FIRE_CONTROL_MONITOR_FILTER extends NetSDKLib.SdkStructure {
    /**
     * / 智能分析规则，当szRule不为""时，MEDIAFILE_FIRE_CONTROL_MONITOR_PARAM 中 nEventList 无效
     * / 通配符"*"代表任意规则、"SleepDetect"、"PlayMobilephone"、"PhoneCallDetect"
     */
    public byte[]           szRule = new byte[64];
    /**
     * null
     */
    public byte[]           byReserved = new byte[1024];

    public NET_FIRE_CONTROL_MONITOR_FILTER() {
    }
}

