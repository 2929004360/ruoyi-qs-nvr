package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 事件类型 EVENT_IVS_TRAFFIC_NONMOTOR_HOLDUMBRELLA (非机动车装载伞具事件) 对应的数据块描述信息
 * @date 2021/10/23
 */
public class DEV_EVENT_TRAFFIC_NONMOTOR_HOLDUMBRELLA_INFO extends NetSDKLib.SdkStructure {
	/**
     *  通道号
     */
    public int              nChannelID;
    /**
     *  0:脉冲 1:开始 2:停止
     */
    public int              nAction;
    /**
     *  事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     *  时间戳(单位是毫秒)
     */
    public double           PTS;
    /**
     *  事件发生的时间
     */
    public NET_TIME_EX      UTC;
    /**
     *  事件ID
     */
    public int              nEventID;
    /**
     *  事件对应文件信息
     */
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo;
    /**
     *  智能事件公共信息 
     */
    public NetSDKLib.EVENT_INTELLI_COMM_INFO stuIntelliCommInfo;
    /**
     *  抓图标志(按位),具体见NET_RESERVED_COMMON 
     */
    public int              dwSnapFlagMask;
    /**
     *  对应图片的分辨率
     */
    public NetSDKLib.NET_RESOLUTION_INFO stuResolution;
    /**
     *  检测到的物体
     */
    public NetSDKLib.NET_MSG_OBJECT stuObject;
    /**
     *  非机动车信息
     */
    public NetSDKLib.VA_OBJECT_NONMOTOR stuNonMotor;
    /**
     *  车道号
     */
    public int              nLane;
    /**
     *  抓拍序号，如3-2-1/0，1表示抓拍正常结束，0表示抓拍异常结束
     */
    public int              nSequence;
    /**
     *  公共信息
     */
    public NetSDKLib.EVENT_COMM_INFO stCommInfo;
    /**
     *  交通车辆信息
     */
    public NetSDKLib.DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;
    /**
     *  保留字节 
     */
    public byte[]           byReserved = new byte[1368];
    /**
     * 扩展协议字段,参见结构体定义 {@link NET_EVENT_INFO_EXTEND}
    */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
}

