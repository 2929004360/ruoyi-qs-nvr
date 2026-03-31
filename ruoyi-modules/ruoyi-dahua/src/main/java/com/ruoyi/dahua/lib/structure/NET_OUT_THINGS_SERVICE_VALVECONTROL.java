package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 闸门控制输出参数(对应 EM_THINGS_SERVICE_TYPE_VALVECONTROL)
 * @date 2022/04/20 10:50:18
 */
public class NET_OUT_THINGS_SERVICE_VALVECONTROL extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小, 调用者必须初始化该字段
     */
    public int              dwSize;
    /**
     * 设置结果，实际闸门控制设置成功/失败已该值为准
     */
    public int              bResult;
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

    public NET_OUT_THINGS_SERVICE_VALVECONTROL() {
        this.dwSize = this.size();
    }
}

