package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 添加设备输入参数(对应 EM_THINGS_SERVICE_TYPE_ADDDEVICE)
 * @date 2022/04/20 10:50:22
 */
public class NET_IN_THINGS_SERVICE_ADDDEVICE extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小, 调用者必须初始化该字段
     */
    public int              dwSize;
    /**
     * 产品ID，全网唯一
     */
    public byte[]           szProductID = new byte[64];
    /**
     * 设备ID
     */
    public byte[]           szDeviceID = new byte[64];
    /**
     * 添加设备类型,3:电表 5:塑壳 6:烟感
     */
    public int              nDevCata;
    /**
     * 485地址（塑壳、烟感填写）
     */
    public int              nDev485Addr;
    /**
     * 设备序列号（电表填写）
     */
    public byte[]           szDevSn = new byte[128];
    /**
     * 设备别名（电表填写）
     */
    public byte[]           szDevName = new byte[128];

    public NET_IN_THINGS_SERVICE_ADDDEVICE() {
        this.dwSize = this.size();
    }
}

