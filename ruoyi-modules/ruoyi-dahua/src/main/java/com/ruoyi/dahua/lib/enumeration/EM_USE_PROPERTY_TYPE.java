package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 使用性质
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 20:21
 */
public class EM_USE_PROPERTY_TYPE extends NetSDKLib.SdkStructure {
    /**
     *  非营运
     */
    public static final int   EM_USE_PROPERTY_NONOPERATING = 0;
    /**
     *  公路客运,旅游客运
     */
    public static final int   EM_USE_PROPERTY_HIGWAY = 1;
    /**
     *  公交客运
     */
    public static final int   EM_USE_PROPERTY_BUS = 2;
    /**
     *  出租客运
     */
    public static final int   EM_USE_PROPERTY_TAXI = 3;
    /**
     *  货运
     */
    public static final int   EM_USE_PROPERTY_FREIGHT = 4;
    /**
     *  租赁
     */
    public static final int   EM_USE_PROPERTY_LEASE = 5;
    /**
     *  警用,消防,救护,工程救险
     */
    public static final int   EM_USE_PROPERTY_SECURITY = 6;
    /**
     *  教练
     */
    public static final int   EM_USE_PROPERTY_COACH = 7;
    /**
     *  幼儿校车,小学生校车,其他校车
     */
    public static final int   EM_USE_PROPERTY_SCHOOLBUS = 8;
    /**
     *  危化品运输
     */
    public static final int   EM_USE_PROPERTY_FOR_DANGE_VEHICLE = 9;
    /**
     *  其他
     */
    public static final int   EM_USE_PROPERTY_OTHER = 10;
    /**
     *  网约车
     */
    public static final int   EM_USE_PROPERTY_ONLINE_CAR_HAILING = 11;
    /**
     *  非机动外卖车
     */
    public static final int   EM_USE_PROPERTY_NON_MOTORIZED_TAKE_OUT_VEHICLE = 12;
    /**
     *  非机动快递车
     */
    public static final int   EM_USE_PROPERTY_NON_MOTORIZED_EXPRESS_CAR = 13;
}

