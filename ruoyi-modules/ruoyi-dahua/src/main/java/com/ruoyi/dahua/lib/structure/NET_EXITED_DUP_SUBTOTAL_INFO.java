package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 出去重复小计
*/
public class NET_EXITED_DUP_SUBTOTAL_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 设备运行后经过人数统计总数
    */
    public int              nTotal;
    /**
     * 小时内的总人数
    */
    public int              nHour;
    /**
     * 当天的总人数(自然天)
    */
    public int              nToday;
    /**
     * IPC专用，如果不执行clearSectionStat操作，等同于Today人数
    */
    public int              nTotalInTimeSection;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[32];

    public NET_EXITED_DUP_SUBTOTAL_INFO() {
    }
}

