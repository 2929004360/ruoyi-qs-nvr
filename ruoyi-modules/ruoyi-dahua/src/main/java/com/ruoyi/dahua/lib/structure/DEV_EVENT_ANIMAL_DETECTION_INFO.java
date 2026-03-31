package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 260611
 * @description ： 事件类型EVENT_IVS_ANIMAL_DETECTION(动物检测事件)对应的数据块描述信息
 * @since ： Created in 2021/11/05 14:46
 */

public class DEV_EVENT_ANIMAL_DETECTION_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 通道号
	 */
    public int              nChannelID;
	/**
	 * 0:脉冲 1:开始 2:停止
	 */
    public int              nAction;
	/**
	 * 事件名称
	 */
    public byte[]           szName = new byte[128];
	/**
	 * 时间戳(单位是毫秒)
	 */
    public double           PTS;
	/**
	 * 事件发生的时间
	 */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
	/**
	 * 事件ID
	 */
    public int              nEventID;
	/**
	 * 智能事件规则编号，用于标示哪个规则触发的事件
	 */
    public int              nRuleID;
	/**
	 * 帧序号
	 */
    public int              nSequence;
	/**
	 * 全景广角图
	 */
    public NET_ANIMAL_SCENE_IMAGE_INFO stuSceneImage = new NET_ANIMAL_SCENE_IMAGE_INFO();
	/**
	 * 动物统计信息
	 */
    public NET_ANIMAL_OBJECTS_STATISTICS stuObjectsStatistics = new NET_ANIMAL_OBJECTS_STATISTICS();
	/**
	 * 智能事件所属大类,EM_CLASS_TYPE
	 */
    public int              emClassType;
	/**
	 * 动物检测规则下的场景类型,EM_DETECTION_SCENE_TYPE
	 */
    public int              emDetectionSceneType;
	/**
	 * 图片信息数组
	 */
    public NET_IMAGE_INFO_EX2[] stuImageInfo = (NET_IMAGE_INFO_EX2[]) new NET_IMAGE_INFO_EX2().toArray(32);
	/**
	 * 图片信息个数
	 */
    public int              nImageInfoNum;
	/**
	 * 区域内目标列表个数
	 */	
    public int              nObjectListCount;
	/**
	 * 区域内目标列表
	 */			
    public NET_ANIMAL_OBJECT_LIST_INFO[] stuObjectListInfo = (NET_ANIMAL_OBJECT_LIST_INFO[]) new NET_ANIMAL_OBJECT_LIST_INFO().toArray(64);
	/**
	 * 预留字节
	 */
    public byte[]           byReserved = new byte[1024];
}

