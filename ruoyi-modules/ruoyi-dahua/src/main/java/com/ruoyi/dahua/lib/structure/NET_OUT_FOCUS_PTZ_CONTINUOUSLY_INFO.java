package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_FocusPTZContinuously 接口输出参数
*/
public class NET_OUT_FOCUS_PTZ_CONTINUOUSLY_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;

    public NET_OUT_FOCUS_PTZ_CONTINUOUSLY_INFO() {
        this.dwSize = this.size();
    }
}

