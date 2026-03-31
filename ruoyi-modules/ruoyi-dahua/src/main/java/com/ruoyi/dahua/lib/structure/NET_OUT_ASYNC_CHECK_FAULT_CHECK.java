package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_AsyncCheckFaultCheck 接口出参
*/
public class NET_OUT_ASYNC_CHECK_FAULT_CHECK extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;

    public NET_OUT_ASYNC_CHECK_FAULT_CHECK() {
        this.dwSize = this.size();
    }
}

