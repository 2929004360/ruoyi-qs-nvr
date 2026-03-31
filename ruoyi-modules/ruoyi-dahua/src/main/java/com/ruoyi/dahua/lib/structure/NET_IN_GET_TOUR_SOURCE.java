package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_GetTourSource 接口输入参数
*/
public class NET_IN_GET_TOUR_SOURCE extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 输出通道号, pszCompsiteID为NULL时有效
    */
    public int              nChannel;
    /**
     * 拼接屏ID
    */
    public Pointer          pszCompositeID;
    /**
     * 窗口号, -1表示所有窗口
    */
    public int              nWindow;

    public NET_IN_GET_TOUR_SOURCE() {
        this.dwSize = this.size();
    }
}

