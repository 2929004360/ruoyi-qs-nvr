package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 遥测数据信息
 * @date 2022/12/13 10:24:15
 */
public class DEV_SCADA_DATA_INFO extends NetSDKLib.SdkStructure {
    /**
     * 点位ID
     */
    public byte[]           szID = new byte[32];
    /**
     * 遥测数据采集时间，utc
     */
    public NET_TIME_EX      stuTime = new NET_TIME_EX();
    /**
     * 点位状态，0：无告警，1：一级告警, -1为未收到报警状态
     */
    public int              nStatus;
    /**
     * 点位类型，YC：遥测
     */
    public byte[]           szType = new byte[8];
    /**
     * 遥测数据单位，如m/s，℃
     */
    public byte[]           szUnit = new byte[8];
    /**
     * 遥调值
     */
    public float            fSetupVal;
    /**
     * 点位遥测值
     */
    public float            fMeasuredVal;
    /**
     * 电力104点号，当启用电力104协议时存在
     */
    public byte[]           szAddress = new byte[16];
    /**
     * 保留字节
     */
    public byte[]           szResvered = new byte[256];

    public DEV_SCADA_DATA_INFO() {
    }
}

