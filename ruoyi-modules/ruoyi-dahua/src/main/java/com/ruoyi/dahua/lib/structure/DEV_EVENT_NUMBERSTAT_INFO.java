package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description 事件类型EVENT_IVS_NUMBERSTAT(数量统计事件)对应的数据块描述信息
 * @date 2022/07/04 10:11:30
 */
public class DEV_EVENT_NUMBERSTAT_INFO extends NetSDKLib.SdkStructure {
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
    public byte[]           bReserved2 = new byte[4];
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
     * 区域内物体的个数
     */
    public int              nNumber;
    /**
     * 设置的上限
     */
    public int              nUpperLimit;
    /**
     * 事件对应文件信息
     */
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo = new NetSDKLib.NET_EVENT_FILE_INFO();
    /**
     * 事件动作,0表示脉冲事件,1表示持续性事件开始,2表示持续性事件结束;
     */
    public byte             bEventAction;
    /**
     * 字节对齐
     */
    public byte[]           bReserved1 = new byte[2];
    /**
     * 图片的序号, 同一时间内(精确到秒)可能有多张图片, 从0开始
     */
    public byte             byImageIndex;
    /**
     * 表示进入区域或者出入口的内物体的个数
     */
    public int              nEnteredNumber;
    /**
     * 表示出来区域或者出入口的内物体的个数
     */
    public int              nExitedNumber;
    /**
     * 抓图标志(按位),具体见NET_RESERVED_COMMON
     */
    public int              dwSnapFlagMask;
    /**
     * 事件触发累计次数
     */
    public int              nOccurrenceCount;
    /**
     * 智能事件公共信息
     */
    public NetSDKLib.EVENT_INTELLI_COMM_INFO stuIntelliCommInfo = new NetSDKLib.EVENT_INTELLI_COMM_INFO();
    /**
     * 区域ID，一个预置点可以有多个区域ID
     */
    public int              nAreaID;
    /**
     * 该场景下是否合规
     */
    public int              bIsCompliant;
    /**
     * 表示人数越上限类型 {@link com.ruoyi.dahua.lib.enumeration.EM_NUMBER_STAT_TYPE}
     */
    public int              emType;
    /**
     * 图片信息数组
     */
    public Pointer          pstuImageInfo;
    /**
     * 图片信息个数
     */
    public int              nImageInfoNum;
    /**
     * 经过区域物体的个数
     */
    public int              nPassedNumber;
    /**
     * 事件公共扩展字段结构体
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 保留字节,留待扩展.
     */
    public byte[]           bReserved = new byte[800];

    public DEV_EVENT_NUMBERSTAT_INFO() {
    }
}

