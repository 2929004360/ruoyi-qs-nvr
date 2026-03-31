package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_CreateLowPowerChannel 接口输入参数
*/
public class NET_IN_CREATE_LOW_POWER_CHANNEL extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 连接断线回调,参见回调函数定义 {@link NetSDKLib.fSubBizDisConnectCallBack}
    */
    public NetSDKLib.fSubBizDisConnectCallBack cbDisConnectCallBack;
    /**
     * 用户数据
    */
    public Pointer          dwUserData;
    /**
     * 低功耗设备保活状态回调,参见回调函数定义 {@link NetSDKLib.fLowPowerKeepAliveCallBack}
    */
    public NetSDKLib.fLowPowerKeepAliveCallBack cbLowPowerKeepAliveCallBack;

    public NET_IN_CREATE_LOW_POWER_CHANNEL() {
        this.dwSize = this.size();
    }
}

