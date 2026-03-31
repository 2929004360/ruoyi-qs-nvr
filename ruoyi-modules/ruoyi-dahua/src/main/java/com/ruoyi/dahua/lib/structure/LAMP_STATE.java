package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 灯色状态信息
 * @date 2021/09/01
 */
public class LAMP_STATE extends NetSDKLib.SdkStructure {
	 /**
     *  信号灯控制路口的进口数量（0~8）
     */
    public int              nEnterNumber;
    /**
     *  进口灯色状态信息个数
     */
    public int              nEnterInfoNum;
    /**
     *  进口灯色状态信息，包含1到N（路口进口数量）个进口灯色状态信息
     */
    public ENTER_INFO[]     stuEnterInfo = (ENTER_INFO[]) new ENTER_INFO().toArray(8);
    /**
     *  预留字节
     */
    public byte[]           byReserved = new byte[128];
}

