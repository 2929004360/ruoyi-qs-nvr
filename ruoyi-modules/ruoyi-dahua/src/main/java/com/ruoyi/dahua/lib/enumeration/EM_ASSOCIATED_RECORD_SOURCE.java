package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 是否与其他目标关联
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 19:35
 */
public class EM_ASSOCIATED_RECORD_SOURCE extends NetSDKLib.SdkStructure {
    /**
     *  未知
     */
    public static final int   EM_ASSOCIATED_RECORD_SOURCE_UNKNOWN = -1;
    /**
     *  未关联
     */
    public static final int   EM_ASSOCIATED_RECORD_SOURCE_NONE = 0;
    /**
     *  与人体有关联成功
     */
    public static final int   EM_ASSOCIATED_RECORD_SOURCE_HUMAN = 1;
    /**
     *  与机动车过车有关联
     */
    public static final int   EM_ASSOCIATED_RECORD_SOURCE_MOTOR_VEHICLE_PASSING = 2;
    /**
     *  与机动车违章有关联
     */
    public static final int   EM_ASSOCIATED_RECORD_SOURCE_MOTOR_VEHICLE_VIOLATION = 3;
    /**
     *  与结构化机动车有关联
     */
    public static final int   EM_ASSOCIATED_RECORD_SOURCE_STRUCTURED_MOTOR_VEHICLE = 4;
    /**
     *  与人脸有关联
     */
    public static final int   EM_ASSOCIATED_RECORD_SOURCE_FACE = 5;
    /**
     *  与非机动车有关联
     */
    public static final int   EM_ASSOCIATED_RECORD_SOURCE_NON_MOTOR_VEHICLE = 6;
}

