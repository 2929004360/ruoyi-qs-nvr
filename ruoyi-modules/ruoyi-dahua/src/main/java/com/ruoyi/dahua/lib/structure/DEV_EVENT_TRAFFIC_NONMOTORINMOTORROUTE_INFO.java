package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description 事件类型 EVENT_IVS_TRAFFIC_NONMOTORINMOTORROUTE (非机动车占用机动车道)对应的数据块描述信息
 * @date 2021/10/23
 */
public class DEV_EVENT_TRAFFIC_NONMOTORINMOTORROUTE_INFO extends NetSDKLib.SdkStructure {
	  /**
     *  通道号
     */
    public int              nChannelID;
    /**
     *  事件名称
     */
    public byte[]           szName = new byte[NetSDKLib.NET_EVENT_NAME_LEN];
    /**
     *  事件动作,0表示脉冲事件,1表示持续性事件开始,2表示持续性事件结束;
     */
    public byte             bEventAction;
    /**
     *  保留字节.
     */
    public byte[]           bReserved1 = new byte[3];
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
     *  交通车辆信息
     */
    public NetSDKLib.DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;
    /**
     *  检测到的物体
     */
    public NetSDKLib.NET_MSG_OBJECT stuObject;
    /**
     *  车身信息 
     */
    public NetSDKLib.NET_MSG_OBJECT stuVehicle;
    /**
     *  抓图标志(按位),具体见NET_RESERVED_COMMON 
     */
    public int              dwSnapFlagMask;
    /**
     *  对应图片的分辨率
     */
    public NetSDKLib.NET_RESOLUTION_INFO stuResolution;
    /**
     *  报警对应的原始录像文件信息
     */
    public byte[]           szRecordFile = new byte[NetSDKLib.NET_COMMON_STRING_128];
    /**
     *  对应车道号
     */
    public int              nLane;
    /**
     *  表示抓拍序号,如3,2,1,1表示抓拍结束,0表示异常结束 
     */
    public int              nSequence;
    /**
     *  公共信息
     */
    public NetSDKLib.EVENT_COMM_INFO stCommInfo;
    /**
     *  是否有非机动车对象
     */
    public int              bHasNonMotor;
    /**
     *  非机动车对象
     */
    public NetSDKLib.VA_OBJECT_NONMOTOR stuNonMotor;
    /**
     * 图片信息数组,参见结构体定义 {@link NET_IMAGE_INFO_EX3}
    */
    public Pointer          pstuImageInfo;
    /**
     * 图片信息个数
    */
    public int              nImageInfoNum;
    /**
     * 扩展协议字段,参见结构体定义 {@link NET_EVENT_INFO_EXTEND}
    */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     *  保留字节,留待扩展.
     */
    public byte[]           bReserved = new byte[3028-NetSDKLib.POINTERSIZE];
}

