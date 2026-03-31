package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 空开红蓝灯闪烁输出参数(对应 EM_THINGS_SERVICE_TYPE_AIRSWITHFIND)
 * @date 2022/04/20 10:50:20
 */
public class NET_OUT_THINGS_SERVICE_AIRSWITHFIND extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小, 调用者必须初始化该字段
     */
    public int              dwSize;
    /**
     * 物模型协议错误码定义
     */
    public int              nErrcode;
    /**
     * 产品ID
     */
    public byte[]           szProductID = new byte[64];
    /**
     * 设备ID
     */
    public byte[]           szDeviceID = new byte[64];

    public NET_OUT_THINGS_SERVICE_AIRSWITHFIND() {
        this.dwSize = this.size();
    }
}

