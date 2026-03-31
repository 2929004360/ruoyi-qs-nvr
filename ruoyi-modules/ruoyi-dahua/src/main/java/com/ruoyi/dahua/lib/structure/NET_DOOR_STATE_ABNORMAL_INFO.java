package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 门状态异常报警
*/
public class NET_DOOR_STATE_ABNORMAL_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 门状态的报警指定时间段，在指定时间段开门或关门为异常，产生报警,参见结构体定义 {@link NetSDKLib.NET_CFG_TIME_SCHEDULE}
    */
    public NetSDKLib.NET_CFG_TIME_SCHEDULE stuDSTimeSection = new NetSDKLib.NET_CFG_TIME_SCHEDULE();
    /**
     * 门的状态，开门状态1表示，关门状态0表示
    */
    public int              nDoorState;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[124];

    public NET_DOOR_STATE_ABNORMAL_INFO() {
    }
}

