package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_DoFindObjectFavoritesLibrary 接口输出参数
*/
public class NET_OUT_DO_FIND_OBJECT_FAVORITES_LIBRARY extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 查询到的总条数
    */
    public int              nFound;
    /**
     * 记录集合个数
    */
    public int              nRetCount;
    /**
     * pstuRecords申请的最大个数
    */
    public int              nMaxCount;
    /**
     * 查询到的记录集合, 内存由用户申请释放, 大小为sizeof(NET_OBJECT_FAVORITES_LIBRARY_RECORDS)*nMaxCount,参见结构体定义 {@link NET_OBJECT_FAVORITES_LIBRARY_RECORDS}
    */
    public Pointer          pstuRecords;

    public NET_OUT_DO_FIND_OBJECT_FAVORITES_LIBRARY() {
        this.dwSize = this.size();
    }
}

