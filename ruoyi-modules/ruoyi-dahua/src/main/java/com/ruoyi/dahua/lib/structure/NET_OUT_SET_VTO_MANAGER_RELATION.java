package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_SetVTOManagerRelation 出参
 * @origin autoTool
 * @date 2023/10/19 14:16:46
 */
public class NET_OUT_SET_VTO_MANAGER_RELATION extends NetSDKLib.SdkStructure {
	/**
	 * / 结构体大小
	 */
    public int              dwSize;
	/**
	 * / 接口返回结果为0时成功
	 */
    public int              nErrorCode;

	public NET_OUT_SET_VTO_MANAGER_RELATION() {
		this.dwSize = this.size();
	}
}

