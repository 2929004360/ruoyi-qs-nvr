package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_ManualTest 接口输入参数
 * @date 2023/03/16 08:53:00
 */
public class NET_IN_MANUAL_TEST extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 测试ID，每次下发的测试ID不一样
	 */
    public int              dwTestID;
	/**
	 * 测试的通道号，可以对应某一个要测试的SIA服务器
	 */
    public int              nChannel;

	public NET_IN_MANUAL_TEST() {
		this.dwSize = this.size();
	}
}

