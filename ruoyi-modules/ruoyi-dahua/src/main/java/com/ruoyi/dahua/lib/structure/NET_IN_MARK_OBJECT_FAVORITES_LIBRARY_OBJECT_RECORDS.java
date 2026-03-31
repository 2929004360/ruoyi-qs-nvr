package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_MarkObjectFavoritesLibraryObjectRecords 接口输入参数
*/
public class NET_IN_MARK_OBJECT_FAVORITES_LIBRARY_OBJECT_RECORDS extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 收藏的数据来源, 0: 人脸以图搜图, 1: QuickPick以图搜图, 2: AcuPick以图搜图, 3: IVS查询搜索
    */
    public int              nSearchType;
    /**
     * 收藏的目标事件数据, 内存由用户申请释放, 大小为sizeof(NET_OBJECT_FAVORITES_LIBRARY_COLLECT_DATA)*nCollectDatasNum,参见结构体定义 {@link NET_OBJECT_FAVORITES_LIBRARY_COLLECT_DATA}
    */
    public Pointer          pstuCollectDatas;
    /**
     * 收藏的目标事件数据个数
    */
    public int              nCollectDatasNum;
    /**
     * 字节对齐
    */
    public byte[]           szReserved = new byte[4];

    public NET_IN_MARK_OBJECT_FAVORITES_LIBRARY_OBJECT_RECORDS() {
        this.dwSize = this.size();
    }
}

