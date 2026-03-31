package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 海外车辆类型中的子类别，一个车辆只能是子类型的某一种。
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 20:21
 */
public class EM_OVERSEA_VEHICLE_CATEGORY_TYPE extends NetSDKLib.SdkStructure {
    /**
     *  未知
     */
    public static final int   EM_OVERSEA_VEHICLE_CATEGORY_UNKNOWN = 0;
    /**
     * 摩托车
     */
    public static final int   EM_OVERSEA_VEHICLE_CATEGORY_MOTORCYCLE = 1;
    /**
     * 轻型货车
     */
    public static final int   EM_OVERSEA_VEHICLE_CATEGORY_LIGHT_GOODS_VEHICLE = 2;
    /**
     * 公司用私家车        
     */
    public static final int   EM_OVERSEA_VEHICLE_CATEGORY_COMPANY_VEHICLE = 3;
    /**
     * 个人用私家车
     */
    public static final int   EM_OVERSEA_VEHICLE_CATEGORY_PRIVATE_VEHICLE = 4;
    /**
     * TAXI或者快线车
     */
    public static final int   EM_OVERSEA_VEHICLE_CATEGORY_TAXI = 5;
    /**
     * 拖车
     */
    public static final int   EM_OVERSEA_VEHICLE_CATEGORY_TRAILER = 6;
    /**
     * 工程车
     */
    public static final int   EM_OVERSEA_VEHICLE_CATEGORY_ENGINEERING_PLANT_VEHICLE = 7;
    /**
     * 超大货车
     */
    public static final int   EM_OVERSEA_VEHICLE_CATEGORY_VERY_HEAVY_GOODS_VEHICLE = 8;
    /**
     * 大货车
     */
    public static final int   EM_OVERSEA_VEHICLE_CATEGORY_HEAVY_GOODS_VEHICLE = 9;
    /**
     * 公共BUS 
     */
    public static final int   EM_OVERSEA_VEHICLE_CATEGORY_PUBLIC_BUS = 10;
    /**
     * 私营BUS  
     */
    public static final int   EM_OVERSEA_VEHICLE_CATEGORY_PRIVATE_BUS = 11;
    /**
     * 特殊车辆
     */
    public static final int   EM_OVERSEA_VEHICLE_CATEGORY_SPECIAL_VEHICLE = 12;
}

