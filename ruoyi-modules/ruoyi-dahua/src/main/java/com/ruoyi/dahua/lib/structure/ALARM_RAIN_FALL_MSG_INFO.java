package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型DH_ALARM_RAIN_FALL_MSG (雨量数据上报事件) 对应的数据块描述信息
*/
public class ALARM_RAIN_FALL_MSG_INFO extends NetSDKLib.SdkStructure
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
     * 时间戳(单位是毫秒)
    */
    public double           dbPTS;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuTime = new NetSDKLib.NET_TIME_EX();
    /**
     * 智能事件所属大类,参见枚举定义 {@link NetSDKLib.EM_SCENE_CLASS_TYPE}
    */
    public int              emClassType;
    /**
     * 事件ID
    */
    public int              nEventID;
    /**
     * 事件的数据类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.NETAEM_EVENT_TYPE}
    */
    public int              emEventType;
    /**
     * 雨量高度(单位：毫米)
    */
    public float            fRainLevel;
    /**
     * 雨量状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_RAIN_FALL_STATUS}
    */
    public int              emStatus;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1024];

    public ALARM_RAIN_FALL_MSG_INFO() {
    }
}

