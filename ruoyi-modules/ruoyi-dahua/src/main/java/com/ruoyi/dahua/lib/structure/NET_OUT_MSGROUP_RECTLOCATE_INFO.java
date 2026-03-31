package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 主动跟踪主操作命令, EM_MSGROUP_OPERATE_RECTLOCATE 出参
 * @date 2022/09/14 14:06:16
 */
public class NET_OUT_MSGROUP_RECTLOCATE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 联动到球机上的绝对坐标
	 */
    public int[]            nPTZ = new int[3];

	public NET_OUT_MSGROUP_RECTLOCATE_INFO() {
		this.dwSize = this.size();
	}
}

