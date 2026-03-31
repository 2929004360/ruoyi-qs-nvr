package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 经过小计信息
 * @date 2022/09/21 14:54:46
 */
public class NET_PASSED_SUBTOTAL_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 设备运行后经过人数统计总数
	 */
    public int              nTotal;
	/**
	 * 小时内的总人数
	 */
    public int              nHour;
	/**
	 * 当天的总人数(自然天)
	 */
    public int              nToday;
	/**
	 * IPC专用，如果不执行clearSectionStat操作，等同于Today人数
	 */
    public int              nTotalInTimeSection;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[112];

	public NET_PASSED_SUBTOTAL_INFO() {
	}
}

