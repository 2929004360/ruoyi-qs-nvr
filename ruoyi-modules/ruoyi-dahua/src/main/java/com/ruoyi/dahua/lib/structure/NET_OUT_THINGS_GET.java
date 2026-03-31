package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description CLIENT_GetThingsConfig 接口输出参数
 * @date 2022/04/20 10:16:57
 */
public class NET_OUT_THINGS_GET extends NetSDKLib.SdkStructure {
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
     * 实际返回的pstuGetOutProperInfo个数
     */
    public int              nRetPropertiesCount;
    /**
     * 用户分配的pstuGetOutProperInfo个数
     */
    public int              nMaxPropertiesCount;
    /**
     * 属性值，具体内容由物模型协议决定, Key，Value，ValueType均有效
     * 用户分配内存,大小为sizeof{@link NET_PROPERTIES_INFO}*nMaxPropertiesCount
     */
    public Pointer          pstuGetOutProperInfo;

    public NET_OUT_THINGS_GET() {
        this.dwSize = this.size();
    }
}

