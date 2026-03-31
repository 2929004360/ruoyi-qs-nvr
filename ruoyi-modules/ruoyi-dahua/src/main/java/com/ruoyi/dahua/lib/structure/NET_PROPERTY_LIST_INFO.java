package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 可变车道属性信息
 * @date 2021/09/28
 */
public class NET_PROPERTY_LIST_INFO extends NetSDKLib.SdkStructure {
	/**
     *  该指示牌支持的可变属性，最多支持七种，0表示未配置（1：左转2：直行（潮汐）3：右转4：掉头5：禁行（匝道）6：通行（匝道）7：反向（潮汐））
     */
    public int              nVarlaneProperty;
    /**
     *  该指示牌对应可变属性的对应通道编号
     */
    public int              nVarlaneTochanelNum;
    /**
     *  该指示牌对应可变属性的对应的通道的灯色（1：红灯2：黄灯3：绿灯）
     */
    public int              nVarlaneTochanelColor;
    /**
     *  该指示牌对应可变属性的对应的检测器编号（对应检测器表）
     */
    public int              nVarlanevehDectNumber;
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

