package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_AttachDockEvents 接口输出参数
*/
public class NET_OUT_ATTACH_DOCK_EVENTS extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;

    public NET_OUT_ATTACH_DOCK_EVENTS() {
        this.dwSize = this.size();
    }
}

