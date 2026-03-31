package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 感应参数信息
 * @date 2021/09/28
 */
public class NET_SENIOR_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 周期设置个数
	 */
    public int              nCycleSettingNum;
	/**
     *  周期设置
     */
    public NET_CYCLE_SETTINGS_INFO[] stuPhasePriorityInfo = (NET_CYCLE_SETTINGS_INFO[]) new NET_CYCLE_SETTINGS_INFO().toArray(20);
    /**
     *  选票设置
     */
    public NET_VOTE_SETTINGS_INFO stuVoteSettingInfo;
    /**
     *  相位设置
     */
    public NET_PHASE_SETTINGS_INFO stuPhaseSettingInfo;
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

