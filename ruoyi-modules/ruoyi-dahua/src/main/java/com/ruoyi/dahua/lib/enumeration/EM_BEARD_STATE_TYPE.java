package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 胡子状态
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 20:21
 */
public class EM_BEARD_STATE_TYPE extends NetSDKLib.SdkStructure {
    /**
     *  未知
     */
    public static final int   EM_BEARD_STATE_UNKNOWN = 0;
    /**
     *  未识别
     */
    public static final int   EM_BEARD_STATE_NODISTI = 1;
    /**
     *  没胡子
     */
    public static final int   EM_BEARD_STATE_NOBEARD = 2;
    /**
     *  有胡子
     */
    public static final int   EM_BEARD_STATE_HAVEBEARD = 3;
}

