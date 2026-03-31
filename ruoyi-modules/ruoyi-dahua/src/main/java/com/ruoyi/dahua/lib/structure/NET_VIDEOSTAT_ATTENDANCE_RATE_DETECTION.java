package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 到课率检测事件
*/
public class NET_VIDEOSTAT_ATTENDANCE_RATE_DETECTION extends NetSDKLib.SdkStructure
{
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 智能事件所属大类
    */
    public byte[]           szClass = new byte[16];
    /**
     * 相对事件时间戳,单位毫秒
    */
    public double           dbPTS;
    /**
     * 事件发生时间，带时区偏差的UTC时间，单位秒,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件编号，用来唯一标志一个事件
    */
    public int              nEventID;
    /**
     * 事件触发的预置点号，从1开始没有该字段，表示预置点未知
    */
    public int              nPresetID;
    /**
     * 应到总人数
    */
    public int              nTotalCount;
    /**
     * 检测到的总人数
    */
    public int              nHumanCount;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_VIDEOSTAT_ATTENDANCE_RATE_DETECTION() {
    }
}

