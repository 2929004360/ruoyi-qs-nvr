package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 区域信息
 * @date 2022/07/22 14:43:48
 */
public class CFG_AREA_MODE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 区域名称
	 */
    public byte[]           szName = new byte[32];
	/**
	 * 该区域的总车位个数
	 */
    public int              nCount;
	/**
	 * 区域坐标个数
	 */
    public int              nAreaCount;
	/**
	 * 区域坐标
	 */
    public NetSDKLib.CFG_POLYGON[] stuArea = new NetSDKLib.CFG_POLYGON[20];
	/**
	 * 车位类型: 0:未知, 1:小车车位, 2:大车车位, 3:危化品车位, 4:货车车位, 5:小客车车位, 6:大客车车位
	 */
    public int              nSpaceType;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[60];

	public CFG_AREA_MODE_INFO() {
		for (int i = 0; i < stuArea.length; i++) {
			stuArea[i] = new NetSDKLib.CFG_POLYGON();
		}
	}
}

