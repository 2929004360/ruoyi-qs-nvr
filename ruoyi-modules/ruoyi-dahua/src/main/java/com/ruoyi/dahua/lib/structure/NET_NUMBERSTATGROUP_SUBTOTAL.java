package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 统计小计信息
*/
public class NET_NUMBERSTATGROUP_SUBTOTAL extends NetSDKLib.SdkStructure
{
    /**
     * 设备运行后人数统计总数
    */
    public int              nTotal;
    /**
     * 小时内的总人数
    */
    public int              nHour;
    /**
     * 当天的总人数, 不可手动清除
    */
    public int              nToday;
    /**
     * IPC专用，如果不执行clearSectionStat操作，等同于Today人数
    */
    public int              nTotalInTimeSection;
    public byte[]           byReserved = new byte[252];

    public NET_NUMBERSTATGROUP_SUBTOTAL() {
    }
}

