package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description  事件类型EVENT_IVS_TRAFFIC_PARKING_BACKING (出入口倒车驶离事件)对应的数据块描述信息
 * @origin autoTool
 * @date 2023/11/21 08:54:17
 */
public class DEV_EVENT_TRAFFIC_PARKING_BACKING_INFO extends NetSDKLib.SdkStructure {
    /**
     * / 通道号
     */
    public int              nChannelID;
    /**
     * / 0:脉冲
     */
    public int              nAction;
    /**
     * / 事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     * / 时间戳(单位是毫秒)
     */
    public double           dbPTS;
    /**
     * / 事件发生的时间
     */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
    /**
     * / 事件ID
     */
    public int              nEventID;
    /**
     * / nGroupID事件组ID,同一物体抓拍过程内nGroupID相同
     */
    public int              nGroupID;
    /**
     * / nCountInGroup一个事件组内的抓拍张数
     */
    public int              nCountInGroup;
    /**
     * / IndexInGroup一个事件组内的抓拍序号
     */
    public int              nIndexInGroup;
    /**
     * / 车牌信息
     */
    public NetSDKLib.NET_MSG_OBJECT stuObject = new NetSDKLib.NET_MSG_OBJECT();
    /**
     * / 车身信息
     */
    public NetSDKLib.NET_MSG_OBJECT stuVehicle = new NetSDKLib.NET_MSG_OBJECT();
    /**
     * / TriggerType:触发类型,0车检器,1雷达,2视频 {@link com.ruoyi.dahua.lib.enumeration.EM_TRIGGER_TYPE}
     */
    public int              emTriggerType;
    /**
     * / 底层产生的触发抓拍帧标记
     */
    public int              nMark;
    /**
     * / 视频分析帧序号
     */
    public int              nFrameSequence;
    /**
     * / 对应车道号
     */
    public int              nLane;
    /**
     * / 表示抓拍序号,如3,2,1,1表示抓拍结束,0表示异常结束
     */
    public int              nSequence;
    /**
     * / 车速，单位km/h
     */
    public int              nSpeed;
    /**
     * / 交通车辆信息
     */
    public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stuTrafficCar = new DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO();
    /**
     * / 公共信息
     */
    public NetSDKLib.EVENT_COMM_INFO stuCommInfo = new NetSDKLib.EVENT_COMM_INFO();
    /**
     * / 保留字节,留待扩展
     */
    public byte[]           szReserved = new byte[1024];

    public DEV_EVENT_TRAFFIC_PARKING_BACKING_INFO() {
    }
}

