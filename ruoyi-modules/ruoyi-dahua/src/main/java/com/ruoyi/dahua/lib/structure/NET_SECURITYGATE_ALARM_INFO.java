package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description
 * @date 2022/06/30 17:04:09
 */
public class NET_SECURITYGATE_ALARM_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 报警位置，范围：0-9，分别对应10个区位
	 */
    public int              nPosition;
	/**
	 * 分类检测结果物品类型 0:未分类, 1:手机类, 2:刀枪工具类, 3:易拉罐类, 4:马口铁类
	 */
    public int              nObjectType;
	/**
	 * 保留字节
	 */
    public byte[]           szResvered = new byte[128];
}

