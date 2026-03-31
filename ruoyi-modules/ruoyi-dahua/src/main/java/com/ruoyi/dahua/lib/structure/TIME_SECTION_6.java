package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * @author 251823
 * @description 6个NET_TSECT时间段结构体
 * @date 2022/10/14 13:53:01
 */
public class TIME_SECTION_6 extends NetSDKLib.SdkStructure {
    public NetSDKLib.NET_TSECT[] timeSection = new NetSDKLib.NET_TSECT[NetSDKLib.NET_N_REC_TSECT];
}

