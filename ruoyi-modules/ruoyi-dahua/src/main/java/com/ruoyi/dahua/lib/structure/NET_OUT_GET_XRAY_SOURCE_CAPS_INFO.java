package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetXRaySourceCaps 接口输出参数
*/
public class NET_OUT_GET_XRAY_SOURCE_CAPS_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * X射线源能力集信息,参见结构体定义 {@link NET_XRAY_SOURCE_CAPS}
    */
    public NET_XRAY_SOURCE_CAPS stuCaps = new NET_XRAY_SOURCE_CAPS();

    public NET_OUT_GET_XRAY_SOURCE_CAPS_INFO() {
        this.dwSize = this.size();
    }
}

