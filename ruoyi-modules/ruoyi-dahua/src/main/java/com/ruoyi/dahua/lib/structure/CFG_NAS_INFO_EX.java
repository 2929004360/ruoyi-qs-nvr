package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 网络存储服务器配置
 * @date 2022/09/08 19:33:11
 */
public class CFG_NAS_INFO_EX extends NetSDKLib.SdkStructure {
	/**
	 * 0, 1, 由能力集确定, 只读
	 */
    public int              nVersion;
	/**
	 * nVersion == 0时有效, 仅支持单服务器
	 */
    public CFG_NAS_INFO     stuNasOld = new CFG_NAS_INFO();
	/**
	 * nVersion == 1时有效, 支持多个服务器
	 */
    public CFG_NAS_GROUP_INFO stuNasGroup = new CFG_NAS_GROUP_INFO();

	public CFG_NAS_INFO_EX() {
	}
}

