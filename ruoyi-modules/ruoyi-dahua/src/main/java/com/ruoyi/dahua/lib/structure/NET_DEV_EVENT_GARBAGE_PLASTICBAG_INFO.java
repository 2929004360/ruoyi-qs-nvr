package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 事件类型 EVENT_IVS_GARBAGE_PLASTICBAG (打包垃圾检测事件)对应的数据块描述信息
 * @date 2023/02/17 19:39:19
 */
public class NET_DEV_EVENT_GARBAGE_PLASTICBAG_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 通道号
	 */
    public int              nChannelID;
	/**
	 * 0:脉冲,1:开始, 2:停止
	 */
    public int              nAction;
	/**
	 * 扩展协议字段
	 */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
	/**
	 * 事件名称
	 */
    public byte[]           szName = new byte[128];
	/**
	 * 智能事件所属大类
	 */
    public byte[]           szClass = new byte[16];
	/**
	 * 事件发生的时间
	 */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
	/**
	 * GroupID事件组ID，同一物体抓拍过程内GroupID相同
	 */
    public int              nGroupID;
	/**
	 * CountInGroup一个事件组内的抓拍张数
	 */
    public int              nCountInGroup;
	/**
	 * IndexInGroup一个事件组内的抓拍序号，从1开始
	 */
    public int              nIndexInGroup;
	/**
	 * 相对事件时间戳,(单位是毫秒)
	 */
    public double           dbPTS;
	/**
	 * 事件ID
	 */
    public int              nEventID;
	/**
	 * 检测到的物体个数
	 */
    public int              nObjectCount;
	/**
	 * 检测到的物体信息
	 */
    public NetSDKLib.DH_MSG_OBJECT[] stuObjects = new NetSDKLib.DH_MSG_OBJECT[64];
	/**
	 * 规则检测区域
	 */
    public NET_POINT_EX[]   stuDetectRegion = new NET_POINT_EX[20];
	/**
	 * 规则检测区域顶点数
	 */
    public int              nDetectRegionCount;
	/**
	 * 事件触发的预置点号，从1开始
	 */
    public int              nPresetID;
	/**
	 * 事件触发的预置点名称
	 */
    public byte[]           szPresetName = new byte[64];
	/**
	 * 事件关联ID
	 */
    public byte[]           szSourceID = new byte[32];
	/**
	 * 预留字节
	 */
    public byte[]           szReserved = new byte[1024];

	public NET_DEV_EVENT_GARBAGE_PLASTICBAG_INFO() {
		for (int i = 0; i < stuObjects.length; i++) {
			stuObjects[i] = new NetSDKLib.DH_MSG_OBJECT();
		}
		for (int i = 0; i < stuDetectRegion.length; i++) {
			stuDetectRegion[i] = new NET_POINT_EX();
		}
	}
}

