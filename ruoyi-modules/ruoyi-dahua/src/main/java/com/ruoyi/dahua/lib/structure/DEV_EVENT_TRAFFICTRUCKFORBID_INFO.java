package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.DH_MSG_OBJECT;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description 事件类型 EVENT_IVS_TRAFFIC_TRUCKFORBID(货车禁止事件)对应的数据块描述信息
 * @date 2023/03/17 10:27:24
 */
public class DEV_EVENT_TRAFFICTRUCKFORBID_INFO extends NetSDKLib.SdkStructure {
    /**
     * 通道号
     */
    public int              nChannel;
    /**
     * 事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     * 事件组ID，同一辆车抓拍过程内GroupID相同
     */
    public int              nGroupID;
    /**
     * 一个事件组内的抓拍张数
     */
    public int              nCountInGroup;
    /**
     * 一个事件组内的抓拍序号
     */
    public int              nIndexInGroup;
    /**
     * 时间戳(单位是毫秒)
     */
    public double           PTS;
    /**
     * 事件发生的时间
     */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
    /**
     * UTC时间对应的毫秒数
     */
    public int              UTCMS;
    /**
     * 事件ID
     */
    public int              nEventID;
    /**
     * 车牌信息
     */
    public DH_MSG_OBJECT    stuObject = new DH_MSG_OBJECT();
    /**
     * 车身信息
     */
    public DH_MSG_OBJECT    stuVehicle = new DH_MSG_OBJECT();
    /**
     * 车道号
     */
    public int              nLane;
    /**
     * 表示抓拍序号,如3,2,1,1表示抓拍结束,0表示异常结束
     */
    public int              nSequence;
    /**
     * 车辆信息
     */
    public NetSDKLib.DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar = new NetSDKLib.DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO();
    /**
     * 公共信息
     */
    public NetSDKLib.EVENT_COMM_INFO stCommInfo = new NetSDKLib.EVENT_COMM_INFO();
    /**
     * 事件对应文件信息
     */
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo = new NetSDKLib.NET_EVENT_FILE_INFO();
    /**
     * 抓图标志(按位),具体见NET_RESERVED_COMMON
     */
    public int              dwSnapFlagMask;
    /**
     * 对应图片的分辨率
     */
    public NetSDKLib.NET_RESOLUTION_INFO stuResolution = new NetSDKLib.NET_RESOLUTION_INFO();
    /**
     * 全景图,参见结构体定义 {@link NetSDKLib.SCENE_IMAGE_INFO_EX}
    */
    public Pointer          pstuSceneImage;
    /**
     * 保留字节
     */
    public byte[]           byReserved = new byte[1016-NetSDKLib.POINTERSIZE];
    /**
     * 扩展协议字段,参见结构体定义 {@link NET_EVENT_INFO_EXTEND}
    */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();

    public DEV_EVENT_TRAFFICTRUCKFORBID_INFO() {
    }
}

