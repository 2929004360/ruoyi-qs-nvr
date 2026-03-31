package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 所有传感器的信息
*/
public class NET_IOTBOX_DEVICE_INFO_EX extends NetSDKLib.SdkStructure
{
    /**
     * 传感器通道名称
    */
    public byte[]           szChannelName = new byte[64];
    /**
     * 通道状态点名称
    */
    public byte[]           szChannelStatusName = new byte[128];
    /**
     * 通道状态点的值
    */
    public double           dbChannelStatusValue;
    /**
     * 通道控制点名称
    */
    public byte[]           szChannelControlName = new byte[128];
    /**
     * 通道控制点的值
    */
    public double           dbChannelControlValue;
    /**
     * 通道下所有传感器的信息，用户申请内存, nDevicesNum*sizeof(NET_IOTBOX_CHANNEL_DEVICE_INFO),参见结构体定义 {@link NET_IOTBOX_CHANNEL_DEVICE_INFO}
    */
    public Pointer          pstuDevices;
    /**
     * 通道下所有传感器的信息个数
    */
    public int              nDevicesNum;
    /**
     * 返回的通道下所有传感器的信息个数
    */
    public int              nRetDevicesNum;
    /**
     * 保留字节
    */
    public byte[]           szResvered = new byte[64];

    public NET_IOTBOX_DEVICE_INFO_EX() {
    }
}

