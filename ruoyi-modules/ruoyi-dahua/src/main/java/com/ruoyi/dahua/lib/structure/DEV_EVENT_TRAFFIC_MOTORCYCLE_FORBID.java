package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_TRAFFIC_MOTORCYCLE_FORBID (禁摩事件) 对应的数据描述信息
*/
public class DEV_EVENT_TRAFFIC_MOTORCYCLE_FORBID extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 事件动作,0表示脉冲事件,1表示持续性事件开始,2表示持续性事件结束;
    */
    public int              nAction;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 事件组ID
    */
    public int              nGroupID;
    /**
     * 一个事件组内应有的抓拍张数
    */
    public int              nCountInGroup;
    /**
     * 一个事件组内的抓拍序号
    */
    public int              nIndexInGroup;
    /**
     * 事件ID编号
    */
    public int              nEventID;
    /**
     * 时间戳(单位是毫秒)
    */
    public double           PTS;
    /**
     * 事件发生的时间(单位是秒),参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX UTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件时间(单位是毫秒)
    */
    public int              UTCMS;
    /**
     * 检测到的车牌信息,参见结构体定义 {@link NetSDKLib.NET_MSG_OBJECT}
    */
    public NetSDKLib.NET_MSG_OBJECT stuObject = new NetSDKLib.NET_MSG_OBJECT();
    /**
     * 检测到的车辆信息,参见结构体定义 {@link NetSDKLib.NET_MSG_OBJECT}
    */
    public NetSDKLib.NET_MSG_OBJECT stuVehicle = new NetSDKLib.NET_MSG_OBJECT();
    /**
     * 事件对应文件信息,参见结构体定义 {@link NetSDKLib.NET_EVENT_FILE_INFO}
    */
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo = new NetSDKLib.NET_EVENT_FILE_INFO();
    /**
     * 非机动车信息,参见结构体定义 {@link NetSDKLib.VA_OBJECT_NONMOTOR}
    */
    public NetSDKLib.VA_OBJECT_NONMOTOR stuNonMotor = new NetSDKLib.VA_OBJECT_NONMOTOR();
    /**
     * 是否有非机动车信息
    */
    public int              bNonMotorInfoEx;
    /**
     * 对应车道号
    */
    public int              nLane;
    /**
     * 抓拍序号 0/1 : 1表示抓拍正常结束，0表示抓拍异常结束
    */
    public int              nSequence;
    /**
     * 交通车辆的数据库记录,参见结构体定义 {@link NetSDKLib.DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO}
    */
    public NetSDKLib.DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stuTrafficCar = new NetSDKLib.DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO();
    /**
     * 公共信息,参见结构体定义 {@link NetSDKLib.EVENT_COMM_INFO}
    */
    public NetSDKLib.EVENT_COMM_INFO stuCommInfo = new NetSDKLib.EVENT_COMM_INFO();
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[1020];
    /**
     * 扩展协议字段,参见结构体定义 {@link NET_EVENT_INFO_EXTEND}
    */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();

    public DEV_EVENT_TRAFFIC_MOTORCYCLE_FORBID() {
    }
}

