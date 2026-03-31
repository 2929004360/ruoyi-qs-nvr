package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * NTP校时状态
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 20:21
 */
public class EM_NTP_STATUS extends NetSDKLib.SdkStructure {
    /**
     *    NET_NTP_STATUS_UNKNOWN = 0 ,
     */
    public static final int   NET_NTP_STATUS_UNKNOWN = 0;
    /**
     *    NET_NTP_STATUS_DISABLE , 
     */
    public static final int   NET_NTP_STATUS_DISABLE = 1;
    /**
     *    NET_NTP_STATUS_SUCCESSFUL , 
     */
    public static final int   NET_NTP_STATUS_SUCCESSFUL = 2;
    /**
     *    NET_NTP_STATUS_FAILED , 
     */
    public static final int   NET_NTP_STATUS_FAILED = 3;
}

