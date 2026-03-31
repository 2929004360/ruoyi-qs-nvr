package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 门禁互锁相关能力
 * @date 2023/04/18 19:40:08
 */
public class NET_INTER_LOCK_CAPS extends NetSDKLib.SdkStructure {
	/**
	 * 支持互锁回能力掩码，支持位或 bit0-本地反潜;bit1-网络反潜主控;bit2-网络反潜分控
	 */
    public int              nTypes;
	/**
	 * 最大规则组数量, nTypes bit1=1时有效
	 */
    public int              nMaxRule;
	/**
	 * 组内每个组最大支持多少个门, nTypes bit1=1时有效
	 */
    public int              nWithinMaxDoors;
	/**
	 * 组间每个组最大支持多少个门, nTypes bit1=1时有效
	 */
    public int              nBetweenMaxDoors;
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[512];

	public NET_INTER_LOCK_CAPS() {
	}
}

