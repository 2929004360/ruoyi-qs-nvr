package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description CLIENT_GetDeviceInfoEx 接口输出参数
 * @date 2023/04/19 16:17:09
 */
public class NET_OUT_GET_DEVICE_INFO_EX extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 用户申请的设备个数
	 */
    public int              nMaxCount;
	/**
	 * 实际返回的设备个数
	 */
    public int              nRetCount;
	/**
	 * 设备信息列表, 由用户申请和释放内存,申请大小为sizeof(NET_GET_DEVICE_INFO_EX)*nMaxCount，
	 * 结构体参考{@link NET_GET_DEVICE_INFO_EX}数组
	 */
    public Pointer          pstuDeviceInfo;

	public NET_OUT_GET_DEVICE_INFO_EX() {
		this.dwSize = this.size();
	}
}

