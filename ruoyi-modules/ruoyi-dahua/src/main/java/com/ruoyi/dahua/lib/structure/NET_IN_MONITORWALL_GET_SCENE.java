package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_MonitorWallGetScene接口输入参数(获取电视墙场景)
*/
public class NET_IN_MONITORWALL_GET_SCENE extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 电视墙序号
    */
    public int              nMonitorWallID;

    public NET_IN_MONITORWALL_GET_SCENE() {
        this.dwSize = this.size();
    }
}

