package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_ManualTest 接口输出参数
 * @date 2023/03/16 08:53:12
 */
public class NET_OUT_MANUAL_TEST extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 结果 1：测试成功，结果同步返回 2：发送测试请求成功，结果异步返回 3：测试失败
	 */
    public int              nTestResult;

	public NET_OUT_MANUAL_TEST() {
		this.dwSize = this.size();
	}
}

