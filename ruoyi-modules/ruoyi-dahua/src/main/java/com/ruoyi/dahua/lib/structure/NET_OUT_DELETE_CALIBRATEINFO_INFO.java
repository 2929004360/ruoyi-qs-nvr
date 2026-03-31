package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 删除标定点信息出参
 * @date 2023/05/24 10:24:52
 */
public class NET_OUT_DELETE_CALIBRATEINFO_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;

	public NET_OUT_DELETE_CALIBRATEINFO_INFO() {
		this.dwSize = this.size();
	}
}

