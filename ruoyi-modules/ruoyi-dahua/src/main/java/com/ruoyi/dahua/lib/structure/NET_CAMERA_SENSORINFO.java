package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 镜头传感器信息
*/
public class NET_CAMERA_SENSORINFO extends NetSDKLib.SdkStructure
{
    /**
     * 传感器类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.NET_CAMERA_SENSOR}
    */
    public int              emSensorType;
    /**
     * 此传感器类型对应的通道个数
    */
    public int              nChannelsCount;
    /**
     * 此传感器类型对应的通道号
    */
    public int[]            nChannels = new int[512];
    public byte[]           reserved = new byte[512];

    public NET_CAMERA_SENSORINFO() {
    }
}

