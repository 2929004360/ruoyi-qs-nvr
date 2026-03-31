package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 嘴巴状态
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 20:21
 */
public class EM_MOUTH_STATE_TYPE extends NetSDKLib.SdkStructure {
    /**
     *  未知
     */
    public static final int   EM_MOUTH_STATE_UNKNOWN = 0;
    /**
     *  未识别
     */
    public static final int   EM_MOUTH_STATE_NODISTI = 1;
    /**
     *  闭嘴
     */
    public static final int   EM_MOUTH_STATE_CLOSE = 2;
    /**
     *  张嘴
     */
    public static final int   EM_MOUTH_STATE_OPEN = 3;
}

