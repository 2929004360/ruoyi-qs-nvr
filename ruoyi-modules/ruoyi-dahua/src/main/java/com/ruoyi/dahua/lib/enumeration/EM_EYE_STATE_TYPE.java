package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 眼睛状态
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 20:21
 */
public class EM_EYE_STATE_TYPE extends NetSDKLib.SdkStructure {
    /**
     *  未知
     */
    public static final int   EM_EYE_STATE_UNKNOWN = 0;
    /**
     *  未识别
     */
    public static final int   EM_EYE_STATE_NODISTI = 1;
    /**
     *  闭眼
     */
    public static final int   EM_EYE_STATE_CLOSE = 2;
    /**
     *  睁眼
     */
    public static final int   EM_EYE_STATE_OPEN = 3;
}

