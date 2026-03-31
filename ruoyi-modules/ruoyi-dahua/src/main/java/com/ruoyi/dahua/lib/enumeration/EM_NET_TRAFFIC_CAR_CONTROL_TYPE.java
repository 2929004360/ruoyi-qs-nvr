package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 布控类型
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 20:21
 */
public class EM_NET_TRAFFIC_CAR_CONTROL_TYPE extends NetSDKLib.SdkStructure {
    /**
     *  其他
     */
    public static final int   NET_CAR_CONTROL_OTHER = 0;
    /**
     *  过期未检	"OverdueNoCheck"
     */
    public static final int   NET_CAR_CONTROL_OVERDUE_NO_CHECK = 1;
    /**
     *  	"BrigandageCar"
     */
    public static final int   NET_CAR_CONTROL_BRIGANDAGE_CAR = 2;
    /**
     *  违章		"Breaking"
     */
    public static final int   NET_CAR_CONTROL_BREAKING = 3;
    /**
     *  "CausetroubleEscape"
     */
    public static final int   NET_CAR_CONTROL_CAUSETROUBLE_ESCAPE = 4;
    /**
     *  停车欠费  "OverdueParking"
     */
    public static final int   NET_CAR_CONTROL_CAUSETROUBLE_OVERDUEPARKING = 5;
    /**
     *  假牌车     "CounterfeitPlateCar"
     */
    public static final int   NET_CAR_CONTROL_COUNTERFEI_PLATE_CAR = 6;
    /**
     *  套牌车     "FakePlateCar"
     */
    public static final int   NET_CAR_CONTROL_FAKE_PLATE_CAR = 7;
    /**
     *  重点车辆   "FocalCar"
     */
    public static final int   NET_CAR_CONTROL_FOCAL_CAR = 8;
    /**
     *  保障车辆   "GuaranteeCar"
     */
    public static final int   NET_CAR_CONTROL_GUARANTEE_CAR = 9;
    /**
     *  关注车辆   "FollowCar"
     */
    public static final int   NET_CAR_CONTROL_FOLLOW_CAR = 10;
}

