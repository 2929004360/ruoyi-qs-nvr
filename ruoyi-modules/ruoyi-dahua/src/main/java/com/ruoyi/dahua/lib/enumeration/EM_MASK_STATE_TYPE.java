package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 口罩状态
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 20:21
 */
public class EM_MASK_STATE_TYPE extends NetSDKLib.SdkStructure {
    /**
     *  未知
     */
    public static final int   EM_MASK_STATE_UNKNOWN = 0;
    /**
     *  未识别
     */
    public static final int   EM_MASK_STATE_NODISTI = 1;
    /**
     *  没戴口罩
     */
    public static final int   EM_MASK_STATE_NOMASK = 2;
    /**
     *  戴口罩
     */
    public static final int   EM_MASK_STATE_WEAR = 3;
}

