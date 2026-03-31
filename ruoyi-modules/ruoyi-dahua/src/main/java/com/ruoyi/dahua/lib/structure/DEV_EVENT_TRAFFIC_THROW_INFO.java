package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

import static com.ruoyi.dahua.lib.NetSDKLib.NET_EVENT_NAME_LEN;

/**
 * @author ： 260611
 * @description ： 事件类型 EVENT_IVS_TRAFFIC_THROW(交通抛洒物品事件)对应数据块描述信息
 * @since ： Created in 2022/01/18 14:34
 */

public class DEV_EVENT_TRAFFIC_THROW_INFO extends NetSDKLib.SdkStructure {
    /**
     * 通道号
     */
    public int              nChannelID;
    /**
     * 事件名称
     */
    public byte[]           szName = new byte[NET_EVENT_NAME_LEN];
    /**
     * 字节对齐
     */
    public byte[]           bReserved1 = new byte[8];
    /**
     * 时间戳(单位是毫秒)
     */
    public int              PTS;
    /**
     * 事件发生的时间
     */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
    /**
     * 事件ID
     */
    public int              nEventID;
    /**
     * 事件对应文件信息
     */
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo = new NetSDKLib.NET_EVENT_FILE_INFO();
    /**
     * 对应图片的分辨率
     */
    public NetSDKLib.NET_RESOLUTION_INFO stuResolution = new NetSDKLib.NET_RESOLUTION_INFO();
    /**
     * 抓图标志(按位),0位:"*",1位:"Timing",2位:"Manual",3位:"Marked",4位:"Event",5位:"Mosaic",6位:"Cutout"
     */
    public int              dwSnapFlagMask;
    /**
     * 事件动作,0表示脉冲事件,1表示持续性事件开始,2表示持续性事件结束;
     */
    public byte             bEventAction;
    /**
     *
     */
    public byte[]           bReserved2 = new byte[2];
    /**
     * 图片的序号, 同一时间内(精确到秒)可能有多张图片, 从0开始
     */
    public byte             byImageIndex;
    /**
     * 对应车道号
     */
    public int              nLane;
    /**
     * 检测到的物体
     */
    public NetSDKLib.NET_MSG_OBJECT stuObject = new NetSDKLib.NET_MSG_OBJECT();
    /**
     * 智能事件公共信息
     */
    public NetSDKLib.EVENT_INTELLI_COMM_INFO stuIntelliCommInfo = new NetSDKLib.EVENT_INTELLI_COMM_INFO();
    /**
     * 交通车辆部分信息
     */
    public NetSDKLib.EVENT_TRAFFIC_CAR_PART_INFO stuTrafficCarPartInfo = new NetSDKLib.EVENT_TRAFFIC_CAR_PART_INFO();
    /**
     * GPS信息 
     */
    public NetSDKLib.NET_GPS_INFO stuGPSInfo = new NetSDKLib.NET_GPS_INFO();
    /**
     * 全景广角图,参见结构体定义 {@link NetSDKLib.SCENE_IMAGE_INFO}
    */
    public NetSDKLib.SCENE_IMAGE_INFO stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO();
    /**
     * 图片信息数组,参见结构体定义 {@link NET_IMAGE_INFO_EX3}
    */
    public Pointer          pstuImageInfo;
    /**
     * 图片信息个数
    */
    public int              nImageInfoNum;
    /**
     * 保留字节
     */
    public byte[]           bReserved = new byte[264-NetSDKLib.POINTERSIZE];
    /**
     * 公共信息
     */
    public NetSDKLib.EVENT_COMM_INFO stCommInfo = new NetSDKLib.EVENT_COMM_INFO();
}

