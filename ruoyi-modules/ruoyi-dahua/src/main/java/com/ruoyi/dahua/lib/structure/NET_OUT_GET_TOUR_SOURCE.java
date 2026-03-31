package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_GetTourSource 接口输出参数
*/
public class NET_OUT_GET_TOUR_SOURCE extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 窗口轮巡信息数组, 用户分配内存,大小为sizeof(NET_SPLIT_TOUR_SOURCE)*nMaxWndCount,参见结构体定义 {@link NET_SPLIT_TOUR_SOURCE}
    */
    public Pointer          pstuWndSrcs;
    /**
     * 窗口最大数组, 用户填写
    */
    public int              nMaxWndCount;
    /**
     * 返回的窗口数量
    */
    public int              nRetWndCount;

    public NET_OUT_GET_TOUR_SOURCE() {
        this.dwSize = this.size();
    }
}

