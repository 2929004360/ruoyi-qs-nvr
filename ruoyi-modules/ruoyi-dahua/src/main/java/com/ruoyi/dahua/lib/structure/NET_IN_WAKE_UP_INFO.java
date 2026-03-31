package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_DoWakeUpLowPowerDevcie 接口输入参数
*/
public class NET_IN_WAKE_UP_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;

    public NET_IN_WAKE_UP_INFO() {
        this.dwSize = this.size();
    }
}

