package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_CheckObjectFavoritesLibraryMarkStatus 接口输出参数
*/
public class NET_OUT_CHECK_OBJECT_FAVORITES_LIBRARY_MARK_STATUS extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * pstuResults最大个数
    */
    public int              nMaxCount;
    /**
     * 收藏结果状态列表, 内存由用户申请释放, 大小为sizeof(NET_OBJECT_FAVORITES_LIBRARY_MARK_STATUS_RESULTS)*nMaxCount,参见结构体定义 {@link NET_OBJECT_FAVORITES_LIBRARY_MARK_STATUS_RESULTS}
    */
    public Pointer          pstuResults;
    /**
     * 实际返回的个数
    */
    public int              nRetCount;

    public NET_OUT_CHECK_OBJECT_FAVORITES_LIBRARY_MARK_STATUS() {
        this.dwSize = this.size();
    }
}

