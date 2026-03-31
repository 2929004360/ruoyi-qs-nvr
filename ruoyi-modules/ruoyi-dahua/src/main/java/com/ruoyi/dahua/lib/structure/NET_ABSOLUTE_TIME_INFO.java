package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 绝对时间信息
*/
public class NET_ABSOLUTE_TIME_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 开始时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuStartTime = new NetSDKLib.NET_TIME();
    /**
     * 结束时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuStopTime = new NetSDKLib.NET_TIME();
    /**
     * 绝对时间使能
    */
    public int              bEnable;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[28];

    public NET_ABSOLUTE_TIME_INFO() {
    }
}

