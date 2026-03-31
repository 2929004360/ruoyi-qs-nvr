package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 收藏结果状态列表
*/
public class NET_OBJECT_FAVORITES_LIBRARY_MARK_STATUS_RESULTS extends NetSDKLib.SdkStructure
{
    /**
     * true表示已收藏，false表示未收藏
    */
    public int              bStatus;
    /**
     * 已收藏的唯一标识，status为true时，该字段有效
    */
    public int              nID;
    /**
     * 保留字段
    */
    public byte[]           szReserved = new byte[512];

    public NET_OBJECT_FAVORITES_LIBRARY_MARK_STATUS_RESULTS() {
    }
}

