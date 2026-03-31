package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 环信息配置
 * @date 2021/09/28
 */
public class NET_RINGS_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  环编号，从1开始
     */
    public int              nRingNo;
    /**
     *  栅栏信息个数
     */
    public int              nBarriersNum;
    /**
     *  栅栏信息
     */
    public NET_BARRIERS_INFO[] stuBarriesInfo = (NET_BARRIERS_INFO[]) new NET_BARRIERS_INFO().toArray(4);
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

