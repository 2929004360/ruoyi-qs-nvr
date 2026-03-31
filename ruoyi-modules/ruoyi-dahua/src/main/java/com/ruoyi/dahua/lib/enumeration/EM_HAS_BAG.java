package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 是否戴包(包括背包或拎包)
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 19:35
 */
public class EM_HAS_BAG extends NetSDKLib.SdkStructure {
    /**
     *  未知
     */
    public static final int   EM_HAS_BAG_UNKNOWN = 0;
    /**
     *  不带包
     */
    public static final int   EM_HAS_BAG_NO = 1;
    /**
     *  带包
     */
    public static final int   EM_HAS_BAG_YES = 2;
}

