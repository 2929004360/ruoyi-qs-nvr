package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description EM_MSGROUP_OPERATE_SLAVE_POSITION_TO_MASTER 出参
 * @date 2022/09/14 14:09:33
 */
public class NET_OUT_MSGROUP_SLAVE_POSITION_TO_MASTER_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 从机画面上的物体坐标在主机画面上的位置，8192坐标系
	 */
    public NetSDKLib.NET_RECT stuRect = new NetSDKLib.NET_RECT();

	public NET_OUT_MSGROUP_SLAVE_POSITION_TO_MASTER_INFO() {
		this.dwSize = this.size();
	}
}

