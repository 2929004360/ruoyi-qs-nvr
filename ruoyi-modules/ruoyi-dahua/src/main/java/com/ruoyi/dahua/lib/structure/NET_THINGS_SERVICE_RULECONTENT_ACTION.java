package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 场景触发执行动作
 * @date 2022/04/20 11:28:14
 */
public class NET_THINGS_SERVICE_RULECONTENT_ACTION extends NetSDKLib.SdkStructure {
    /**
     * 场景触发执行动作类型,见EM_THINGS_ACTION_TYPE {@link com.ruoyi.dahua.lib.enumeration.EM_THINGS_ACTION_TYPE}
     */
    public int              emActionUri;
    /**
     * 设置设备属性，emUri为EM_THINGS_ACTION_TYPE_SETPROPERTY有效
     */
    public NET_THINGS_RULECONTENT_ACTION_SETPROPERTY stuActionSetProperty = new NET_THINGS_RULECONTENT_ACTION_SETPROPERTY();
    /**
     * 保留字节
     */
    public byte[]           szReserve = new byte[1024];
}

