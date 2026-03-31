package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 回调的实时数据
*/
public class NET_NOTIFY_IOTBOX_COMM extends NetSDKLib.SdkStructure
{
    /**
     * 传感器的信息,参见结构体定义 {@link NET_IOTBOX_DEVICE_INFO}
    */
    public NET_IOTBOX_DEVICE_INFO[] stuDeviceInfo = new NET_IOTBOX_DEVICE_INFO[16];
    /**
     * 传感器的信息个数
    */
    public int              nDeviceInfoNum;
    /**
     * 保留字段
    */
    public byte[]           szResvered = new byte[1028];

    public NET_NOTIFY_IOTBOX_COMM() {
        for(int i = 0; i < stuDeviceInfo.length; i++){
            stuDeviceInfo[i] = new NET_IOTBOX_DEVICE_INFO();
        }
    }
}

