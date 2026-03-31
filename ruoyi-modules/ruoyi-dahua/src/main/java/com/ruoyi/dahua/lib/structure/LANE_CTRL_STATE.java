package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 车道/匝道控制状态
 * @date 2021/09/01
 */
public class LANE_CTRL_STATE extends NetSDKLib.SdkStructure {
	 /**
     *  车道/匝道数量
     */
    public int              nNumber;
    /**
     *  控制信息个数
     */
    public int              nCtrlNum;
    /**
     *  控制信息(0-64) 
     */
    public CTRL_INFO[]      stuCtrlInfo = (CTRL_INFO[]) new CTRL_INFO().toArray(64);
    /**
     *  预留字节
     */
    public byte[]           byReserved = new byte[128];
}

