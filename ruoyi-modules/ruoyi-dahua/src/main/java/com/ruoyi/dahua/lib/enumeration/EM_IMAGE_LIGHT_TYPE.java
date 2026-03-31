package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 图像成像光源类型
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 19:35
 */
public class EM_IMAGE_LIGHT_TYPE extends NetSDKLib.SdkStructure {
    /**
     *  未知
     */
    public static final int   EM_IMAGE_LIGHT_TYPE_UNKNOWN = 0;
    /**
     *  可见光成像
     */
    public static final int   EM_IMAGE_LIGHT_TYPE_VISIBLE = 1;
    /**
     *  近红外成像(灰度图)
     */
    public static final int   EM_IMAGE_LIGHT_TYPE_NEAR_INFRARED = 2;
    /**
     *  热红外成像(伪彩色)
     */
    public static final int   EM_IMAGE_LIGHT_TYPE_THERMAL_INFRARED = 3;
}

