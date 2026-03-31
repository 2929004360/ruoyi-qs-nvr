package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description 事件类型 EVENT_IVS_TRAFFIC_NON_MOTOR_RETROGRADE(非机动车逆行事件)对应的数据块描述信息
 * @date 2021/10/23
 */
public class DEV_EVENT_TRAFFIC_NON_MOTOR_RETROGRADE_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  通道号
     */
    public int              nChannelID;
    /**
     *  0:脉冲事件
     */
    public int              nAction;
    /**
     *  事件名称 
     */
    public byte[]           szName = new byte[128];
    /**
     *  事件对应文件信息
     */
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo;
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
     *  规则编号,用于标示哪个规则触发的事件，缺省时默认为0
     */
    public int              nRuleID;
    /**
     *  触发类型
     */
    public int              emTriggerLine;
    /**
     *  标记抓拍帧
     */
    public int              nMark;
    /**
     *  视频分析的数据源地址
     */
    public int              nSource;
    /**
     *  视频分析帧序号
     */
    public int              nFrameSequence;
    /**
     *  车道号
     */
    public int              nLaneID;
    /**
     *  车速(单位:km/h) 
     */
    public int              nSpeed;
    /**
     *  抓拍过程
     */
    public int              emCaptureProcess;
    /**
     *  公共信息
     */
    public NetSDKLib.EVENT_COMM_INFO stuCommInfo;
    /**
     *  是否有非机动车信息
     */
    public int              bNonMotorInfoEx;
    /**
     *  非机动车信息
     */
    public NetSDKLib.VA_OBJECT_NONMOTOR stuNonMotor;
    /**
     *  抓图标志(按位),具体见NET_RESERVED_COMMON 
     */
    public int              dwSnapFlagMask;
    /**
     *  对应图片的分辨率
     */
    public NetSDKLib.NET_RESOLUTION_INFO stuResolution;
    /**
     * 图片信息数组,参见结构体定义 {@link NET_IMAGE_INFO_EX3}
    */
    public Pointer          pstuImageInfo;
    /**
     * 图片信息个数
    */
    public int              nImageInfoNum;
    /**
     *  预留字段
     */
    public byte[]           byReserved = new byte[1008-NetSDKLib.POINTERSIZE];
}

