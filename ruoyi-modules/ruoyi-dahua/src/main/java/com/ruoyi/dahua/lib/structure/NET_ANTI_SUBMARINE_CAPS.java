package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 反潜回能力集
 * @date 2023/04/18 19:40:08
 */
public class NET_ANTI_SUBMARINE_CAPS extends NetSDKLib.SdkStructure {
	/**
	 * 支持反潜回能力掩码，支持位或 bit0-本地反潜;bit1-网络反潜主控;bit2-网络反潜分控
	 */
    public int              nTypes;
	/**
	 * 最大反潜规则组（路线）数量, nTypes bit1=1时有效
	 */
    public int              nMaxRouteRule;
	/**
	 * 每个反潜路线最大多少个节点, nTypes bit1=1时有效
	 */
    public int              nOneRouteMaxNode;
	/**
	 * 每个节点最多包含的读头数量, nTypes bit1=1时有效
	 */
    public int              nOneNodeMaxReaderID;
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[512];

	public NET_ANTI_SUBMARINE_CAPS() {
	}
}

