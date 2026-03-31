package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.EM_WORKACTION_STATE;
import com.ruoyi.dahua.lib.enumeration.EM_WORKSTATDETECTION_TYPE;
import com.sun.jna.Pointer;

/**
 * @author ： 260611
 * @description ： 事件类型EVENT_IVS_WORKSTATDETECTION(作业统计事件)对应的数据块描述信息
 * @since ： Created in 2022/03/08 15:25
 */

public class DEV_EVENT_WORKSTATDETECTION_INFO extends NetSDKLib.SdkStructure {
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
	 * 智能事件所属大类,{@link NetSDKLib.EM_CLASS_TYPE}
	 */
    public int              emClassType;
	/**
	 * 智能事件规则编号，用于标示哪个规则触发的事件
	 */
    public int              nRuleID;
	/**
	 * 物体ID
	 */
    public int              nObjectID;
	/**
	 * 作业行为状态个数
	 */
    public int              nWorkActionNum;
	/**
	 * 作业行为状态信息,{@link EM_WORKACTION_STATE}
	 */
    public int[]            emWorkAction = new int[32];
	/**
	 * 全景大图信息
	 */
    public NetSDKLib.SCENE_IMAGE_INFO stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO();
	/**
	 * 报警规则类型,{@link EM_WORKSTATDETECTION_TYPE}
	 */
    public int              emRuleType;
	/**
	 * 物体信息数据 ，参考结构体NET_MSG_OBJECT_EX2
	 */
    public Pointer          pstObjectInfo;
	/**
	 * 物体信息数
	 */
    public int              nObjectNum;
	/**
	 * 预留字节
	 */
    public byte[]           byReserved = new byte[1016 - NetSDKLib.POINTERSIZE];
}

