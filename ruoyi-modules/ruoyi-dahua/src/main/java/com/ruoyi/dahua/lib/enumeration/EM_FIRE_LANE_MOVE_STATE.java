package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 物体进入还是离开
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 19:49
 */
public class EM_FIRE_LANE_MOVE_STATE extends NetSDKLib.SdkStructure {
    /**
     *  未知
     */
    public static final int   EM_FIRE_LANE_MOVE_STATE_UNKNOWN = 0;
    /**
     *  进入
     */
    public static final int   EM_FIRE_LANE_MOVE_STATE_ENTER = 1;
    /**
     *  离开
     */
    public static final int   EM_FIRE_LANE_MOVE_STATE_LEAVE = 2;
}

