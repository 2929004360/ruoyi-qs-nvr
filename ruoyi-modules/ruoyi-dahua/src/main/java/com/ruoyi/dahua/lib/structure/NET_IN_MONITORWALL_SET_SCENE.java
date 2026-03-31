package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_MonitorWallSetScene接口输入参数(设置电视墙场景)
*/
public class NET_IN_MONITORWALL_SET_SCENE extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 电视墙序号
    */
    public int              nMonitorWallID;
    /**
     * 电视墙场景,参见结构体定义 {@link NET_MONITORWALL_SCENE}
    */
    public NET_MONITORWALL_SCENE stuScene = new NET_MONITORWALL_SCENE();

    public NET_IN_MONITORWALL_SET_SCENE() {
        this.dwSize = this.size();
    }
}

