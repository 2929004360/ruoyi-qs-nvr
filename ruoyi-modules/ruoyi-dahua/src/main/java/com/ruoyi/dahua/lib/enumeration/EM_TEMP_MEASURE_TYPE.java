package com.ruoyi.dahua.lib.enumeration;



/**
 * 门禁测温类型
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/09 17:05
 */
public enum EM_TEMP_MEASURE_TYPE {
    /**
     *  未知
     */
    EM_TEMP_MEASURE_TYPE_UNKNOWN,
    /**
     *  单片机红外测温
     */
    EM_TEMP_MEASURE_TYPE_INFRARED,
    /**
     *  热成像测温
     */
    EM_TEMP_MEASURE_TYPE_THERMAL_IMAGE,
    /**
     *  高德测温模块测温
     */
    EM_TEMP_MEASURE_TYPE_GUIDE_MODULE,
    /**
     *  单点手腕测温
     */
    EM_TEMP_MEASURE_TYPE_WRIST;
}

