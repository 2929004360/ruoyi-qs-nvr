package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description mini雷达报警点信息
 * @date 2022/08/04 17:03:45
 */
public class NET_MINI_RADAR_ALARMPOINT_INFO extends NetSDKLib.SdkStructure {
    /**
     * 心率值(生命体征雷达使用)
     */
    public int              nHeartRate;
    /**
     * 呼吸值(生命体征雷达使用)
     */
    public int              nBreathe;
    /**
     * 起身状态(生命体征雷达使用)
     */
    public int              bGetUpStatus;
    /**
     * 离床状态(生命体征雷达使用)
     */
    public int              bAFBStatus;
    /**
     * 翻身状态(生命体征雷达使用)
     */
    public int              bTurnOverStatus;
    /**
     * 睡眠质量(生命体征雷达使用)
     */
    public int              nSleepQuality;
    /**
     * 摔倒状态(室内雷达)
     */
    public int              bPosture;
    /**
     * 人数(室内雷达)
     */
    public int              nNum;
    /**
     * 目标ID，0-31，室内雷达使用
     */
    public int              nId;
    /**
     * 位置信息x个数
     */
    public int              nXPosNum;
    /**
     * 位置信息x，单位米。扩大100倍，最多32人
     */
    public int[]            nXPos = new int[32];
    /**
     * 位置信息y个数
     */
    public int              nYPosNum;
    /**
     * 位置信息y，单位米。扩大100倍，最多32人
     */
    public int[]            nYPos = new int[32];
    /**
     * 速度个数
     */
    public int              nSpeedNum;
    /**
     * 速度 m/s，100倍，最多32人
     */
    public int[]            nSpeed = new int[32];
    /**
     * 人员类型个数
     */
    public int              nTypeNum;
    /**
     * 人员类型：大人、小孩
     */
    public int[]            nType = new int[32];
    /**
     * 广告机专用；1-5，最多支持5个等级
     */
    public int              nHeartRateLevel;
    /**
     * 广告机专用；人与广告机的距离，单位米，扩大100倍
     */
    public int              nDistance;
    /**
     * 保留字节
     */
    public byte[]           szReserved = new byte[256];

    public NET_MINI_RADAR_ALARMPOINT_INFO() {
    }
}

