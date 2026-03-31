package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 区域行人/非机动车流量随帧信息
*/
public class NET_AREA_FLOW_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 相对事件时间戳,单位毫秒
    */
    public long             dbPTS;
    /**
     * 视频分析帧序号
    */
    public int              nSequence;
    /**
     * 保留字节
    */
    public byte[]           szReserved1 = new byte[4];
    /**
     * 对应的UTC时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_AREA_FLOW_INFO() {
    }
}

