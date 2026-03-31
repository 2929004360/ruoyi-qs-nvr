package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 车道类型
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 20:21
 */
public class EM_TRAFFICCAR_LANE_TYPE extends NetSDKLib.SdkStructure {
    /**
     *  未知
     */
    public static final int   EM_TRAFFICCAR_LANE_TYPE_UNKNOWN = 0;
    /**
     *  普通车道
     */
    public static final int   EM_TRAFFICCAR_LANE_TYPE_NORMAL = 1;
    /**
     *  非机动车车道
     */
    public static final int   EM_TRAFFICCAR_LANE_TYPE_NONMOTOR = 2;
    /**
     *  小型车车道
     */
    public static final int   EM_TRAFFICCAR_LANE_TYPE_LIGHT_DUTY = 3;
    /**
     *  公交车车道
     */
    public static final int   EM_TRAFFICCAR_LANE_TYPE_BUS = 4;
    /**
     *  应急车道
     */
    public static final int   EM_TRAFFICCAR_LANE_TYPE_EMERGENCY = 5;
    /**
     *  危险品车道
     */
    public static final int   EM_TRAFFICCAR_LANE_TYPE_DANGEROUS = 6;
    /**
     *  潮汐车道
     */
    public static final int   EM_TRAFFICCAR_LANE_TYPE_TIDAL = 7;
}

