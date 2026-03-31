package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_AttachIotboxComm 接口出参
*/
public class NET_OUT_ATTACH_IOTBOX_COMM extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;

    public NET_OUT_ATTACH_IOTBOX_COMM() {
        this.dwSize = this.size();
    }
}

