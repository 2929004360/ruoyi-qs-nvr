package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetCollectDevicesInfoByTsfId 接口输出参数
*/
public class NET_OUT_GET_COLLECT_DEVICES_INFO_BY_TSFID_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 绑定的执法记录仪序列号列表个数
    */
    public int              nDeviceNum;
    /**
     * 绑定的执法记录仪序列号列表
    */
    public BYTE_ARRAY_32[]  szDevices = new BYTE_ARRAY_32[1024];

    public NET_OUT_GET_COLLECT_DEVICES_INFO_BY_TSFID_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < szDevices.length; i++){
            szDevices[i] = new BYTE_ARRAY_32();
        }
    }
}

