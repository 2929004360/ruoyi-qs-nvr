package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_GetThingsCaps接口输入参数
 * @date 2022/04/20 10:16:56
 */
public class NET_IN_THINGS_GET_CAPS extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小, 调用者必须初始化该字段
     */
    public int              dwSize;
    /**
     * 设备ID，产品下唯一
     */
    public byte[]           szDeviceID = new byte[128];

    public NET_IN_THINGS_GET_CAPS() {
        this.dwSize = this.size();
    }
}

