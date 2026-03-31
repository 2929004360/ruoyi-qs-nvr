package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 属性计划表信息
 * @date 2021/09/28
 */
public class NET_PROPERTY_PLAN_LIST_INFO extends NetSDKLib.SdkStructure {
	/**
     *  该指示牌支持的可变属性，最多支持七种，0表示未配置（1：左转2：直行（潮汐）3：右转4：掉头5：禁行（匝道）6：通行（匝道）7：反向（潮汐））
     */
    public int              nVarlaneProperty;
    /**
     *  可变车道对应属性的检测时间
     */
    public int              nVarlaneDectTime;
    /**
     *  可变属性的排队差（米）
     */
    public int              nQueueDissimilarity;
    /**
     *  可变属性的判断起始排队长（米）
     */
    public int              nQueueLen;
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

