package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 进口灯色状态信息
 * @date 2021/09/01
 */
public class ENTER_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  进口方向
     */
    public int              nEnterDir;
    /**
     *  进口灯组数量（0~48）
     */
    public int              nLampNumber;
    /**
     *  灯组灯色信息个数
     */
    public int              nLampGroupNum;
    /**
     *  灯组灯色信息，包含1到M（进口灯组数量）个灯组灯色信息
     */
    public LAMP_GROUP_INFO[] stuLampGroupInfo = (LAMP_GROUP_INFO[]) new LAMP_GROUP_INFO().toArray(48);
    /**
     *  预留字节
     */
    public byte[]           byReserved = new byte[64];
}

