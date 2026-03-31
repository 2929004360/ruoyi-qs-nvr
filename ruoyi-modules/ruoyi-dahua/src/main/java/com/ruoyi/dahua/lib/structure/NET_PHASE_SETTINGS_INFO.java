package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 相位设置信息
 * @date 2021/09/28
 */
public class NET_PHASE_SETTINGS_INFO extends NetSDKLib.SdkStructure {
	/**
     *  相位表个数
     */
    public int              nPhaseListNum;
    /**
     *  相位表
     */
    public NET_PHASE_LIST_INFO[] stuPhaseListInfo = (NET_PHASE_LIST_INFO[]) new NET_PHASE_LIST_INFO().toArray(16);
    /**
     *  相位优先配置表个数
     */
    public int              nPhasePrioritysNum;
    /**
     *  相位优先配置
     */
    public NET_PHASE_PRIORITY_INFO[] stuPhasePriorityInfo = (NET_PHASE_PRIORITY_INFO[]) new NET_PHASE_PRIORITY_INFO().toArray(16);
    /**
     *  检测器配置表个数
     */
    public int              nDetectorSettingsNum;
    /**
     *  检测器配置表
     */
    public NET_DETECTOR_SETTING_INFO[] stuDetectorSettingInfo = (NET_DETECTOR_SETTING_INFO[]) new NET_DETECTOR_SETTING_INFO().toArray(16);
    /**
     *  相位所属表个数
     */
    public int              nClosureSettingNum;
    /**
     *  相位所属配置表
     */
    public NET_CLOSURE_SETTING_INFO[] stuClosureSettingInfo = (NET_CLOSURE_SETTING_INFO[]) new NET_CLOSURE_SETTING_INFO().toArray(16);
    /**
     *  预留字节 
     */
    public byte[]           szReserved = new byte[32];
}

