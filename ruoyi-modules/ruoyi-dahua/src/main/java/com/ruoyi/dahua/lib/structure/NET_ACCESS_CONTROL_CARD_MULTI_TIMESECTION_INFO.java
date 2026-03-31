package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 一人对单个门支持多时段和假日计划方案
*/
public class NET_ACCESS_CONTROL_CARD_MULTI_TIMESECTION_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 门权限，控制器中的索引
    */
    public int              nDoor;
    /**
     * 门权限对应时间段索引数量
    */
    public int              nTimeSectionNum;
    /**
     * 门权限对应时间段索引, 表示当前门权限可在此时间段通行
    */
    public int[]            nTimeSectionList = new int[128];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[504];

    public NET_ACCESS_CONTROL_CARD_MULTI_TIMESECTION_INFO() {
    }
}

