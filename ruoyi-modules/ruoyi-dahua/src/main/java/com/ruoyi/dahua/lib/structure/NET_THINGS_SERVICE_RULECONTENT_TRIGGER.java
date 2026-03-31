package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 场景的触发器
 * @date 2022/04/20 11:28:14
 */
public class NET_THINGS_SERVICE_RULECONTENT_TRIGGER extends NetSDKLib.SdkStructure {
    /**
     * 场景的触发类型,见EM_THINGS_TRIGGER_TYPE {@link com.ruoyi.dahua.lib.enumeration.EM_THINGS_TRIGGER_TYPE}
     */
    public int              emUri;
    /**
     * 定时触发，emUri为EM_THINGS_TRIGGER_TYPE_TRIGGER_TIMER有效
     */
    public NET_THINGS_RULECONTENT_TRIGGER_TIMER stuTriggerTimer = new NET_THINGS_RULECONTENT_TRIGGER_TIMER();
    /**
     * 设备属性触发，emUri为EM_THINGS_TRIGGER_TYPE_TRIGGER_PROPERTY有效
     */
    public NET_THINGS_RULECONTENT_TRIGGER_PROPERTY stuTriggerProperty = new NET_THINGS_RULECONTENT_TRIGGER_PROPERTY();
    /**
     * 保留字节
     */
    public byte[]           szReserve = new byte[1024];
}

