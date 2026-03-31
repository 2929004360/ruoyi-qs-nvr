package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 事件类型 EVENT_IVS_DUSTBIN_DETECTION (垃圾桶检测事件)对应的数据块描述信息
*/
public class DEV_EVENT_DUSTBIN_DETECTION_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 0:脉冲,1:开始, 2:停止
    */
    public int              nAction;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 智能事件所属大类
    */
    public byte[]           szClass = new byte[16];
    /**
     * GroupID事件组ID，同一物体抓拍过程内GroupID相同
    */
    public int              nGroupID;
    /**
     * CountInGroup一个事件组内的抓拍张数
    */
    public int              nCountInGroup;
    /**
     * IndexInGroup一个事件组内的抓拍序号，从1开始
    */
    public int              nIndexInGroup;
    /**
     * 事件时间毫秒数
    */
    public int              nUTCMS;
    /**
     * 相对事件时间戳,(单位是毫秒)
    */
    public double           dbPTS;
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件ID
    */
    public int              nEventID;
    /**
     * 检测到的物体信息个数
    */
    public int              nObjectNum;
    /**
     * 表示检测到的物体信息,参见结构体定义 {@link NetSDKLib.NET_MSG_OBJECT_EX2}
    */
    public NetSDKLib.NET_MSG_OBJECT_EX2[] stuObjects = new NetSDKLib.NET_MSG_OBJECT_EX2[64];
    /**
     * 检测区,参见结构体定义 {@link NetSDKLib.NET_POINT}
    */
    public NetSDKLib.NET_POINT[] stuDetectRegion = new NetSDKLib.NET_POINT[20];
    /**
     * 检测区个数
    */
    public int              nDetectRegionNum;
    /**
     * 事件触发的预置点号，从1开始没有该字段，表示预置点未知
    */
    public int              nPresetID;
    /**
     * 事件触发的预置点名称
    */
    public byte[]           szPresetName = new byte[64];
    /**
     * 违法持续时长，单位：秒
    */
    public int              nViolationDuration;
    /**
     * 事件关联ID 格式：类型+时间+序列号，其中类型2位，时间14位，序列号5位
    */
    public byte[]           szSourceID = new byte[32];
    /**
     * 图片信息数组,参见结构体定义 {@link NET_IMAGE_INFO_EX2}
    */
    public Pointer          pstuImageInfo;
    /**
     * 图片信息个数
    */
    public int              nImageInfoNum;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1024-NetSDKLib.POINTERSIZE];

    public DEV_EVENT_DUSTBIN_DETECTION_INFO() {
        for(int i = 0; i < stuObjects.length; i++){
            stuObjects[i] = new NetSDKLib.NET_MSG_OBJECT_EX2();
        }
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NetSDKLib.NET_POINT();
        }
    }
}

