package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 场景触发过滤条件
 * @date 2022/04/20 11:28:15
 */
public class NET_THINGS_SERVICE_RULECONTENT_CONDITION extends NetSDKLib.SdkStructure {
    /**
     * 场景触发过滤条件类型,见EM_THINGS_CONDITION_TYPE {@link com.ruoyi.dahua.lib.enumeration.EM_THINGS_CONDITION_TYPE}
     */
    public int              emConditionUri;
    /**
     * 比较当前时间是否在一个区间内，emConditionUri为EM_THINGS_CONDITION_TYPE_TIMERANGE有效
     */
    public NET_THINGS_RULECONTENT_TRIGGER_TIMER stuTriggerTimer = new NET_THINGS_RULECONTENT_TRIGGER_TIMER();
    /**
     * 设置设备属性，emConditionUri为EM_THINGS_CONDITION_TYPE_PROPERTY有效
     */
    public NET_THINGS_RULECONTENT_CONDITION_PROPERTY stuConditionProperty = new NET_THINGS_RULECONTENT_CONDITION_PROPERTY();
    /**
     * 保留字节
     */
    public byte[]           szReserve = new byte[1024];
}

