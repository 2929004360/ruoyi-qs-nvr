package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * ONVIF服务配置, 对应 NET_EM_CFG_VSP_ONVIF
*/
public class NET_CFG_VSP_ONVIF_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 开关控制
    */
    public int              bServiceStart;

    public NET_CFG_VSP_ONVIF_INFO() {
        this.dwSize = this.size();
    }
}

