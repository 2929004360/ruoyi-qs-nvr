package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 DH_ALARM_TRAFFIC_PARKING(交通违章停车事件)对应的数据块描述信息
*/
public class ALARM_TRAFFIC_PARKING_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 事件动作,0表示脉冲事件,1表示持续性事件开始,2表示持续性事件结束;
    */
    public int              nAction;
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 时间戳(单位是毫秒)
    */
    public double           dbPTS;
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuTime = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件ID
    */
    public int              nEventID;
    /**
     * 事件公共扩展字段结构体,参见结构体定义 {@link NET_EVENT_INFO_EXTEND}
    */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 车道号
    */
    public int              nLane;
    /**
     * 最后一张图片的时间, 格式为：yyyy-MM-ddTHH:mm:ss.SSSXXX, 其中T为不需要解析的无意义字符，XXX为时区
    */
    public byte[]           szRecordEndTime = new byte[64];
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[956];

    public ALARM_TRAFFIC_PARKING_INFO() {
    }
}

