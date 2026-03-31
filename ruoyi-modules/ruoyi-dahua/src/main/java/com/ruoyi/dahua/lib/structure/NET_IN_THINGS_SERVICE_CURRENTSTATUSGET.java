package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 获取所有空开当前报警状态输入参数(对应 EM_THINGS_SERVICE_TYPE_CURRENTSTATUSGET)
 * @date 2022/04/20 10:50:24
 */
public class NET_IN_THINGS_SERVICE_CURRENTSTATUSGET extends NetSDKLib.SdkStructure {
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

    public NET_IN_THINGS_SERVICE_CURRENTSTATUSGET() {
        this.dwSize = this.size();
    }
}

