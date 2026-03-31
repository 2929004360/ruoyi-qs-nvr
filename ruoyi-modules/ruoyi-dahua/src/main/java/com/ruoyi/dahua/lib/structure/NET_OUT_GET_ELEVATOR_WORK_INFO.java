package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GetElevatorWorkInfo 接口出参
 * @date 2022/08/11 19:21:29
 */
public class NET_OUT_GET_ELEVATOR_WORK_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 电梯运行状态, 0 电梯停止, 1 电梯上行, 2 电梯下行
	 */
    public int              nElevatorWorkState;
	/**
	 * 电梯所在的楼层
	 */
    public byte[]           szElevatorFloor = new byte[32];
	/**
	 * 电梯运行速度, 单位mm/s
	 */
    public int              nElevatorWorkSpeed;
	/**
	 * 基准楼层是否已校准(梯控传感器重启需要重新校准)
	 */
    public int              bIsCheckFloor;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[256];

	public NET_OUT_GET_ELEVATOR_WORK_INFO() {
		this.dwSize = this.size();
	}
}

