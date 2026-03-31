package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * @author 251823
 * @description 人数统计大类场景
 * @date 2021/01/11
 */
public class CFG_NUMBER_STAT_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 相机类型，0：表示老单目客流 1：表示新统一单目客流 2：表示新统一双目客流
	 */
    public int              nCameraType;
	/**
	 * 定时上报以及上报周期
	 */
    public CFG_SUPPORT_SCHEDULE_REPORT stuSupportScheduleReport;
	/**
	 * 精准客流能力，客流ReID, 目标去重，客流聚档的对外能力
	 */
    public CFG_ACCURATE_NUMBER_STAT stuAccurateNumberStat = new CFG_ACCURATE_NUMBER_STAT();
}

