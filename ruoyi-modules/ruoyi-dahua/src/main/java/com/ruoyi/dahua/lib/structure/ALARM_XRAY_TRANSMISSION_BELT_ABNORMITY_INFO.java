package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型DH_ALARM_XRAY_TRANSMISSION_BELT_ABNORMITY (X光机图像传送带状态异常事件) 对应的数据块描述信息
*/
public class ALARM_XRAY_TRANSMISSION_BELT_ABNORMITY_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 事件动作,0表示脉冲事件,1表示事件开始,2表示事件结束;
    */
    public int              nAction;
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuTime = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件ID
    */
    public int              nEventID;
    /**
     * 时间戳(单位是毫秒)
    */
    public double           dbPTS;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 预留字段
    */
    public byte[]           szReserved = new byte[1024];

    public ALARM_XRAY_TRANSMISSION_BELT_ABNORMITY_INFO() {
    }
}

