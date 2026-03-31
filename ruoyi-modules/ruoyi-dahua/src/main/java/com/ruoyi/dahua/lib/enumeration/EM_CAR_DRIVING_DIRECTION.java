package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 规则区内车辆行驶方向
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 20:21
 */
public class EM_CAR_DRIVING_DIRECTION extends NetSDKLib.SdkStructure {
    /**
     *  未知
     */
    public static final int   EM_CAR_DRIVING_DIRECTION_UNKNOWN = 0;
    /**
     *  驶入区域
     */
    public static final int   EM_CAR_DRIVING_DIRECTION_DRIVE_IN_AREA = 1;
    /**
     *  驶出区域
     */
    public static final int   EM_CAR_DRIVING_DIRECTION_EXIT_AREA = 2;
}

