package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 劳保鞋相关属性状态信息
 * @date 2023/04/21 16:09:59
 */
public class NET_SAFETY_SHOES_INFO extends NetSDKLib.SdkStructure {
    /**
     * 是否穿劳保鞋, 0:未知，1:未穿劳保鞋，2:穿了劳保鞋
     */
    public int              nHasSafetyShoes;
    /**
     * 劳保鞋检测结果 , 0-合规 1-不合规 2-未知
     */
    public int              nHasLegalSafetyShoes;
    /**
     * 预留字节
     */
    public byte[]           szReserved = new byte[24];

    public NET_SAFETY_SHOES_INFO() {
    }
}

