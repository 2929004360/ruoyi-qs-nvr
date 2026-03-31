package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 姿态行为分析场景能力
 * @date 2021/03/31
 */
public class CFG_GENEAL_ATTITUDE_CAPS extends NetSDKLib.SdkStructure {
    /**
     *  支持的最大规则数
     */
    public int              nMaxRules;
    /**
     *  大类外部翻译展示名称
     */
    public byte[]           szShowType = new byte[32];
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[988];
}

