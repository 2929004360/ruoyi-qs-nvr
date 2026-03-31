package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_ControlDevice接口的 DH_CTRL_NOTIFY_EVNENT 命令参数(向设备发送事件)
*/
public class NET_NOTIFY_EVENT_DATA extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 事件类型
    */
    public int              lCommand;
    /**
     * 事件内容,与lCommand对应的结构体指针
    */
    public Pointer          pEventData;

    public NET_NOTIFY_EVENT_DATA() {
        this.dwSize = this.size();
    }
}

