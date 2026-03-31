package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 通用配置信息
 * @date 2021/09/28
 */
public class NET_COMMON_CONFIG_INFO extends NetSDKLib.SdkStructure {
	/**
     *  溢出控制占有率阈值
     */
    public float            fRatioLimit;
    /**
     *  进入溢出控制占有率满足次数
     */
    public int              nInOCTimes;
    /**
     *  跌出溢出控制占有率满足次数
     */
    public int              nOutOCTimes;
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

