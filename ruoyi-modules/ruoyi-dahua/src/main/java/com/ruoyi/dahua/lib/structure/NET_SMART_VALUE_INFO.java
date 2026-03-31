package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 设备SMART信息
 * @date 2022/05/30 14:43:22
 */
public class NET_SMART_VALUE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 属性ID
	 */
    public int              nID;
	/**
	 * 属性名
	 */
    public byte[]           szName = new byte[32];
	/**
	 * 属性值
	 */
    public int              nCurrent;
	/**
	 * 最大出错值
	 */
    public int              nWorst;
	/**
	 * 阈值
	 */
    public int              nThreshold;
	/**
	 * 实际值
	 */
    public byte[]           szRaw = new byte[32];
	/**
	 * 状态
	 */
    public int              nPredict;
	/**
	 * Raid同步状态, -1: 未知; 0: 自适应; 1: 同步优先,I/O优先分给Raid同步; 2:业务优先,I/O优先分给硬盘写数据; 3: 均衡
	 */
    public int              nSync;
	/**
	 * 保留字节
	 */
    public byte[]           szResvered = new byte[256];

	public NET_SMART_VALUE_INFO() {
	}
}

