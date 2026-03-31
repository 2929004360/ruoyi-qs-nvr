package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 当前信号方案色步信息
 * @date 2021/09/01
 */
public class CUR_STEP_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  灯组总数量
     */
    public int              nLampNumber;
    /**
     *  信号灯控制路口的进口数量
     */
    public int              nEnterNumber;
    /**
     *  灯组色步信息个数
     */
    public int              nStepInfoNum;
    /**
     *  灯组色步信息（0~8）包含1到N（进口数量）个灯组色步信息
     */
    public STEP_INFO[]      stuStepInfo = (STEP_INFO[]) new STEP_INFO().toArray(8);
    /**
     *  预留字节
     */
    public byte[]           byReserved = new byte[128];
}

