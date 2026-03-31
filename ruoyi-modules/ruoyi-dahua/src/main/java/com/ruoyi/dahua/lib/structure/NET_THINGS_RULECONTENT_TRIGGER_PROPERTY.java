package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 设备属性触发类型内容(对应EM_THINGS_TRIGGER_TYPE_TRIGGER_PROPERTY)
 * @date 2022/04/20 11:28:14
 */
public class NET_THINGS_RULECONTENT_TRIGGER_PROPERTY extends NetSDKLib.SdkStructure {
    /**
     * 设备类型
     */
    public byte[]           szProductId = new byte[64];
    /**
     * 设备名称
     */
    public byte[]           szDeviceName = new byte[32];
    /**
     * 属性名称
     */
    public byte[]           szPropertyName = new byte[32];
    /**
     * 对比格式：< ==等
     */
    public byte[]           szCompareType = new byte[8];
    /**
     * 对比值
     */
    public byte[]           szCompareValue = new byte[32];
    /**
     * 对比值类型 {@link com.ruoyi.dahua.lib.enumeration.EM_PROPERTIES_VALUE_TYPE}
     */
    public int              emCompareValueType;
    /**
     * 保留字节
     */
    public byte[]           szReserve = new byte[256];
}

