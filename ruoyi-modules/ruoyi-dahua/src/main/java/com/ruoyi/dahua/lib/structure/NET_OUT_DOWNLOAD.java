package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_DownloadByRecordFileEx3 接口输出参数
 * @origin autoTool
 * @date 2023/11/30 19:38:56
 */
public class NET_OUT_DOWNLOAD extends NetSDKLib.SdkStructure {
	/**
	 * / 结构体大小：赋值为结构体大小
	 */
    public int              dwSize;

	public NET_OUT_DOWNLOAD() {
		this.dwSize = this.size();
	}
}

