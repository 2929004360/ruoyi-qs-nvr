package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 获取报警能力集
 * @date 2023/04/18 10:57:31
 */
public class CFG_CAP_ALARM_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 设备是否支持PIR(人体热式感应器)报警,外部报警的一种
	 */
    public int              bAlarmPir;
	/**
	 * 设备是否支持补光灯
	 */
    public int              bFlashLight;
	/**
	 * 防区类型种类数,为0表示无此字段时报警输入不作为防区使用。
	 */
    public int              nDefenceAreaTypeNum;
	/** 
	 * 支持防区类型，参考枚举{ @link EM_CFG_DEFENCEAREATYPE}
	 */
    public int[]            emDefenceAreaType = new int[8];
	/**
	 * 延时防区的最大延时时间,单位为秒，只有支持延时防区时此字段才有效。
	 */
    public int              nMaxDelay;
	/**
	 * 报警通道数,为了保持兼容性,此为0时表示每个通道支持所有类型的传感器
	 */
    public int              nAlarmChannelNum;
	/**
	 * 报警通道集合
	 */
    public CFG_ALARM_SENSE_METHOD[] stuAlarmChannel = new CFG_ALARM_SENSE_METHOD[32];
	/**
	 * 警号个数
	 */
    public int              nAlarmBellCount;
	/**
	 * 最大备用报警中心数,无此字段时默认为0,0表示不支持备用报警中心
	 */
    public int              nMaxBackupAlarmServer;
	/**
	 * 最大电话报警中心数, 无此字段时默认为0,0表示不支持电话报警中心。
	 */
    public int              nMaxPSTNAlarmServer;
	/**
	 * 报警设备是否支持AlarmRegion组件。如果没有该字段或者该字段为false，客户端使用已有报警组件（alarm、alarmSubregion）操作设备；如果该字段为true，客户端使用新报警组件操作设备
	 */
    public int              bSupportAlarmRegion;
    /**
     * VTH报警输入传感器类型NO、NC正确使用，无这个能力或false的VTH表示NO、NC与协议相反使用
    */
    public int              bVTHSensorTypeCorrectly;
    /**
     * 保留字段
    */
    public byte[]           szResvered = new byte[1024];

	public CFG_CAP_ALARM_INFO() {
		for (int i = 0; i < stuAlarmChannel.length; i++) {
			stuAlarmChannel[i] = new CFG_ALARM_SENSE_METHOD();
		}
	}
}

