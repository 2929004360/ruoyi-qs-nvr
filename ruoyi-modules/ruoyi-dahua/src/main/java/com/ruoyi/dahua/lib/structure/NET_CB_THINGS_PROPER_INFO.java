package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 物模型属性信息
 * @date 2022/04/20 11:36:49
 */
public class NET_CB_THINGS_PROPER_INFO extends NetSDKLib.SdkStructure {
    /**
     * DQZT、FHZYY、Action等
     */
    public byte[]           szKey = new byte[64];
    /**
     * 1,true,"normal"等
     */
    public byte[]           szValue = new byte[256];
    /**
     * szValue对应的类型；有int,bool,string类型 {@link com.ruoyi.dahua.lib.enumeration.EM_PROPERTIES_VALUE_TYPE}
     */
    public int              emValueType;
    /**
     * stuIntArrayValue实际个数
     */
    public int              nIntArrayValueNum;
    /**
     * int+Array值，当emValueType为EM_PROPERTIES_VALUE_INTARRAY
     */
    public NET_PROPERTIES_INTARRAY_VALUE[] stuIntArrayValue = (NET_PROPERTIES_INTARRAY_VALUE[]) new NET_PROPERTIES_INTARRAY_VALUE().toArray(64);
    /**
     * 预留字节
     */
    public byte[]           szReserved = new byte[1024];
}

