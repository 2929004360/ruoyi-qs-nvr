package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 47081
 * @version 1.0
 * @description 时间拓展结构体
 * @date 2020/9/10
 */
public class NET_TIME_EX1 extends NetSDKLib.SdkStructure {
    /**
     * 时
     */
    public int              dwHour;
    /**
     * 分
     */
    public int              dwMinute;
    /**
     * 秒
     */
    public int              dwSecond;

    public String toTime() {
        return dwHour + ":" + dwMinute + ":" + dwSecond;
    }
}

