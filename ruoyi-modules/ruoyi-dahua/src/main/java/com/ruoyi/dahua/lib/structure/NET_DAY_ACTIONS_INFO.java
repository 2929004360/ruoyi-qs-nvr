package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 日计划时段表内容
 * @date 2021/09/27
 */
public class NET_DAY_ACTIONS_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  时段编号，从1开始
     */
    public int              nActionNumber;
    /**
     *  时段的起始小时
     */
    public int              nBeginHour;
    /**
     *  时段的起始分钟
     */
    public int              nBeginMinute;
    /**
     *  时段模式
     */
    public int              nCyclePlanControlMode;
    /**
     * 周期方案编号
    */
    public int              nCyclePlanNum;
    /**
     *  预留字节 
     */
    public byte[]           szReserved = new byte[28];
}

