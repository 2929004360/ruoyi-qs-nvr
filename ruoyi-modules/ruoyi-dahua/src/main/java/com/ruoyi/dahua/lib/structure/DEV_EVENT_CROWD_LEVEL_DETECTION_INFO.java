package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description 事件类型 EVENT_IVS_CROWD_LEVEL_DETECTION (拥挤程度检测事件)对应的数据块描述信息
 * @origin autoTool
 * @date 2023/06/19 14:48:39
 */
public class DEV_EVENT_CROWD_LEVEL_DETECTION_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 通道号
	 */
    public int              nChannelID;
	/**
	 * 0:脉冲,1:开始, 2:停止
	 */
    public int              nAction;
	/**
	 * 事件名称
	 */
    public byte[]           szName = new byte[128];
	/**
	 * 智能事件所属大类
	 */
    public byte[]           szClass = new byte[16];
	/**
	 * 相对事件时间戳,(单位是毫秒)
	 */
    public double           dbPTS;
	/**
	 * 事件发生的时间
	 */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
	/**
	 * 事件时间毫秒数
	 */
    public int              nUTCMS;
	/**
	 * 事件ID
	 */
    public int              nEventID;
	/**
	 * 规则区域
	 */
    public NetSDKLib.NET_POINT[] stuDetectRegion = new NetSDKLib.NET_POINT[20];
	/**
	 * 规则区域个数
	 */
    public int              nDetectRegionNum;
	/**
	 * 事件触发的预置点号，从1开始
	 */
    public int              nPresetID;
	/**
	 * 触发模式 {@link com.ruoyi.dahua.lib.enumeration.EM_CROWD_LEVEL_DETECTION_TRIGGERMODE}
	 */
    public int              emTriggerMode;
	/**
	 * 实际触发报警的人数
	 */
    public int              nCrowdDetectNum;
	/**
	 * 检测到的拥挤程度等级
	 */
    public int              nCrowdRankResult;
	/**
	 * 字节对齐
	 */
    public byte[]           szReserved1 = new byte[4];
	/**
	 * 检测到的拥挤程度等级名称
	 */
    public byte[]           szCrowdGroupName = new byte[128];
	/**
	 * 图片信息数组，{@link NET_IMAGE_INFO_EX3}
	 */
    public Pointer          pstuImageInfo;
	/**
	 * 图片信息个数
	 */
    public int              nImageInfoNum;
	/**
	 * 扩展协议字段
	 */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
	/**
	 * 预留字节
	 */
    public byte[]           szReserved = new byte[1020 - NetSDKLib.POINTERSIZE];

	public DEV_EVENT_CROWD_LEVEL_DETECTION_INFO() {
		for (int i = 0; i < stuDetectRegion.length; i++) {
			stuDetectRegion[i] = new NetSDKLib.NET_POINT();
		}
	}
}

