package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GetElevatorWorkInfo 接口入参
 * @date 2022/08/11 19:21:09
 */
public class NET_IN_GET_ELEVATOR_WORK_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 视频通道号
	 */
    public int              nChannel;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[256];

	public NET_IN_GET_ELEVATOR_WORK_INFO() {
		this.dwSize = this.size();
	}
}

