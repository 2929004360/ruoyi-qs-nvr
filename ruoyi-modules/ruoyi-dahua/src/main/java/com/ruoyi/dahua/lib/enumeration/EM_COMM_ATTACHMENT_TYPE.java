package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 车辆物件类型
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 20:21
 */
public class EM_COMM_ATTACHMENT_TYPE extends NetSDKLib.SdkStructure {
    /**
     *  未知类型       
     */
    public static final int   COMM_ATTACHMENT_TYPE_UNKNOWN = 0;
    /**
     *  摆件       
     */
    public static final int   COMM_ATTACHMENT_TYPE_FURNITURE = 1;
    /**
     *  挂件       
     */
    public static final int   COMM_ATTACHMENT_TYPE_PENDANT = 2;
    /**
     *  纸巾盒       
     */
    public static final int   COMM_ATTACHMENT_TYPE_TISSUEBOX = 3;
    /**
     *  危险品  
     */
    public static final int   COMM_ATTACHMENT_TYPE_DANGER = 4;
    /**
     *  香水
     */
    public static final int   COMM_ATTACHMENT_TYPE_PERFUMEBOX = 5;
}

