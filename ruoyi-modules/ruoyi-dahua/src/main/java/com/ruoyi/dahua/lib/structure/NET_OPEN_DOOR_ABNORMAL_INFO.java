package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 开门状态异常报警
*/
public class NET_OPEN_DOOR_ABNORMAL_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 开门状态的报警指定时间段，在指定时间段开门达到nLongTime，产生报警,参见结构体定义 {@link NetSDKLib.NET_CFG_TIME_SCHEDULE}
    */
    public NetSDKLib.NET_CFG_TIME_SCHEDULE stuODTimeSection = new NetSDKLib.NET_CFG_TIME_SCHEDULE();
    /**
     * 开门过长时间/min
    */
    public int              nLongTime;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[124];

    public NET_OPEN_DOOR_ABNORMAL_INFO() {
    }
}

