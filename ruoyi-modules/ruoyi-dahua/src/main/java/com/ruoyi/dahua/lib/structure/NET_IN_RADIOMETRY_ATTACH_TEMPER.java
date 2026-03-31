package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_RadiometryAttachTemper 入参
*/
public class NET_IN_RADIOMETRY_ATTACH_TEMPER extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 通道号
    */
    public int              nChannel;
    /**
     * 状态回调函数指针,参见回调函数定义 {@link NetSDKLib.fRadiometryAttachTemperCB}
    */
    public NetSDKLib.fRadiometryAttachTemperCB cbNotify;
    /**
     * 用户数据
    */
    public Pointer          dwUser;

    public NET_IN_RADIOMETRY_ATTACH_TEMPER() {
        this.dwSize = this.size();
    }
}

