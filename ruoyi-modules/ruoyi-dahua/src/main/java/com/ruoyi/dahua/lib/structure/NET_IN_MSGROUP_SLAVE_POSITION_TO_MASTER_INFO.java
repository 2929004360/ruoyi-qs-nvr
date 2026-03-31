package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description EM_MSGROUP_OPERATE_SLAVE_POSITION_TO_MASTER 入参
 * @date 2022/09/14 14:09:08
 */
public class NET_IN_MSGROUP_SLAVE_POSITION_TO_MASTER_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 主从跟踪组句柄, 从 EM_MSGROUP_OPERATE_OPEN 操作获取
	 */
    public int              dwToken;
	/**
	 * 从机PTZ位置，PT为真实值扩大10倍表示，Z为真实倍率扩大10倍表示
	 */
    public int[]            nPTZ = new int[3];
	/**
	 * 物体在从机视频上的位置, 8192坐标系
	 */
    public NetSDKLib.NET_RECT stuRect = new NetSDKLib.NET_RECT();

	public NET_IN_MSGROUP_SLAVE_POSITION_TO_MASTER_INFO() {
		this.dwSize = this.size();
	}
}

