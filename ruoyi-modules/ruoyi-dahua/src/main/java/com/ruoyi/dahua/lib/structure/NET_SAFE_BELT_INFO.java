package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 安全带相关属性状态信息
 * @date 2023/04/21 16:09:59
 */
public class NET_SAFE_BELT_INFO extends NetSDKLib.SdkStructure {
    /**
     * 是否穿安全带 , 0:未知，1:未穿安全带，2:穿了安全带
     */
    public int              nHasSafeBelt;
    /**
     * 安全带检测结果, 0-合规 1-不合规 2-未知
     */
    public int              nHasLegalSafeBelt;
    /**
     * 预留字节
     */
    public byte[]           szReserved = new byte[24];

    public NET_SAFE_BELT_INFO() {
    }
}

