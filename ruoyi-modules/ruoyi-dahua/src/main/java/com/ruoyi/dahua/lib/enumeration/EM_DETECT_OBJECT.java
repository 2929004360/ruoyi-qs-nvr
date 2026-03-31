package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 目标类型
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 19:34
 */
public class EM_DETECT_OBJECT extends NetSDKLib.SdkStructure {
    /**
     *  未知
     */
    public static final int   EM_DETECT_OBJECT_TYPE_UNKNOWN = 0;
    /**
     *  人脸
     */
    public static final int   EM_DETECT_OBJECT_TYPE_FACE = 1;
    /**
     *  车辆
     */
    public static final int   EM_DETECT_OBJECT_TYPE_VEHICLE = 2;
    /**
     *  结构化
     */
    public static final int   EM_DETECT_OBJECT_TYPE_STRUCTLIZE = 3;
}

