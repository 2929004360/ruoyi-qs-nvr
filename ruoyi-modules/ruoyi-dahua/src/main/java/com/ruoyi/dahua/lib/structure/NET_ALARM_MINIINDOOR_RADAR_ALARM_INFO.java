package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description Mini雷达报警事件(对应 DH_ALARM_MINIINDOOR_RADAR_ALARM)
 * @date 2022/08/04 10:13:30
 */
public class NET_ALARM_MINIINDOOR_RADAR_ALARM_INFO extends NetSDKLib.SdkStructure {
    /**
     * 通道号
     */
    public int              nChannelID;
    /**
     * 事件动作, 1:开始 2:停止
     */
    public int              nAction;
    /**
     * 事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     * 事件所属大类
     */
    public byte[]           szClass = new byte[128];
    /**
     * 事件编号，用来唯一标志一个事件
     */
    public int              nEventID;
    /**
     * 事件发生的时间,单位秒
     */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
    /**
     * 时间戳(单位是毫秒)
     */
    public double           dbPTS;
    /**
     * UTC时间对应的毫秒数
     */
    public int              nUTCMS;
    /**
     * 呼吸异常(生命体征雷达使用)
     */
    public int              bBreatheAbnormal;
    /**
     * 心跳异常(生命体征雷达使用)
     */
    public int              bHeartBeatAbnormal;
    /**
     * 在离床异常(生命体征雷达使用)
     */
    public int              bAFBStatusAbnormal;
    /**
     * 跌倒报警(室内雷达)
     */
    public int              bFallAlarm;
    /**
     * 人数异常(室内雷达)
     */
    public int              bNumAbnormal;
    /**
     * 保留字节
     */
    public byte[]           szReserved = new byte[1024];

    public NET_ALARM_MINIINDOOR_RADAR_ALARM_INFO() {
    }
}

