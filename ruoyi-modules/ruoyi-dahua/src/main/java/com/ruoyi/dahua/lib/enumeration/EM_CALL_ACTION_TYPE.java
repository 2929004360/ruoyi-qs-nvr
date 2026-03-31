package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 打电话的动作类型
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 20:21
 */
public class EM_CALL_ACTION_TYPE extends NetSDKLib.SdkStructure {
    /**
     *  未知
     */
    public static final int   EM_CALL_ACTION_TYPE_UNKNOWN = 0;
    /**
     *  贴耳
     */
    public static final int   EM_CALL_ACTION_TYPE_CALL_BY_EAR = 1;
    /**
     *  手持 
     */
    public static final int   EM_CALL_ACTION_TYPE_CALL_HAND_HOLD = 2;
}

