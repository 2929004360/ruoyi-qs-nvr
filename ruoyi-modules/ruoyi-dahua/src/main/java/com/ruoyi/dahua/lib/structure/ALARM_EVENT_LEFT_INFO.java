package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 物品遗留事件(对应事件 DH_EVENT_LEFT_DETECTION)
 * @origin autoTool
 * @date 2023/09/22 16:17:45
 */
public class ALARM_EVENT_LEFT_INFO extends NetSDKLib.SdkStructure {
    public int              dwSize;
    /**
     * / 通道号
     */
    public int              nChannelID;
    /**
     * / 时间戳(单位是毫秒)
     */
    public double           PTS;
    /**
     * / 事件发生的时间
     */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
    /**
     * / 事件ID
     */
    public int              nEventID;
    /**
     * / 事件动作,0表示脉冲事件,1表示持续性事件开始,2表示持续性事件结束;
     */
    public int              nEventAction;
    /**
     * / 规则被触发生次数
     */
    public int              nOccurrenceCount;
    /**
     * / 事件级别,GB30147需求项
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

    public ALARM_EVENT_LEFT_INFO() {
        this.dwSize = this.size();
    }
}

