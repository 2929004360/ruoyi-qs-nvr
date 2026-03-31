package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_MarkObjectFavoritesLibraryObjectRecords 接口输出参数
*/
public class NET_OUT_MARK_OBJECT_FAVORITES_LIBRARY_OBJECT_RECORDS extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 收藏结果码, 0: 收藏成功 1: 收藏失败 2: 收藏夹已满
    */
    public int              nResultCode;
    /**
     * 收藏结果集合, 内存由用户申请释放, 大小为sizeof(NET_MARK_OBJECT_RESULTS_DATA)*nMaxCount,参见结构体定义 {@link NET_MARK_OBJECT_RESULTS_DATA}
    */
    public Pointer          pstuResultData;
    /**
     * 收藏结果集合最个数
    */
    public int              nMaxCount;
    /**
     * 收藏结果集合个数
    */
    public int              nRetCount;

    public NET_OUT_MARK_OBJECT_FAVORITES_LIBRARY_OBJECT_RECORDS() {
        this.dwSize = this.size();
    }
}

