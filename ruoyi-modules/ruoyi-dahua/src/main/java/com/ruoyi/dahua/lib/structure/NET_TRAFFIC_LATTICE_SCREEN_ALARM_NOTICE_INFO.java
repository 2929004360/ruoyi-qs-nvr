package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description 点阵屏报警提示显示信息
 * @date 2021/8/9 16:38
 */
public class NET_TRAFFIC_LATTICE_SCREEN_ALARM_NOTICE_INFO extends NetSDKLib.SdkStructure {
    public byte[]           szNoHelmet = new byte[128];           // 未带头盔屏幕提示信息
    public byte[]           szNonMotorOverload = new byte[128];   // 非机动车超载提示信息
}

