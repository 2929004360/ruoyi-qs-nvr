package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 车位统计场景配置信息
 * @date 2022/07/22 14:43:48
 */
public class CFG_PARKING_STATISTICS_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 统计模式: "AreaMode" 区域模式, "SpaceMode" 车位模式
	 */
    public byte[]           szStatisticsMode = new byte[16];
	/**
	 * 区域个数
	 */
    public int              nAreaModeCount;
	/**
	 * 区域信息数组, 每个元素表示一个区域; szStatisticsMode为AreaMode时有效
	 */
    public CFG_AREA_MODE_INFO[] stuAreaMode = new CFG_AREA_MODE_INFO[128];
	/**
	 * 车位个数
	 */
    public int              nSpaceModeCount;
	/**
	 * 车位信息数组, 每个元素表示一个车位; szStatisticsMode为SpaceMode时有效
	 */
    public CFG_SPACE_MODE_INFO[] stuSpaceMode = new CFG_SPACE_MODE_INFO[128];
	/**
	 * 车辆置信度阈值, 范围[0, 100], 低于该阈值的车辆不做统计
	 */
    public int              nConfidenceFilter;

	public CFG_PARKING_STATISTICS_INFO() {
		for (int i = 0; i < stuAreaMode.length; i++) {
			stuAreaMode[i] = new CFG_AREA_MODE_INFO();
		}
		for (int i = 0; i < stuSpaceMode.length; i++) {
			stuSpaceMode[i] = new CFG_SPACE_MODE_INFO();
		}
	}
}

