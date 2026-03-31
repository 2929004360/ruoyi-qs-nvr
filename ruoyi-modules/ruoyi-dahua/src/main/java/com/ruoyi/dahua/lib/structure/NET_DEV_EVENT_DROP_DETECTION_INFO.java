package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

import static com.ruoyi.dahua.lib.NetSDKLib.POINTERSIZE;

/**
 * @author 260611
 * @description 事件类型 EVENT_IVS_DROP_DETECTION(滴漏检测事件)对应的数据块描述信息
 * @origin autoTool
 * @date 2023/07/14 15:01:21
 */
public class NET_DEV_EVENT_DROP_DETECTION_INFO extends NetSDKLib.SdkStructure {
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
	 * 检测目标个数
	 */
    public int              nObjectsNum;
	/**
	 * 检测目标的物体信息
	 */
    public NetSDKLib.DH_MSG_OBJECT[] stuObjects = new NetSDKLib.DH_MSG_OBJECT[128];
	/**
	 * 全景广角图，仅 nIndexInData字段有效
	 */
    public NetSDKLib.SCENE_IMAGE_INFO stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO();
	/**
	 * 图片信息数组, refer to {@link NET_IMAGE_INFO_EX2}
	 */
    public Pointer          pstuImageInfo;
	/**
	 * 图片信息个数
	 */
    public int              nImageInfoNum;
	/**
	 * null
	 */
    public byte[]           szReserved = new byte[508 - POINTERSIZE];

	public NET_DEV_EVENT_DROP_DETECTION_INFO() {
		for (int i = 0; i < stuObjects.length; i++) {
			stuObjects[i] = new NetSDKLib.DH_MSG_OBJECT();
		}
	}
}

