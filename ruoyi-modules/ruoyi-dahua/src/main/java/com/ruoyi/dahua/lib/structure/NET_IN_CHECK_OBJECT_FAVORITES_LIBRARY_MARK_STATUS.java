package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_CheckObjectFavoritesLibraryMarkStatus 接口输入参数
*/
public class NET_IN_CHECK_OBJECT_FAVORITES_LIBRARY_MARK_STATUS extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 记录收藏条件集合个数
    */
    public int              nConditionsCount;
    /**
     * 记录收藏条件集合, 内存由用户申请释放, 大小为sizeof(NET_OBJECT_FAVORITES_LIBRARY_MARK_STATUS_RESULTS)*nConditionsCount,参见结构体定义 {@link NET_CHECK_MARK_STATUS_CONDITIONS}
    */
    public Pointer          pstuConditions;

    public NET_IN_CHECK_OBJECT_FAVORITES_LIBRARY_MARK_STATUS() {
        this.dwSize = this.size();
    }
}

