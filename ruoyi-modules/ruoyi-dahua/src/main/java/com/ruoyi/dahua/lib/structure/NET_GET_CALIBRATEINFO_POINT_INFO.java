package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 标定点参数信息
 * @date 2023/05/24 10:24:51
 */
public class NET_GET_CALIBRATEINFO_POINT_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 标定点编号
	 */
    public int              nID;
	/**
	 * 标定点名称
	 */
    public byte[]           szName = new byte[128];
	/**
	 * 使能
	 */
    public int              bEnable;
	/**
	 * 经度
	 */
    public int              nLongitude;
	/**
	 * 纬度
	 */
    public int              nLatitude;
	/**
	 * 海拔
	 */
    public double           fAltitude;
	/**
	 * 标定点坐标 第一维表示不同位置， 第二位表示可见光或热成像
	 */
    public NET_GET_CALIBRATEINFO_CALCINFO_INFO[] stuCalcInfo = new NET_GET_CALIBRATEINFO_CALCINFO_INFO[5 * 2];
	/**
	 * 标定点个数
	 */
    public int              nPointNum;
	/**
	 * 字节对齐
	 */
    public byte[]           byReserved = new byte[4];

	public NET_GET_CALIBRATEINFO_POINT_INFO() {
		for (int i = 0; i < stuCalcInfo.length; i++) {
			stuCalcInfo[i] = new NET_GET_CALIBRATEINFO_CALCINFO_INFO();
		}
	}
}

