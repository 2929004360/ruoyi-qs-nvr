package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 添加Onvif用户，CLIENT_AddOnvifUser 入参
*/
public class NET_IN_ADD_ONVIF_USER_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 用户名
    */
    public byte[]           szName = new byte[128];
    /**
     * 密码
    */
    public byte[]           szPassword = new byte[128];
    /**
     * 用户所在的组,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_GROUP_TYPE}
    */
    public int              emGroupType;

    public NET_IN_ADD_ONVIF_USER_INFO() {
        this.dwSize = this.size();
    }
}

