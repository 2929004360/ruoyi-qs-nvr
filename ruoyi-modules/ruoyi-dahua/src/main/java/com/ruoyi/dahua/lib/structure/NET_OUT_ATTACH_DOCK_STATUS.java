package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_AttachDockStatus 接口输出参数
*/
public class NET_OUT_ATTACH_DOCK_STATUS extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;

    public NET_OUT_ATTACH_DOCK_STATUS() {
        this.dwSize = this.size();
    }
}

