package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 查询条件
*/
public class NET_VEHICLE_FLOW_STAT_CONDITION extends NetSDKLib.SdkStructure
{
    /**
     * 开始时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuStartTime = new NetSDKLib.NET_TIME();
    /**
     * 结束时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuEndTime = new NetSDKLib.NET_TIME();
    /**
     * 查询要求返回的报表统计信息粒度, "Hour"按小时, "Day"按天, "Month"按月, "Year"按年
    */
    public byte[]           szGranularity = new byte[8];
    /**
     * 选择查询的通道
    */
    public int[]            nChannels = new int[1024];
    /**
     * 查询通道有效个数
    */
    public int              nChannelsNum;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_VEHICLE_FLOW_STAT_CONDITION() {
    }
}

