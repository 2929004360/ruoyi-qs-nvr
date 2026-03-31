package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description 事件类型 EVENT_IVS_WADING_DETECTION (涉水安全检测、水域监测报警)对应的数据块描述信息
 * @origin autoTool
 * @date 2023/07/21 15:56:23
 */
public class NET_DEV_EVENT_WADING_DETECTION_INFO extends NetSDKLib.SdkStructure {
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
	 * 事件组ID，同一辆车抓拍过程内GroupID相同。
	 */
    public int              nGroupID;
	/**
	 * CountInGroup一个事件组内应有的抓拍张数
	 */
    public int              nCountInGroup;
	/**
	 * 一个事件组内的抓拍序号，从1开始
	 */
    public int              nIndexInGroup;
	/**
	 * 检测到的物体信息个数
	 */
    public int              nObjectsNum;
	/**
	 * 检测到的物体信息
	 */
    public NetSDKLib.DH_MSG_OBJECT[] stuObjects = new NetSDKLib.DH_MSG_OBJECT[32];
	/**
	 * 检测区域
	 */
    public NET_POINT_EX[]   stuDetectRegion = new NET_POINT_EX[20];
	/**
	 * 实际检测到区域个数
	 */
    public int              nDetectRegionNum;
    /**
     * 实际检测到区域个扩展
    */
    public int              nDetectRegionNumEx;
    /**
     * 检测区域扩展,参见结构体定义 {@link NET_POINT_EX}
    */
    public Pointer          pstuDetectRegion;
	/**
	 * 预留字节
	 */
    public byte[]           szReserved = new byte[1016-NetSDKLib.POINTERSIZE];

	public NET_DEV_EVENT_WADING_DETECTION_INFO() {
		for (int i = 0; i < stuObjects.length; i++) {
			stuObjects[i] = new NetSDKLib.DH_MSG_OBJECT();
		}
		for (int i = 0; i < stuDetectRegion.length; i++) {
			stuDetectRegion[i] = new NET_POINT_EX();
		}
	}
}

