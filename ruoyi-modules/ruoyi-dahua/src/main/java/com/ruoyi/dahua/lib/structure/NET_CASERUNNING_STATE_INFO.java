package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 智能机箱的日常运行信息
 * @date 2023/05/11 16:03:12
 */
public class NET_CASERUNNING_STATE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 输入电压状态
	 */
    public NET_STATEMANAGER_V_INFO stuInV = new NET_STATEMANAGER_V_INFO();
	/**
	 * 输出电压状态
	 */
    public NET_CASERUNNING_OUTV_INFO stuOutV = new NET_CASERUNNING_OUTV_INFO();
	/**
	 * AC220V1输出的电压、电流、功率、能耗
	 */
    public NET_STATEMANAGER_V_INFO stuAc220v1Value = new NET_STATEMANAGER_V_INFO();
	/**
	 * AC220V2输出的电压、电流、功率、能耗
	 */
    public NET_STATEMANAGER_V_INFO stuAc220v2Value = new NET_STATEMANAGER_V_INFO();
	/**
	 * AC220V3输出的电压、电流、功率、能耗
	 */
    public NET_STATEMANAGER_V_INFO stuAc220v3Value = new NET_STATEMANAGER_V_INFO();
	/**
	 * 字节对齐
	 */
    public byte[]           szReserved1 = new byte[4];
	/**
	 * POE输出功率数量
	 */
    public int              nPoewNum;
	/**
	 * POE输出功率
	 */
    public byte[]           szPoe_w = new byte[4 * 32];
	/**
	 * 水浸状态，00异常01正常
	 */
    public byte[]           szWater = new byte[32];
	/**
	 * 是否有IPC相机，00 无 01 有
	 */
    public byte[]           szHasIPC = new byte[32];
	/**
	 * 是否有4G，00 无 01 有
	 */
    public byte[]           szHasFourG = new byte[32];
	/**
	 * SIM卡iccid
	 */
    public byte[]           szIccid = new byte[32];
	/**
	 * 照明状态，00关 01开
	 */
    public byte[]           szLight = new byte[32];
	/**
	 * 温度
	 */
    public byte[]           szTemp = new byte[32];
	/**
	 * 湿度
	 */
    public byte[]           szHumidity = new byte[32];
	/**
	 * 经度
	 */
    public byte[]           szLon = new byte[32];
	/**
	 * 维度
	 */
    public byte[]           szLat = new byte[32];
	/**
	 * 软件版本
	 */
    public byte[]           szRom = new byte[32];
	/**
	 * 单片机版本
	 */
    public byte[]           szScm = new byte[32];
	/**
	 * 重合闸
	 */
    public byte[]           szReclosing = new byte[32];
	/**
	 * 是否有重合闸，00 无 01 有
	 */
    public byte[]           szHasReclosing = new byte[32];
	/**
	 * 设备IP
	 */
    public byte[]           szBordIp = new byte[32];
	/**
	 * 设备名称
	 */
    public byte[]           szDevName = new byte[32];
	/**
	 * tf卡状态
	 */
    public NET_CASERUNNING_TF_INFO stuTf = new NET_CASERUNNING_TF_INFO();
	/**
	 * 智慧空开状态信息数量
	 */
    public int              nAirSwitchNum;
	/**
	 * 动环（串口）状态数量
	 */
    public int              nCommNum;
	/**
	 * 智慧空开状态信息
	 */
    public NET_CASERUNNING_AIRSWITCH_INFO[] stuAirSwitch = new NET_CASERUNNING_AIRSWITCH_INFO[16];
	/**
	 * 动环（串口）状态
	 */
    public NET_CASERUNNING_COMM_INFO[] stuComm = new NET_CASERUNNING_COMM_INFO[16];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[1024];

	public NET_CASERUNNING_STATE_INFO() {
		for (int i = 0; i < stuAirSwitch.length; i++) {
			stuAirSwitch[i] = new NET_CASERUNNING_AIRSWITCH_INFO();
		}
		for (int i = 0; i < stuComm.length; i++) {
			stuComm[i] = new NET_CASERUNNING_COMM_INFO();
		}
	}
}

