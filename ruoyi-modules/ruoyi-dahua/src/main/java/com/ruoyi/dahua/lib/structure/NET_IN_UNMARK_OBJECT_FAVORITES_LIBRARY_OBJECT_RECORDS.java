package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_UnmarkObjectFavoritesLibraryObjectRecords 接口输入参数
*/
public class NET_IN_UNMARK_OBJECT_FAVORITES_LIBRARY_OBJECT_RECORDS extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 记录收藏唯一标识集合个数
    */
    public int              nIDCount;
    /**
     * 记录收藏唯一标识集合
    */
    public int[]            nIDs = new int[1000];

    public NET_IN_UNMARK_OBJECT_FAVORITES_LIBRARY_OBJECT_RECORDS() {
        this.dwSize = this.size();
    }
}

