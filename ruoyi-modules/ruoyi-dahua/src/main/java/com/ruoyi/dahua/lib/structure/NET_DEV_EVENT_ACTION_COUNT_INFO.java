package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 事件类型 EVENT_IVS_ACTION_COUNT (行为自定义行为计数事件)对应的数据块描述信息
 * @origin autoTool
 * @date 2023/07/11 10:31:10
 */
public class NET_DEV_EVENT_ACTION_COUNT_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 通道号
	 */
    public int              nChannelID;
	/**
	 * 0:脉冲, 1:开始, 2:停止
	 */
    public int              nAction;
	/**
	 * 扩展协议字段
	 */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
	/**
	 * 事件发生时间, 带时区偏差的UTC时间, 单位秒
	 */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
	/**
	 * 事件编号
	 */
    public int              nEventID;
	/**
	 * 事件名称
	 */
    public byte[]           szName = new byte[128];
	/**
	 * 智能事件所属大类
	 */
    public byte[]           szClass = new byte[16];
	/**
	 * 相对事件时间戳,单位毫秒
	 */
    public double           dbPTS;
	/**
	 * 智能事件规则编号
	 */
    public int              nRuleID;
	/**
	 * 检测到的物体信息个数
	 */
    public int              nObjectsNum;
	/**
	 * 检测到的物体信息
	 */
    public NetSDKLib.DH_MSG_OBJECT[] stuObjects = new NetSDKLib.DH_MSG_OBJECT[100];
	/**
	 * 全景广角图
	 */
    public NetSDKLib.SCENE_IMAGE_INFO_EX stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO_EX();
	/**
	 * 算法名称
	 */
    public byte[]           szAlgorithmName = new byte[64];
	/**
	 * 自定义报警id
	 */
    public byte[]           szAlarmID = new byte[32];
	/**
	 * 自定义行为计数
	 */
    public byte[]           szActionName = new byte[64];
	/**
	 * 自定义行为分析行为名称
	 */
    public int              nActionCount;
	/**
	 * 预留字节
	 */
    public byte[]           szReserved = new byte[1020];

	public NET_DEV_EVENT_ACTION_COUNT_INFO() {
		for (int i = 0; i < stuObjects.length; i++) {
			stuObjects[i] = new NetSDKLib.DH_MSG_OBJECT();
		}
	}
}

