package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_DeleteWorkSuitBySourceUID 接口输出参数
 * @date 2022/10/08 20:13:32
 */
public class NET_OUT_DELETE_WORKSUIT_BY_SOURCEUID extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 错误码个数
	 */
    public int              nErrCodeNum;
	/**
	 * 错误码信息,参考枚举{ @link EM_DELETE_BY_SOURCEUID_ERRCODE }
	 */
    public int[]            emErrCode = new int[64];

	public NET_OUT_DELETE_WORKSUIT_BY_SOURCEUID() {
		this.dwSize = this.size();
	}
}

