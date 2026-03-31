package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 获取屏幕窗口解码信息CLIENT_MonitorWallGetWindowInfo入参
*/
public class NET_IN_MW_GET_WINODW_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 电视墙序号
    */
    public int              nMonitorWallID;
    /**
     * 拼接屏或者单屏ID
    */
    public Pointer          pszCompositeID;

    public NET_IN_MW_GET_WINODW_INFO() {
        this.dwSize = this.size();
    }
}

