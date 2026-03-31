package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.NET_EVENT_FILE_INFO;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description 事件类型EVENT_IVS_ABNORMALRUNDETECTION(异常奔跑事件)对应的数据块描述信息
 * @date 2023/04/21 16:10:00
 */
public class DEV_EVENT_ABNORMALRUNDETECTION_INFO extends NetSDKLib.SdkStructure {
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
     * 事件发生的时间
     */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
    /**
     * 事件ID
     */
    public int              nEventID;
    /**
     * 检测到的物体
     */
    public NetSDKLib.DH_MSG_OBJECT stuObject = new NetSDKLib.DH_MSG_OBJECT();
    /**
     * 物体运动速度,km/h
     */
    public double           dbSpeed;
    /**
     * 触发速度,km/h
     */
    public double           dbTriggerSpeed;
    /**
     * 规则检测区域顶点数
     */
    public int              nDetectRegionNum;
    /**
     * 规则检测区域
     */
    public NetSDKLib.DH_POINT[] DetectRegion = new NetSDKLib.DH_POINT[20];
    /**
     * 物体运动轨迹顶点数
     */
    public int              nTrackLineNum;
    /**
     * 物体运动轨迹
     */
    public NetSDKLib.DH_POINT[] TrackLine = new NetSDKLib.DH_POINT[20];
    /**
     * 事件对应文件信息
     */
    public NET_EVENT_FILE_INFO stuFileInfo = new NET_EVENT_FILE_INFO();
    /**
     * 事件动作,0表示脉冲事件,1表示持续性事件开始,2表示持续性事件结束;
     */
    public byte             bEventAction;
    /**
     * 异常奔跑类型, 0-快速奔跑, 1-突然加速, 2-突然减速
     */
    public byte             bRunType;
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
     * 智能事件公共信息
     */
    public NetSDKLib.EVENT_INTELLI_COMM_INFO stuIntelliCommInfo = new NetSDKLib.EVENT_INTELLI_COMM_INFO();
    /**
     * 图片信息数组,{@link NET_IMAGE_INFO_EX2}
     */
    public Pointer          pstuImageInfo;
    /**
     * 图片信息个数
     */
    public int              nImageInfoNum;
    /**
     * 事件公共扩展字段结构体
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 字节对齐
    */
    public byte[]           szReserved3 = new byte[4];
    /**
     * 全景图,参见结构体定义 {@link NetSDKLib.SCENE_IMAGE_INFO_EX}
    */
    public Pointer          pstuSceneImage;
    /**
     * 保留字节,留待扩展.
     */
    public byte[]           bReserved = new byte[600-NetSDKLib.POINTERSIZE];

    public DEV_EVENT_ABNORMALRUNDETECTION_INFO() {
        for (int i = 0; i < DetectRegion.length; i++) {
            DetectRegion[i] = new NetSDKLib.DH_POINT();
        }
        for (int i = 0; i < TrackLine.length; i++) {
            TrackLine[i] = new NetSDKLib.DH_POINT();
        }
    }
}

