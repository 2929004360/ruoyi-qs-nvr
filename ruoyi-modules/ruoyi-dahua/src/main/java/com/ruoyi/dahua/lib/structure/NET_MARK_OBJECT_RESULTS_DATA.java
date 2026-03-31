package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 收藏结果集合
*/
public class NET_MARK_OBJECT_RESULTS_DATA extends NetSDKLib.SdkStructure
{
    /**
     * 收藏成功的唯一标识，若值为-1表示收藏失败
    */
    public int              nID;
    /**
     * 保留字段
    */
    public byte[]           szReserved = new byte[1020];

    public NET_MARK_OBJECT_RESULTS_DATA() {
    }
}

