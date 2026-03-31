package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 添加电视墙输入参数
*/
public class NET_IN_MONITORWALL_ADD extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 电视墙信息,参见结构体定义 {@link NetSDKLib.NET_MONITORWALL}
    */
    public NetSDKLib.NET_MONITORWALL stuMonitorWall = new NetSDKLib.NET_MONITORWALL();

    public NET_IN_MONITORWALL_ADD() {
        this.dwSize = this.size();
    }
}

