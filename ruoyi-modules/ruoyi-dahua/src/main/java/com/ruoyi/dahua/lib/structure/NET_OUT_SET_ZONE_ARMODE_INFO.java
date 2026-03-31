package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description CLIENT_SetZoneArmMode 接口出参, 仅接口失败时下列参数有效
 * @origin autoTool
 * @date 2023/08/10 09:52:29
 */
public class NET_OUT_SET_ZONE_ARMODE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 此结构体大小,必须赋值
	 */
    public int              dwSize;
	/**
	 * 布防结果,0: 成功,1: 失败
	 */
    public int              nArmResult;
	/**
	 * 失败详情数组有效个数
	 */
    public int              nAreaAbnormalNum;
	/**
	 * 设备异常信息数组有效个数
	 */
    public int              nDeviceFaultNum;
	/**
	 * 失败详情实际返回有效个数
	 */
    public int              nAreaAbnormalRetNum;
	/**
	 * 错误码
	 */
    public int              nCode;
	/**
	 * 失败详情，需由用户申请，申请内存大小为sizeof(NET_AREA_ABNORMAL_INFO)*nAreaAbnormalNum, refer to
	 * {@link NET_AREA_ABNORMAL_INFO}
	 */
    public Pointer          pstuAreaAbnormal;
	/**
	 * 设备异常信息
	 */
    public NET_ZONE_ARM_MODE_DEVICE_FAULT_INFO[] stuDeviceFault = new NET_ZONE_ARM_MODE_DEVICE_FAULT_INFO[64];
	/**
	 * 错误说明
	 */
    public byte[]           szMessage = new byte[256];

	public NET_OUT_SET_ZONE_ARMODE_INFO() {
		for (int i = 0; i < stuDeviceFault.length; i++) {
			stuDeviceFault[i] = new NET_ZONE_ARM_MODE_DEVICE_FAULT_INFO();
		}
		this.dwSize = this.size();
	}
}

