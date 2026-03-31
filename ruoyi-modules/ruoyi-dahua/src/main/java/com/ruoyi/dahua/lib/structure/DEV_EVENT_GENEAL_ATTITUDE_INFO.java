package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description 事件类型EVENT_IVS_GENEAL_ATTITUDE (通用姿态行为事件)对应的数据块描述信息
 * @origin autoTool
 * @date 2023/07/11 10:31:09
 */
public class DEV_EVENT_GENEAL_ATTITUDE_INFO extends NetSDKLib.SdkStructure {
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
	 * 时间戳(单位是毫秒)
	 */
    public double           dbPTS;
	/**
	 * 事件发生的时间
	 */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
	/**
	 * 事件ID
	 */
    public int              nEventID;
	/**
	 * 智能事件所属大类
	 */
    public byte[]           szClass = new byte[16];
	/**
	 * 事件时间毫秒数
	 */
    public int              nUTCMS;
	/**
	 * 检测目标的物体个数
	 */
    public int              nObjectCount;
	/**
	 * 检测目标的物体信息
	 */
    public NetSDKLib.DH_MSG_OBJECT[] stuObjects = new NetSDKLib.DH_MSG_OBJECT[128];
	/**
	 * 全景广角图
	 */
    public NetSDKLib.SCENE_IMAGE_INFO stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO();
	/**
	 * 姿态类型 {@link com.ruoyi.dahua.lib.enumeration.NET_EM_ATTITUDE_TYPE}
	 */
    public int              emAttitudeType;
	/**
	 * 智能事件规则编号
	 */
    public int              nRuleID;
	/**
	 * 图片信息个数
	 */
    public int              nImageInfoNum;
	/**
	 * 图片信息数组
	 */
    public Pointer          pstuImageInfo;
	/**
	 * 保留字节,留待扩展
	 */
    public byte[]           szReserved = new byte[1016-NetSDKLib.POINTERSIZE];

	public DEV_EVENT_GENEAL_ATTITUDE_INFO() {
		for (int i = 0; i < stuObjects.length; i++) {
			stuObjects[i] = new NetSDKLib.DH_MSG_OBJECT();
		}
	}
}

