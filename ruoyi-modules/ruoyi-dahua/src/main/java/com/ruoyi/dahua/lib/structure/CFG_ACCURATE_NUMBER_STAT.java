package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 精准客流能力，客流ReID, 人脸去重，客流聚档的对外能力
 * @origin autoTool
 * @date 2023/09/22 13:39:22
 */
public class CFG_ACCURATE_NUMBER_STAT extends NetSDKLib.SdkStructure {
	/**
	 * 是否支持精准客流
	 */
    public int              bSupport;
	/**
	 * 是否支持本地报表查询
	 */
    public int              bSupportLocalDataStore;
	/**
	 * 精准人数统计类型 {@link com.ruoyi.dahua.lib.enumeration.EM_RULE_TYPE_ACCURATE}
	 */
    public int              emRuleType;
	/**
	 * 支持客流融合的通道号个数
	 */
    public int              nChannelsNum;
	/**
	 * 支持客流融合的通道号，在RuleType为NumberStatCluster时有效
	 */
    public int[]            nChannels = new int[1024];
	/**
	 * 单个通道支持的区域个数，NVR填写1，IPC根据支持的区域数填写
	 */
    public int              nAreaNum;
	/**
	 * 是否支持营业时间设置，FaceRecognition存在支持和不支持营业时间设置两种形态
	 */
    public int              bSupportTimeCfg;
	/**
	 * 支持的去重方向个数
	 */
    public int              nDirectionNum;
	/**
	 * 支持的去重方向
	 */
    public int[]            emDirection = new int[32];
	/**
	 * 支持查询粒度个数
	 */
    public int              nGranularityNum;
	/**
	 * 支持查询粒度
	 */
    public byte[]           szGranularity = new byte[8 * 8];

	public CFG_ACCURATE_NUMBER_STAT() {
	}
}

