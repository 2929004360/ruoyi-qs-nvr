package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 栅栏信息配置
 * @date 2021/09/28
 */
public class NET_BARRIERS_INFO extends NetSDKLib.SdkStructure {
	/**
     *  栅栏编号，从1开始
     */
    public int              nBarrierNo;
    /**
     *  相位列表个数
     */
    public int              nPhaseNum;
    /**
     *  相位列表
     */
    public NET_PHASES_INFO[] stuPhaseInfo = (NET_PHASES_INFO[]) new NET_PHASES_INFO().toArray(16);
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

