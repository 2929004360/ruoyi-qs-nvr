package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description CLIENT_SetThingsConfig 接口输入参数
 * @date 2022/04/20 10:16:57
 */
public class NET_IN_THINGS_SET extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小, 调用者必须初始化该字段
     */
    public int              dwSize;
    /**
     * 产品ID，全网唯一
     */
    public byte[]           szProductID = new byte[64];
    /**
     * 设备ID，产品下唯一
     */
    public byte[]           szDeviceID = new byte[128];
    /**
     * pstuSetInProperInfo个数
     */
    public int              nProperCount;
    /**
     * 属性值，具体内容由物模型协议决定, Key，Value，ValueType均需要填写
     * 用户分配内存,大小为sizeof(NET_PROPERTIES_INFO)*nPropertiesCount
     */
    public Pointer          pstuSetInProperInfo;

    public NET_IN_THINGS_SET() {
        this.dwSize = this.size();
    }
}

