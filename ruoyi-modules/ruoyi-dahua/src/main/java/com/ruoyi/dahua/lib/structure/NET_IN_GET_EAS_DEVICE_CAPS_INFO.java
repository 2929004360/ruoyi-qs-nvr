package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetEASDeviceCaps 接口入参
*/
public class NET_IN_GET_EAS_DEVICE_CAPS_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 获取EAS设备能力集查询参数数组大小
    */
    public int              nEASGetDeviceCapsRequestParamsNum;
    /**
     * 获取EAS设备能力集查询参数,参见结构体定义 NET_EAS_GET_DEVICE_CAPS_REQ_PARAM
    */
    public NET_EAS_GET_DEVICE_CAPS_REQ_PARAM[] stuEASGetDeviceCapsRequestParams = new NET_EAS_GET_DEVICE_CAPS_REQ_PARAM[64];

    public NET_IN_GET_EAS_DEVICE_CAPS_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuEASGetDeviceCapsRequestParams.length; i++){
            stuEASGetDeviceCapsRequestParams[i] = new NET_EAS_GET_DEVICE_CAPS_REQ_PARAM();
        }
    }
}

