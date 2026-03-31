package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 260611
* @description  设置设备属性类型内容(对应EM_THINGS_ACTION_TYPE_SETPROPERTY) 
* @date 2022/04/20 11:28:14
*/
public class NET_THINGS_RULECONTENT_ACTION_SETPROPERTY extends NetSDKLib.SdkStructure {
/** 
设备类型
*/
    public			byte[]         szProductId = new byte[64];
/** 
设备名称
*/
    public			byte[]         szDeviceName = new byte[32];
/** 
属性名称
*/
    public			byte[]         szPropertyName = new byte[32];
/** 
属性值
*/
    public			byte[]         szPropertyValue = new byte[32];
/** 
属性值类型 {@link com.ruoyi.dahua.lib.enumeration.EM_PROPERTIES_VALUE_TYPE}
*/
    public			int            emPropertyValueType;
/** 
保留字节
*/
    public			byte[]         szReserve = new byte[256];
}

