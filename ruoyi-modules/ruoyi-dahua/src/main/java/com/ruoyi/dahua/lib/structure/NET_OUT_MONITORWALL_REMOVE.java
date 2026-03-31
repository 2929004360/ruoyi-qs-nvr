package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_OperateMonitorWall接口输出参数=>NET_MONITORWALL_OPERATE_REMOVE
*/
public class NET_OUT_MONITORWALL_REMOVE extends NetSDKLib.SdkStructure
{
    public int              dwSize;

    public NET_OUT_MONITORWALL_REMOVE() {
        this.dwSize = this.size();
    }
}

