package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 删除子设备输入参数(对应 EM_THINGS_SERVICE_TYPE_DELDEVICE)
 * @date 2022/04/20 10:50:23
 */
public class NET_IN_THINGS_SERVICE_DELDEVICE extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小, 调用者必须初始化该字段
     */
    public int              dwSize;
    /**
     * 子设备ID个数
     */
    public int              nDelidListCount;
    /**
     * 子设备ID列表
     */
    public NET_THINGS_SERVICE_DELDEVICE_LIST[] stuDelidList = (NET_THINGS_SERVICE_DELDEVICE_LIST[]) new NET_THINGS_SERVICE_DELDEVICE_LIST().toArray(64);
    /**
     * 产品ID，全网唯一
     */
    public byte[]           szProductID = new byte[64];
    /**
     * 设备ID
     */
    public byte[]           szDeviceID = new byte[64];

    public NET_IN_THINGS_SERVICE_DELDEVICE() {
        this.dwSize = this.size();
    }
}

