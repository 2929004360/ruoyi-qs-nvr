package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 媒体信息――镜头传感器
*/
public class NET_MEDIA_SENSORINFO extends NetSDKLib.SdkStructure
{
    /**
     * 是否支持本能力获取,为TRUE时以下成员有效
    */
    public int              bSupport;
    /**
     * 设备具有的镜头传感器类型个数
    */
    public int              nSensorTypeCount;
    /**
     * 每个传感器类型的详细信息,有效个数由 nSensorTypeCount 指明,参见结构体定义 {@link NET_CAMERA_SENSORINFO}
    */
    public NET_CAMERA_SENSORINFO[] stuDetail = new NET_CAMERA_SENSORINFO[16];
    public byte[]           reserved = new byte[1024];

    public NET_MEDIA_SENSORINFO() {
        for(int i = 0; i < stuDetail.length; i++){
            stuDetail[i] = new NET_CAMERA_SENSORINFO();
        }
    }
}

