package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 去重后的进入人数小计信息
*/
public class NET_ENTERED_NO_DUP_SUBTOTAL_INFO extends NetSDKLib.SdkStructure
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
    public byte[]           szReserved = new byte[112];

    public NET_ENTERED_NO_DUP_SUBTOTAL_INFO() {
    }
}

