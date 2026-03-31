package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 联动主从跟踪组出参
 * @date 2022/09/14 13:56:26
 */
public class NET_OUT_MSGROUP_LOCATE_INTO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 联动到球机上的绝对坐标
	 */
    public short[]          snPTZ = new short[3];

	public NET_OUT_MSGROUP_LOCATE_INTO() {
		this.dwSize = this.size();
	}
}

