package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 录像类型
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 20:21
 */
public class EM_RECORD_TYPE extends NetSDKLib.SdkStructure {
    /**
     *  未知
     */
    public static final int   EM_RECORD_TYPE_UNKNOWN = 0;
    /**
     *  普通录像
     */
    public static final int   EM_RECORD_TYPE_COMMON = 1;
    /**
     *  报警录像
     */
    public static final int   EM_RECORD_TYPE_ALARM2 = 2;
    /**
     *  动检录像
     */
    public static final int   EM_RECORD_TYPE_MOTIONDETECT = 3;
}

