package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 分类检测结果人数统计信息
 * @date 2022/06/30 17:13:22
 */
public class NET_CLASSIFY_RESULT_STATISTICS_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 分类检结果物品类型, 0:未分类, 1:手机类, 2:刀枪工具类, 3:易拉罐类, 4:马口铁类
	 */
    public int              nObjectType;
	/**
	 * 进入方向包含该报警物品类型的人数
	 */
    public int              nInCount;
	/**
	 * 离开方向包含该报警物品类型的人数
	 */
    public int              nOutCount;
	/**
	 * 保留字节
	 */
    public byte[]           szResvered = new byte[244];

	public NET_CLASSIFY_RESULT_STATISTICS_INFO() {
	}
}

