package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 查询到的记录集合
*/
public class NET_OBJECT_FAVORITES_LIBRARY_RECORDS extends NetSDKLib.SdkStructure
{
    /**
     * 记录收藏的唯一标识
    */
    public int              nID;
    /**
     * 通道号
    */
    public int              nChannel;
    /**
     * 收藏时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuCollectTime = new NetSDKLib.NET_TIME();
    /**
     * 目标类型
    */
    public int              nObjectType;
    /**
     * UID
    */
    public int              nUID;
    /**
     * 收藏的数据来源, 0: 人脸以图搜图, 1: QuickPick以图搜图, 2: AcuPick以图搜图, 3: IVS查询搜索,
    */
    public int              nSearchType;
    /**
     * 收藏的目标事件数据,参见结构体定义 {@link NET_OBJECT_FAVORITES_LIBRARY_COLLECT_DATA}
    */
    public NET_OBJECT_FAVORITES_LIBRARY_COLLECT_DATA stuCollectData = new NET_OBJECT_FAVORITES_LIBRARY_COLLECT_DATA();
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_OBJECT_FAVORITES_LIBRARY_RECORDS() {
    }
}

