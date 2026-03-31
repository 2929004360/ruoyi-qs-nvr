package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_DHOP_CUSTOM 及 EVENT_IVS_DHOP_CUSTOM_ONCE (DHOP自定义事件)对应的数据块描述信息
*/
public class DEV_EVENT_DHOP_CUSTOM_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 0:脉冲 1:开始 2:停止
    */
    public int              nAction;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX UTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件ID
    */
    public int              nEventID;
    /**
     * 触发源ID
    */
    public int              nSourceIndex;
    /**
     * 预留,字节对齐
    */
    public byte[]           byReserved1 = new byte[4];
    /**
     * 事件触发源信息（APP信息）,参见结构体定义 {@link NET_DHOP_APP_INFO}
    */
    public NET_DHOP_APP_INFO stuAppInfo = new NET_DHOP_APP_INFO();
    /**
     * 自定义数据
    */
    public byte[]           szCustomData = new byte[4096];
    /**
     * 时间戳(单位是毫秒)
    */
    public double           PTS;
    /**
     * 事件所属大类
    */
    public byte[]           szClass = new byte[32];
    /**
     * 预留字节
    */
    public byte[]           byReserved = new byte[2008];

    public DEV_EVENT_DHOP_CUSTOM_INFO() {
    }
}

