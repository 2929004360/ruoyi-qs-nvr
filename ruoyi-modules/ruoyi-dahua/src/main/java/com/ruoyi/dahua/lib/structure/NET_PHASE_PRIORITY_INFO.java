package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 相位优先配置
 * @date 2021/09/28
 */
public class NET_PHASE_PRIORITY_INFO extends NetSDKLib.SdkStructure {
	/**
     *  相位编号
     */
    public int              nPhaseNum;
    /**
     *  相位重复出现的次数
     */
    public int              nPhaseOrder;
    /**
     *  是否优先
     */
    public int              bPriority;
    /**
     *  预留字节 
     */
    public byte[]           szReserved = new byte[32];
}

