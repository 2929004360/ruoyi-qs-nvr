package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 交通事故事件 EVENT_IVS_TRAFFIC_REAREND_ACCIDENT 对应数据
 * @date 2022/07/29 11:20:55
 */
public class DEV_EVENT_TRAFFIC_REAREND_ACCIDENT_INFO extends NetSDKLib.SdkStructure {
    /**
     * 0:脉冲
     */
    public int              nAction;
    /**
     * 通道号
     */
    public int              nChannelID;
    /**
     * 事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     * 时间戳（单位：毫秒）
     */
    public double           PTS;
    /**
     * 事件发生的时间
     */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
    /**
     * 事件ID
     */
    public int              nEventID;
    /**
     * 标记抓拍帧
     */
    public int              nMark;
    /**
     * 视频分析的数据源地址
     */
    public int              nSource;
    /**
     * 视频分析帧序号
     */
    public int              nFrameSequence;
    /**
     * 车道号
     */
    public int              nLaneID;
    /**
     * 公共信息
     */
    public NetSDKLib.EVENT_COMM_INFO stuCommInfo = new NetSDKLib.EVENT_COMM_INFO();
    /**
     * 抓拍过程 {@link com.ruoyi.dahua.lib.enumeration.EM_CAPTURE_PROCESS_END_TYPE}
     */
    public int              emCaptureProcess;
    /**
     * 事件对应的文件信息
     */
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo = new NetSDKLib.NET_EVENT_FILE_INFO();
    /**
     * 交通车辆信息
     */
    public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar = new DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO();
    /**
     * 检测到的物体信息
     */
    public NetSDKLib.NET_MSG_OBJECT[] stuObjects = new NetSDKLib.NET_MSG_OBJECT[50];
    /**
     * 返回检测到的个数
     */
    public int              nObjectNum;
    /**
     * 保留字节
     */
    public byte[]           byResersed = new byte[1020];

    public DEV_EVENT_TRAFFIC_REAREND_ACCIDENT_INFO() {
       for (int i = 0; i < stuObjects.length; i++) {
           stuObjects[i] = new NetSDKLib.NET_MSG_OBJECT();
       }
    }
}

