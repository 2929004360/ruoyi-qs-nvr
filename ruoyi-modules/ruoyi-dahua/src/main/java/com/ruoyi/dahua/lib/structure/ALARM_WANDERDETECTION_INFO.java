package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * @author 421657
 * @description 徘徊事件(对应事件DH_ALARM_WANDERDETECTION)
 * @origin autoTool
 * @date 2023/09/22 16:32:04
 */
public class ALARM_WANDERDETECTION_INFO extends NetSDKLib.SdkStructure {
    /**
     * / 事件动作,0表示脉冲事件,1表示持续性事件开始,2表示持续性事件结束;
     */
    public int              nAction;
    /**
     * / 通道号
     */
    public int              nChannelID;
    /**
     * / 时间戳(单位是毫秒)
     */
    public double           dbPTS;
    /**
     * / 事件发生的时间
     */
    public NET_TIME_EX      stuTime = new NET_TIME_EX();
    /**
     * / 事件ID
     */
    public int              nEventID;
    /**
     * / 规则被触发的次数
     */
    public int              nCount;
    /**
     * / 事件级别,GB31047需求
     */
    public int              nLevel;
    /**
     * / 事件触发的预置点号，从1开始（没有表示未知）
     */
    public short            nPreserID;
    /**
     * / 事件触发的预置名称
     */
    public byte[]           szPresetName = new byte[64];
    /**
     * / 事件公共扩展字段结构体
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * /保留字节
     */
    public byte[]           byReserved = new byte[958];

    public ALARM_WANDERDETECTION_INFO() {
    }
}

