package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_DoFindObjectFavoritesLibrary 接口输入参数
*/
public class NET_IN_DO_FIND_OBJECT_FAVORITES_LIBRARY extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 查询的偏移条数
    */
    public int              nOffset;
    /**
     * 本次查询需要获取的条数
    */
    public int              nCount;

    public NET_IN_DO_FIND_OBJECT_FAVORITES_LIBRARY() {
        this.dwSize = this.size();
    }
}

