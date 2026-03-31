package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 手动选定目标跟踪入参
 * @date 2022/09/14 14:01:47
 */
public class NET_IN_MSGROUP_TRACK_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 主从跟踪组句柄
	 */
    public int              dwToken;
	/**
	 * 算法方案类型
	 */
    public byte[]           szClass = new byte[16];
	/**
	 * 算法目标ID
	 */
    public int              dwObjectID;

	public NET_IN_MSGROUP_TRACK_INFO() {
		this.dwSize = this.size();
	}
}

