package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * Onvif 新用户信息
*/
public class NET_ONVIF_USER_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 用户名
    */
    public byte[]           szName = new byte[128];
    /**
     * 密码
    */
    public byte[]           szPassword = new byte[128];
    /**
     * 最近修改密码的时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuPasswordModifiedTime = new NetSDKLib.NET_TIME();
    /**
     * 用户所在的组,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_GROUP_TYPE}
    */
    public int              emGroupType;
    /**
     * 用户是否为保留用户，保留用户不可删除
    */
    public int              bReserved;
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[512];

    public NET_ONVIF_USER_INFO() {
    }
}

