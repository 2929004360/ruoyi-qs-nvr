package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 跟随相位列表信息
 * @date 2021/09/02
 */
public class NET_OVER_LAPS_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  跟随相位编号
     */
    public int              nOverlapNum;
    /**
     *  跟随相位关联的主相位个数
     */
    public int              nIncludedPhasesNum;
    /**
     *  跟随相位关联的主相位数组
     */
    public int[]            nIncludedPhases = new int[16];
    /**
     *  跟随相位延时时间个数
     */
    public int              nStartDelayTimeNum;
    /**
     *  跟随相位延时时间数组
     */
    public int[]            nStartDelayTime = new int[16];
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

