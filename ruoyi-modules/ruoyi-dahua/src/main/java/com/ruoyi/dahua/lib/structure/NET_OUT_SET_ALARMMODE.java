package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description 设置布防模式
 *              输出参数。此时CLIENT_SetAlarmRegionInfo的emType参数为NET_EM_SET_ALARMREGION_INFO_ARMMODE
 * @date 2023/03/15 16:26:00
 */
public class NET_OUT_SET_ALARMMODE extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 布防结果 0:成功 1:失败
	 */
    public int              nArmResult;
	/**
	 * 布防失败的区域个数
	 */
    public int              nFailedAreaRet;
	/**
	 * 布防失败的细节
	 */
    public ARM_FAILED_DETAIL[] stuFailedDetail = new ARM_FAILED_DETAIL[8];
	/**
	 * 布防失败个数, 最大值为64
	 */
    public int              nFailedDetailNum;
	/**
	 * 布防失败的细节扩展字段 布防个数大于8个使用此字段,指针对应结构体{ @link ARM_FAILED_DETAIL_EX}数组
	 */
    public Pointer          pstuFailedDetailEx;
	/**
	 * 异常设备信息个数
	 */
    public int              nDeviceFaultNum;
	/**
	 * 设备异常信息，最大支持256个
	 */
    public NET_DEVICE_FAULT_INFO[] stuDeviceFault = new NET_DEVICE_FAULT_INFO[256];

	public NET_OUT_SET_ALARMMODE() {
		for (int i = 0; i < stuFailedDetail.length; i++) {
			stuFailedDetail[i] = new ARM_FAILED_DETAIL();
		}
		for (int i = 0; i < stuDeviceFault.length; i++) {
			stuDeviceFault[i] = new NET_DEVICE_FAULT_INFO();
		}
		this.dwSize = this.size();
	}
}

