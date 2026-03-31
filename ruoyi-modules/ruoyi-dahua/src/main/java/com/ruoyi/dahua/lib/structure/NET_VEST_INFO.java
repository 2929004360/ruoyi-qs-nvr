package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 反光背心相关属性状态信息
 * @date 2023/04/21 16:09:59
 */
public class NET_VEST_INFO extends NetSDKLib.SdkStructure {
    /**
     * 是否穿反光背心, 0:未知，1:未穿反光背心，2:穿了反光背心
     */
    public int              nHasVest;
    /**
     * 反光背心检测结果, 0-合规 1-不合规 2-未知
     */
    public int              nHasLegalVest;
    /**
     * 预留字节
     */
    public byte[]           szReserved = new byte[24];

    public NET_VEST_INFO() {
    }
}

