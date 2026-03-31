package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 灯组色步信息
 * @date 2021/09/01
 */
public class STEP_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  进口方向
     */
    public int              nEnterDir;
    /**
     *  进口灯组数量
     */
    public int              nEnterLampNumber;
    /**
     *  灯色信息个数
     */
    public int              nLampInfoNum;
    /**
     *  灯色信息(0~10）包含1到M（进口灯组数量）个灯色信息 
     */
    public LAMP_INFO[]      stuLightColorInfo = (LAMP_INFO[]) new LAMP_INFO().toArray(10);
    /**
     *  预留字节 
     */
    public byte[]           byReserved = new byte[128];
}

