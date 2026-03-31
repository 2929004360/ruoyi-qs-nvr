package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_MultiAppendToWorkSuitCompareGroup 接口输出参数
 * @date 2022/10/08 19:50:19
 */
public class NET_OUT_MULTI_APPEND_TO_WORKSUIT_GROUP extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 工装合规导入返回的错误码个数
	 */
    public int              nErrCodeNum;
	/**
	 * 工装合规导入返回的错误码信息,参考枚举{ @link EM_WORKSUIT_APPEND_ERRCODE }
	 */
    public int[]            emErrCode = new int[1000];

	public NET_OUT_MULTI_APPEND_TO_WORKSUIT_GROUP() {
		this.dwSize = this.size();
	}
}

