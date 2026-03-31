package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 网络存储服务器配置, 包含多个服务器
 * @date 2022/09/08 19:33:11
 */
public class CFG_NAS_GROUP_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 存储服务器数量
	 */
    public int              nNasNum;
	/**
	 * 存储服务器数组
	 */
    public CFG_NAS_INFO_NEW[] stuNasInfo = new CFG_NAS_INFO_NEW[16];

	public CFG_NAS_GROUP_INFO() {
		for (int i = 0; i < stuNasInfo.length; i++) {
			stuNasInfo[i] = new CFG_NAS_INFO_NEW();
		}
	}
}

