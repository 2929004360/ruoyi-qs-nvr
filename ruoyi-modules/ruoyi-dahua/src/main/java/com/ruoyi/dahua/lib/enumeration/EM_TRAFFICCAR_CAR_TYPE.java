package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 车辆类型
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 20:21
 */
public class EM_TRAFFICCAR_CAR_TYPE extends NetSDKLib.SdkStructure {
    /**
     *  未知
     */
    public static final int   EM_TRAFFICCAR_CAR_TYPE_UNKNOWN = 0;
    /**
     *  允许名单车辆
     */
    public static final int   EM_TRAFFICCAR_CAR_TYPE_TRUST_CAR = 1;
    /**
     *  禁止名单车辆
     */
    public static final int   EM_TRAFFICCAR_CAR_TYPE_SUSPICIOUS_CAR = 2;
    /**
     *  非允许名单且非禁止名单车辆
     */
    public static final int   EM_TRAFFICCAR_CAR_TYPE_NORMAL_CAR = 3;
}

