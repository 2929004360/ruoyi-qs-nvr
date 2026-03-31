package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_DockEventsReply 接口输出参数
*/
public class NET_OUT_DOCK_EVENTS_REPLY extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;

    public NET_OUT_DOCK_EVENTS_REPLY() {
        this.dwSize = this.size();
    }
}

