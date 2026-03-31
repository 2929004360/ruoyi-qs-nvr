package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 获取所有 Onvif 用户信息， CLIENT_GetOnvifUserInfoAll 出参
*/
public class NET_OUT_GETONVIF_USERINFO_ALL_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 本次已查询到的个数
    */
    public int              nRetUserInfoNumber;
    /**
     * 用户信息列表(无法获取到密码信息),参见结构体定义 {@link NET_ONVIF_USER_INFO}
    */
    public NET_ONVIF_USER_INFO[] stuUserInfo = new NET_ONVIF_USER_INFO[20];

    public NET_OUT_GETONVIF_USERINFO_ALL_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuUserInfo.length; i++){
            stuUserInfo[i] = new NET_ONVIF_USER_INFO();
        }
    }
}

