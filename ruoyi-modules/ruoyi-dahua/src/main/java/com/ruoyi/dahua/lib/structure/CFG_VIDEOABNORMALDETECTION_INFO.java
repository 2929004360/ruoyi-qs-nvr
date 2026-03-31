package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 事件类型EVENT_IVS_VIDEOABNORMALDETECTION(视频异常)对应的规则配置
 * @origin autoTool
 * @date 2023/10/31 09:24:33
 */
public class CFG_VIDEOABNORMALDETECTION_INFO extends NetSDKLib.SdkStructure {
	/**
	 * null
	 */
    public byte[]           szRuleName = new byte[128];
	/**
	 * 规则使能
	 */
    public byte             bRuleEnable;
	/**
	 * 灵敏度, 取值1-10，值越小灵敏度越低(只对检测类型视频遮挡，过亮，过暗，场景变化有效)
	 */
    public byte             bSensitivity;
	/**
	 * 保留字段
	 */
    public byte[]           bReserved = new byte[2];
	/**
	 * 相应物体类型个数
	 */
    public int              nObjectTypeNum;
	/**
	 * 相应物体类型列表
	 */
    public byte[]           szObjectTypes = new byte[16 * 128];
	/**
	 * 云台预置点编号 0~65535
	 */
    public int              nPtzPresetId;
	/**
	 * 检测类型数
	 */
    public int              nDetectType;
	/**
	 * 检测类型,0-视频丢失, 1-视频遮挡, 2-画面冻结, 3-过亮, 4-过暗, 5-场景变化 6-条纹检测 , 7-噪声检测 , 8-偏色检测 ,
	 * 9-视频模糊检测 , 10-对比度异常检测 11-视频运动 , 12-视频闪烁 , 13-视频颜色 , 14-虚焦检测 , 15-过曝检测,
	 * 16-场景巨变
	 */
    public byte[]           bDetectType = new byte[32];
	/**
	 * null
	 */
    public int              nMinDuration;
	/**
	 * 报警联动
	 */
    public NetSDKLib.CFG_ALARM_MSG_HANDLE stuEventHandler = new NetSDKLib.CFG_ALARM_MSG_HANDLE();
	/**
	 * 事件响应时间段
	 */
    public NetSDKLib.CFG_TIME_SECTION[] stuTimeSection = new NetSDKLib.CFG_TIME_SECTION[7 * 10];
	/**
	 * 检测区顶点数
	 */
    public int              nDetectRegionPoint;
	/**
	 * 检测区
	 */
    public NetSDKLib.CFG_POLYGON[] stuDetectRegion = new NetSDKLib.CFG_POLYGON[20];
	/**
	 * 异常检测阈值数量
	 */
    public int              nThresholdNum;
	/**
	 * 异常检测阈值,范围1~100
	 */
    public int[]            nThreshold = new int[32];

	public CFG_VIDEOABNORMALDETECTION_INFO() {
		for (int i = 0; i < stuTimeSection.length; i++) {
			stuTimeSection[i] = new NetSDKLib.CFG_TIME_SECTION();
		}
		for (int i = 0; i < stuDetectRegion.length; i++) {
			stuDetectRegion[i] = new NetSDKLib.CFG_POLYGON();
		}
	}
}

