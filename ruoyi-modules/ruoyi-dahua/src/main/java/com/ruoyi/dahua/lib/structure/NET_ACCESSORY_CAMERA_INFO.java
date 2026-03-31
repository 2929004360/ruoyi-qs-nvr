package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 相机模组信息
*/
public class NET_ACCESSORY_CAMERA_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 相机状态, 0:未知, 1:不在位, 2:在位
    */
    public int              nStatus;
    /**
     * 相机版本信息
    */
    public byte[]           szVersion = new byte[64];
    /**
     * 相机序列号
    */
    public byte[]           szSN = new byte[32];
    /**
     * 预留字段
    */
    public byte[]           szReserved = new byte[124];

    public NET_ACCESSORY_CAMERA_INFO() {
    }
}

