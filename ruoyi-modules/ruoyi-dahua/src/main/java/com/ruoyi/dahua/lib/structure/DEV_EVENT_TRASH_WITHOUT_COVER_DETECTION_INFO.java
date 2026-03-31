package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 事件类型EVENT_IVS_TRASH_WITHOUT_COVER_DETECTION
 *              (垃圾桶未盖盖子检测事件)对应的数据块描述信息
 * @origin autoTool
 * @date 2023/12/01 11:34:34
 */
public class DEV_EVENT_TRASH_WITHOUT_COVER_DETECTION_INFO extends NetSDKLib.SdkStructure {
	/**
	 * / 通道号
	 */
    public int              nChannelID;
	/**
	 * / 事件动作, 1:开始 2:停止
	 */
    public int              nAction;
	/**
	 * / 事件名称
	 */
    public byte[]           szName = new byte[128];
	/**
	 * / 时间戳(单位是毫秒)
	 */
    public double           PTS;
	/**
	 * / 事件发生的时间
	 */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
	/**
	 * / 智能事件所属大类 {@link NetSDKLib.EM_SCENE_CLASS_TYPE}
	 */
    public int              emClassType;
	/**
	 * / 规则ID, 用于标示哪个规则触发的事件
	 */
    public int              nRuleID;
	/**
	 * / 事件ID
	 */
    public int              nEventID;
	/**
	 * / 事件组ID，同一物体抓拍过程内GroupID相同
	 */
    public int              nGroupID;
	/**
	 * / 一个事件组内的抓拍张数
	 */
    public int              nCountInGroup;
	/**
	 * / 一个事件组内的抓拍序号, 从1开始
	 */
    public int              nIndexInGroup;
	/**
	 * / 事件触发的预置点号，从1开始, 0 表示无效
	 */
    public int              nPresetID;
	/**
	 * / 字节对齐
	 */
    public byte[]           szReserved1 = new byte[4];
	/**
	 * / 检测到的物体个数
	 */
    public int              nObjectNum;
	/**
	 * / 检测到的物体
	 */
    public NetSDKLib.DH_MSG_OBJECT[] stuObjects = new NetSDKLib.DH_MSG_OBJECT[128];
	/**
	 * / 全景广角图
	 */
    public NetSDKLib.SCENE_IMAGE_INFO_EX stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO_EX();
	/**
	 * / 图片信息数组
	 */
    public NET_IMAGE_INFO_EX2[] stuImageInfo = new NET_IMAGE_INFO_EX2[32];
	/**
	 * / 图片信息个数
	 */
    public int              nImageInfoNum;
	/**
	 * / 保留字节,留待扩展.
	 */
    public byte[]           byReserved = new byte[1024];

	public DEV_EVENT_TRASH_WITHOUT_COVER_DETECTION_INFO() {
		for (int i = 0; i < stuObjects.length; i++) {
			stuObjects[i] = new NetSDKLib.DH_MSG_OBJECT();
		}
		for (int i = 0; i < stuImageInfo.length; i++) {
			stuImageInfo[i] = new NET_IMAGE_INFO_EX2();
		}
	}
}

