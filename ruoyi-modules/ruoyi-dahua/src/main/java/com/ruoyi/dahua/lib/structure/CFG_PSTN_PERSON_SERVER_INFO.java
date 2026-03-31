package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 单个个人电话接机配置信息
 * @date 2023/04/18 10:54:34
 */
public class CFG_PSTN_PERSON_SERVER_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 个人电话名称
	 */
    public byte[]           szName = new byte[128];
	/**
	 * 个人电话接收机号码
	 */
    public byte[]           szNumber = new byte[32];

	public CFG_PSTN_PERSON_SERVER_INFO() {
	}
}

