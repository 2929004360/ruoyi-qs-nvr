package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型DH_ALARM_XRAY_EMERGENCY_STOP (X光机紧急停止事件) 对应的数据块描述信息
*/
public class ALARM_XRAY_EMERGENCY_STOP_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 触发急停事件的按钮位置：0：不区分，1：键盘，2：机身，3：进包口1，4：进包口2，5：出包口1，6：出包口2
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
     * 事件公共扩展字段结构体,参见结构体定义 {@link NET_EVENT_INFO_EXTEND}
    */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 预留字段
    */
    public byte[]           szReserved = new byte[1024];

    public ALARM_XRAY_EMERGENCY_STOP_INFO() {
    }
}

