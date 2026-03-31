package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 禁止布防的条件，当其中一个或多个状态且对应项的值为true时，报警主机应能禁止布防
 * @date 2023/03/15 21:47:18
 */
public class NET_PREVENTION_OF_ARMING_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 入侵探测器处于激活状态
	 */
    public int              bIntrusionDetectorActivated;
	/**
	 * 紧急报警装置处于激活状态
	 */
    public int              bHoldupDeviceActivated;
	/**
	 * 移动目标探测器被遮挡
	 */
    public int              bMovementDetectorMasked;
	/**
	 * 移动目标探测器的探测距离明显减小
	 */
    public int              bMovementDetectorRangeReduction;
	/**
	 * 入侵探测器故障
	 */
    public int              bIntrusionDetectorFault;
	/**
	 * 防拆报警
	 */
    public int              bTamperCondition;
	/**
	 * 互连通信故障
	 */
    public int              bInterconnectionFaults;
	/**
	 * 主电源故障
	 */
    public int              bPrimePowerSourceFault;
	/**
	 * 备用电源故障
	 */
    public int              bAlternativePowerSourceFault;
	/**
	 * 报警传输系统故障
	 */
    public int              bAlarmTransmissionSystemFault;
	/**
	 * 告警装置故障
	 */
    public int              bWarningDeviceFault;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[252];

	public NET_PREVENTION_OF_ARMING_INFO() {
	}
}

