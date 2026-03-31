package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 查询条件
*/
public class NET_XRAY_DOWNLOAD_RECORD_CONDITION extends NetSDKLib.SdkStructure
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
     * 通道号
    */
    public int              nChannel;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_XRAY_DOWNLOAD_RECORD_CONDITION() {
    }
}

