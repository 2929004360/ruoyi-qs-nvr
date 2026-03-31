package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 添加Onvif用户，CLIENT_AddOnvifUser 出参
*/
public class NET_OUT_ADD_ONVIF_USER_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;

    public NET_OUT_ADD_ONVIF_USER_INFO() {
        this.dwSize = this.size();
    }
}

