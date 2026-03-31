package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_DetachNormalUsingJson 接口输入参数
*/
public class NET_IN_DETACH_NORMAL_USING_JSON extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 订阅类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_SUPPORT_ATTACH_TYPE}
    */
    public int              emAttachType;
    /**
     * 订阅句柄
    */
    public NetSDKLib.LLong  lAttachHandle;
    /**
     * 订阅参数，见EM_SUPPORT_ATTACH_TYPE枚举说明
    */
    public Pointer          pstuDetachParam;

    public NET_IN_DETACH_NORMAL_USING_JSON() {
        this.dwSize = this.size();
    }
}

