package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 记录收藏条件集合
*/
public class NET_CHECK_MARK_STATUS_CONDITIONS extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannel;
    /**
     * 目标类型, 0 人脸, 1 人体, 2 机动车, 3 非机动车, 4 动物
    */
    public int              nObjectType;
    /**
     * 原目标记录编号ObjectID
    */
    public int              nUID;
    /**
     * 收藏的数据来源, 0: 人脸以图搜图, 1: QuickPick以图搜图, 2: AcuPick以图搜图, 3: IVS查询搜索,
    */
    public int              nSearchType;
    /**
     * 保留字段
    */
    public byte[]           szReserved = new byte[512];

    public NET_CHECK_MARK_STATUS_CONDITIONS() {
    }
}

