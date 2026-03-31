package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_SetTourSource 接口输入参数(设置窗口轮巡显示源)
*/
public class NET_IN_SET_TOUR_SOURCE extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 输出通道号
    */
    public int              nChannel;
    /**
     * 窗口号
    */
    public int              nWindow;
    /**
     * 显示源数组, 可进行窗口轮巡,由用户申请内存，大小为sizeof(DH_SPLIT_SOURCE)*nSrcCount,参见结构体定义 {@link NetSDKLib.NET_SPLIT_SOURCE}
    */
    public Pointer          pstuSrcs;
    /**
     * 显示源数量
    */
    public int              nSrcCount;

    public NET_IN_SET_TOUR_SOURCE() {
        this.dwSize = this.size();
    }
}

