package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 个人电话接机配置信息
 * @date 2023/04/18 10:54:34
 */
public class CFG_PSTN_PERSON_SERVER_INFO_ALL extends NetSDKLib.SdkStructure {
	/**
	 * 个人电话接机配置使能
	 */
    public int              bEnable;
	/**
	 * 有效的个人电话接机配置信息个数,目前最多支持3个
	 */
    public int              nServerCount;
	/**
	 * 个人电话接机配置信息数组,每个元素对应一个个人电话配置
	 * 最多支持多少个人电话,通过获取报警能力集(CLIENT_QueryNewSystemInfo对应宏CFG_CAP_ALARM) 获取有效电话个数
	 */
    public CFG_PSTN_PERSON_SERVER_INFO[] stuPSTNPersonServer = new CFG_PSTN_PERSON_SERVER_INFO[8];

	public CFG_PSTN_PERSON_SERVER_INFO_ALL() {
		for (int i = 0; i < stuPSTNPersonServer.length; i++) {
			stuPSTNPersonServer[i] = new CFG_PSTN_PERSON_SERVER_INFO();
		}
	}
}

