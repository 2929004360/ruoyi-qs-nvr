package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 通道下所有传感器的信息
*/
public class NET_IOTBOX_CHANNEL_DEVICE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 传感器名称
    */
    public byte[]           szDeviceName = new byte[64];
    /**
     * 设备状态点名称
    */
    public byte[]           szDeviceStatusName = new byte[128];
    /**
     * 设备状态点的值
    */
    public double           dbDeviceStatusValue;
    /**
     * 设备控制点名称
    */
    public byte[]           szDeviceControlName = new byte[128];
    /**
     * 设备控制点的值
    */
    public double           dbDeviceControlValue;
    /**
     * 字节对齐
    */
    public byte[]           szResvered1 = new byte[4];
    /**
     * 传感器所拥有的检测属性个数
    */
    public int              nTagsNum;
    /**
     * 传感器所拥有的检测属性,参见结构体定义 {@link NET_IOTBOX_TAGS_INFO}
    */
    public NET_IOTBOX_TAGS_INFO[] stuTags = new NET_IOTBOX_TAGS_INFO[32];
    /**
     * 保留字节
    */
    public byte[]           szResvered = new byte[64];

    public NET_IOTBOX_CHANNEL_DEVICE_INFO() {
        for(int i = 0; i < stuTags.length; i++){
            stuTags[i] = new NET_IOTBOX_TAGS_INFO();
        }
    }
}

