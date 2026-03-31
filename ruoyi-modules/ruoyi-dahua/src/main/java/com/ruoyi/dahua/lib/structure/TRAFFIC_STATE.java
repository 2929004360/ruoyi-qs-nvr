package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 交通运行状态信息
 * @date 2021/09/01
 */
public class TRAFFIC_STATE extends NetSDKLib.SdkStructure {
	 /**
     *  进口数量
     */
    public int              nEnterNumber;
    /**
     *  进口交通运行状态信息个数
     */
    public int              nEnterStateInfoNum;
    /**
     *  进口交通运行状态信息(0~8), 包含1到N（进口数量）个进口交通运行状态信息
     */
    public ENTER_STATE_INFO[] stuEnterStateInfo = (ENTER_STATE_INFO[]) new ENTER_STATE_INFO().toArray(8);
    /**
     *  统计分析周期，单位分钟1~255
     */
    public int              nPeriod;
    /**
     *  预留字节
     */
    public byte[]           byReserved = new byte[124];
}

