package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 交通车辆行驶方向类型
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 20:21
 */
public class EM_TRAFFICCAR_MOVE_DIRECTION extends NetSDKLib.SdkStructure {
    /**
     *  未知的
     */
    public static final int   EM_TRAFFICCAR_MOVE_DIRECTION_UNKNOWN = 0;
    /**
     *  直行
     */
    public static final int   EM_TRAFFICCAR_MOVE_DIRECTION_STRAIGHT = 1;
    /**
     *  左转
     */
    public static final int   EM_TRAFFICCAR_MOVE_DIRECTION_TURN_LEFT = 2;
    /**
     *  右转
     */
    public static final int   EM_TRAFFICCAR_MOVE_DIRECTION_TURN_RIGHT = 3;
    /**
     *  掉头
     */
    public static final int   EM_TRAFFICCAR_MOVE_DIRECTION_TURN_AROUND = 4;
}

