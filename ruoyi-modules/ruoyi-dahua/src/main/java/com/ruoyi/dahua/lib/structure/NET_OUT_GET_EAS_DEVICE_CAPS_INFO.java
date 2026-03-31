package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetEASDeviceCaps 接口出参
*/
public class NET_OUT_GET_EAS_DEVICE_CAPS_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * EAS设备能力集数据数组有效数量
    */
    public int              nEASDeviceCapsInfoNum;
    /**
     * EAS设备能力集数据,参见结构体定义 NET_EAS_DEVICE_CAPS_INFO
    */
    public NET_EAS_DEVICE_CAPS_INFO[] stuEASDeviceCapsInfo = new NET_EAS_DEVICE_CAPS_INFO[64];
    /**
     * EAS设备支持的报警信息,参见结构体定义 NET_EAS_DEVICE_ALARM_INFO_CAPS
    */
    public NET_EAS_DEVICE_ALARM_INFO_CAPS[] stuAlarmInfoCaps = new NET_EAS_DEVICE_ALARM_INFO_CAPS[64];

    public NET_OUT_GET_EAS_DEVICE_CAPS_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuEASDeviceCapsInfo.length; i++){
            stuEASDeviceCapsInfo[i] = new NET_EAS_DEVICE_CAPS_INFO();
        }
        for(int i = 0; i < stuAlarmInfoCaps.length; i++){
            stuAlarmInfoCaps[i] = new NET_EAS_DEVICE_ALARM_INFO_CAPS();
        }
    }
}

