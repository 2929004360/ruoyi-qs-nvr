package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 所有传感器的信息
*/
public class NET_IOTBOX_DEVICE_INFO extends NetSDKLib.SdkStructure
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
     * 通道下所有传感器的信息,参见结构体定义 {@link NET_IOTBOX_CHANNEL_DEVICE_INFO}
    */
    public NET_IOTBOX_CHANNEL_DEVICE_INFO[] stuDevices = new NET_IOTBOX_CHANNEL_DEVICE_INFO[128];
    /**
     * 通道下所有传感器的信息个数
    */
    public int              nDevicesNum;
    /**
     * 保留字节
    */
    public byte[]           szResvered = new byte[68];

    public NET_IOTBOX_DEVICE_INFO() {
        for(int i = 0; i < stuDevices.length; i++){
            stuDevices[i] = new NET_IOTBOX_CHANNEL_DEVICE_INFO();
        }
    }
}

