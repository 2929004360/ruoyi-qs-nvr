package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description 事件类型 EVENT_IVS_ILLEGAL_CARRIAGE(非法运输事件)对应的数据块描述信息
 * @date 2023/02/17 19:37:30
 */
public class NET_DEV_EVENT_ILLEGAL_CARRIAGE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 通道号
	 */
    public int              nChannelID;
	/**
	 * 事件类型 0:脉冲,1:开始, 2:停止
	 */
    public int              nAction;
	/**
	 * 事件名称
	 */
    public byte[]           szName = new byte[128];
	/**
	 * 智能事件所属大类
	 */
    public byte[]           szClass = new byte[128];
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
	 * 事件发生的时间
	 */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
	/**
	 * 仅用于字节对齐,无实际意义
	 */
    public byte[]           byReserved = new byte[4];
	/**
	 * 事件时间毫秒数
	 */
    public int              nUTCMS;
	/**
	 * 事件ID
	 */
    public int              nEventID;
	/**
	 * 车辆信息
	 */
    public NetSDKLib.NET_MSG_OBJECT_EX2[] stuVehicles = new NetSDKLib.NET_MSG_OBJECT_EX2[10];
	/**
	 * 检测到的车辆信息个数(配合stuVehicles使用)
	 */
    public int              nVehiclesNum;
	/**
	 * 检测到的车牌信息个数(配合stuObjects使用)
	 */
    public int              nObjectsNum;
	/**
	 * 车牌信息
	 */
    public NetSDKLib.NET_MSG_OBJECT_EX2[] stuObjects = new NetSDKLib.NET_MSG_OBJECT_EX2[10];
	/**
	 * 全景广角图, 仅nIndexInData字段有效
	 */
    public NetSDKLib.SCENE_IMAGE_INFO stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO();
	/**
	 * 大客车周身门的状态(比如车门, 行李箱门是否打开), 0-未知, 1-是, 2-不是
	 */
    public int              nBusDoorStatus;
	/**
	 * 附近是否存在行李箱, 0-没有行李箱, 1-有行李箱
	 */
    public int              nIsTrunk;
	/**
	 * 图片信息数组，指针对应结构体NET_IMAGE_INFO_EX2数组
	 */
    public Pointer          pstuImageInfo;
	/**
	 * 图片信息个数
	 */
    public int              nImageInfoNum;
	/**
	 * 检测区个数
	 */
    public int              nDetectRegionNum;
	/**
	 * 检测区
	 */
    public NET_POINT_EX[]   stuDetectRegion = new NET_POINT_EX[20];
	/**
	 * 预留字节
	 */
    public byte[]           szReserved = new byte[1936 - NetSDKLib.POINTERSIZE];

	public NET_DEV_EVENT_ILLEGAL_CARRIAGE_INFO() {
		for (int i = 0; i < stuVehicles.length; i++) {
			stuVehicles[i] = new NetSDKLib.NET_MSG_OBJECT_EX2();
		}
		for (int i = 0; i < stuObjects.length; i++) {
			stuObjects[i] = new NetSDKLib.NET_MSG_OBJECT_EX2();
		}
		for (int i = 0; i < stuDetectRegion.length; i++) {
			stuDetectRegion[i] = new NET_POINT_EX();
		}
	}
}

