package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 道闸禁开配置
*/
public class NET_CFG_FORBID_OPEN extends NetSDKLib.SdkStructure
{
    /**
     * 禁开功能使能，为true时还需要继续判断TimeSchedule内的使能位
    */
    public int              bEnable;
    /**
     * 禁开模式执行时间段,参见结构体定义 {@link NetSDKLib.NET_CFG_TIME_SCHEDULE}
    */
    public NetSDKLib.NET_CFG_TIME_SCHEDULE stuTimeShecule = new NetSDKLib.NET_CFG_TIME_SCHEDULE();
    /**
     * 禁开提醒,参见结构体定义 {@link NET_FORBID_NOTICE_INFO}
    */
    public NET_FORBID_NOTICE_INFO stuForbidNotice = new NET_FORBID_NOTICE_INFO();
    /**
     * 预留字段
    */
    public byte[]           szReserved = new byte[512];

    public NET_CFG_FORBID_OPEN() {
    }
}

