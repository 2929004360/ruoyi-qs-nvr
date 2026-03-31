package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 默认检测最大座驾个数
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 20:21
 */
public class EM_COMMON_SEAT_TYPE extends NetSDKLib.SdkStructure {
    /**
     *  未识别
     */
    public static final int   COMMON_SEAT_TYPE_UNKNOWN = 0;
    /**
     *  主驾驶
     */
    public static final int   COMMON_SEAT_TYPE_MAIN = 1;
    /**
     *  副驾驶
     */
    public static final int   COMMON_SEAT_TYPE_SLAVE = 2;
}

