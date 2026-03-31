package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型EVENT_IVS_CROSSFENCEDETECTION(翻越围栏事件)对应的数据块描述信息
*/
public class DEV_EVENT_CROSSFENCEDETECTION_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 字节对齐
    */
    public byte[]           bReserved1 = new byte[4];
    /**
     * 时间戳(单位是毫秒)
    */
    public double           PTS;
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX UTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件ID
    */
    public int              nEventID;
    /**
     * 检测到的物体,参见结构体定义 {@link NetSDKLib.NET_MSG_OBJECT}
    */
    public NetSDKLib.NET_MSG_OBJECT stuObject = new NetSDKLib.NET_MSG_OBJECT();
    /**
     * 围栏上边线顶点数
    */
    public int              nUpstairsLinePointNumber;
    /**
     * 围栏上边线信息,参见结构体定义 {@link NetSDKLib.NET_POINT}
    */
    public NetSDKLib.NET_POINT[] stuUpstairsLine = new NetSDKLib.NET_POINT[20];
    /**
     * 围栏下边线顶点数
    */
    public int              nDownstairsLinePointNumber;
    /**
     * 围栏下边线信息,参见结构体定义 {@link NetSDKLib.NET_POINT}
    */
    public NetSDKLib.NET_POINT[] stuDownstairsLine = new NetSDKLib.NET_POINT[20];
    /**
     * 物体运动轨迹顶点数
    */
    public int              nTrackLineNum;
    /**
     * 物体运动轨迹,参见结构体定义 {@link NetSDKLib.NET_POINT}
    */
    public NetSDKLib.NET_POINT[] TrackLine = new NetSDKLib.NET_POINT[20];
    /**
     * 事件对应文件信息,参见结构体定义 {@link NetSDKLib.NET_EVENT_FILE_INFO}
    */
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo = new NetSDKLib.NET_EVENT_FILE_INFO();
    /**
     * 事件动作,0表示脉冲事件,1表示持续性事件开始,2表示持续性事件结束;
    */
    public byte             bEventAction;
    /**
     * 表示入侵方向, 0-由左至右, 1-由右至左
    */
    public byte             bDirection;
    public byte[]           byReserved = new byte[1];
    /**
     * 图片的序号, 同一时间内(精确到秒)可能有多张图片, 从0开始
    */
    public byte             byImageIndex;
    /**
     * 抓图标志(按位),具体见NET_RESERVED_COMMON
    */
    public int              dwSnapFlagMask;
    /**
     * 事件源设备上的index,-1表示数据无效
    */
    public int              nSourceIndex;
    /**
     * 事件源设备唯一标识,字段不存在或者为空表示本地设备
    */
    public byte[]           szSourceDevice = new byte[260];
    /**
     * 事件触发累计次数
    */
    public int              nOccurrenceCount;
    /**
     * 智能事件公共信息,参见结构体定义 {@link NetSDKLib.EVENT_INTELLI_COMM_INFO}
    */
    public NetSDKLib.EVENT_INTELLI_COMM_INFO stuIntelliCommInfo = new NetSDKLib.EVENT_INTELLI_COMM_INFO();
    /**
     * 图片信息数组,参见结构体定义 {@link NET_IMAGE_INFO_EX2}
    */
    public NET_IMAGE_INFO_EX2[] stuImageInfo = new NET_IMAGE_INFO_EX2[32];
    /**
     * 图片信息个数
    */
    public int              nImageInfoNum;
    /**
     * 事件公共扩展字段结构体,参见结构体定义 {@link NET_EVENT_INFO_EXTEND}
    */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 保留字节,留待扩展.
    */
    public byte[]           bReserved = new byte[616];

    public DEV_EVENT_CROSSFENCEDETECTION_INFO() {
        for(int i = 0; i < stuUpstairsLine.length; i++){
            stuUpstairsLine[i] = new NetSDKLib.NET_POINT();
        }
        for(int i = 0; i < stuDownstairsLine.length; i++){
            stuDownstairsLine[i] = new NetSDKLib.NET_POINT();
        }
        for(int i = 0; i < TrackLine.length; i++){
            TrackLine[i] = new NetSDKLib.NET_POINT();
        }
        for(int i = 0; i < stuImageInfo.length; i++){
            stuImageInfo[i] = new NET_IMAGE_INFO_EX2();
        }
    }
}

