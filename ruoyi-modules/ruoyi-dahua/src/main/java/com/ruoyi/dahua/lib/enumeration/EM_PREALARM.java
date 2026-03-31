package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 是否为违规预警图片
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 20:00
 */
public class EM_PREALARM extends NetSDKLib.SdkStructure {
    /**
     * 未知
     */
    public static final int   EM_PREALARM_UNKNOWN = -1;
    /**
     * 机动车违章事件
     */
    public static final int   EM_PREALARM_VIOLATION_EVENT = 0;
    /**
     * 机动车违章预警事件
     */
    public static final int   EM_PREALARM_VIOLATION_WARNING_EVENT = 1;
}

