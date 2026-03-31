package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 事件类型DH_ALARM_WORKSUIT_FEATURE_ABSTRACT
 *              (工装特征向量建模结果上报事件)对应的数据块描述信息
 * @date 2022/10/09 11:22:52
 */
public class ALARM_WORKSUIT_FEATURE_ABSTRACT_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 事件动作,0表示脉冲事件
	 */
    public int              nAction;
	/**
	 * 事件ID
	 */
    public int              nEventID;
	/**
	 * 时间戳(单位是毫秒)
	 */
    public double           dbPTS;
	/**
	 * 事件发生的时间
	 */
    public NET_TIME_EX      stuTime = new NET_TIME_EX();
	/**
	 * 工装建模结果个数
	 */
    public int              nAbstractInfoNum;
	/**
	 * 工装建模结果信息
	 */
    public NET_WORKSUIT_ABSTRACT_INFO[] stuAbstractInfo = new NET_WORKSUIT_ABSTRACT_INFO[20];
	/**
	 * 预留字段
	 */
    public byte[]           szReserved = new byte[1024];

	public ALARM_WORKSUIT_FEATURE_ABSTRACT_INFO() {
		for (int i = 0; i < stuAbstractInfo.length; i++) {
			stuAbstractInfo[i] = new NET_WORKSUIT_ABSTRACT_INFO();
		}
	}
}

