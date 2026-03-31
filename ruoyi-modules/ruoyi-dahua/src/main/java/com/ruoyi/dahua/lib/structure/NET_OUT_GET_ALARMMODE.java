package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description获取布防模式 输出参数。此时CLIENT_GetAlarmRegionInfo的emType参数为NET_EM_GET_ALARMREGION_INFO_ARMMODE
 * @date 2021/11/08
 */
public class NET_OUT_GET_ALARMMODE extends NetSDKLib.SdkStructure {
	/**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  布撤防状态个数
     */
    public int              nArmModeRet;
    /**
     *  布撤防信息
     */
    public NET_ARMMODE_INFO[] stuArmMode = (NET_ARMMODE_INFO[]) new NET_ARMMODE_INFO().toArray(NetSDKLib.MAX_AREA_NUMBER);
    /**
     *  布撤防状态个数扩展 超过8个请使用这个字段
     */
    public int              nArmModeRetEx;
    /**
     *  布撤防信息
     */
    public NET_ARMMODE_INFO[] stuArmModeEx = (NET_ARMMODE_INFO[]) new NET_ARMMODE_INFO().toArray(NetSDKLib.MAX_AREA_NUMBER_EX);

    public NET_OUT_GET_ALARMMODE(){
        this.dwSize = this.size();
    }
}

