package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 安全绳相关属性状态信息
 * @date 2023/04/21 16:09:59
 */
public class NET_SAFETY_ROPE_INFO extends NetSDKLib.SdkStructure {
    /**
     * 佩戴带安全绳是否合规, 0-不合规 1-合规 2-未知
     */
    public int              nCompliantType;
    /**
     * 是否有安全绳 0:未知 1：无            2：有,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_HAS_SAFE_ROPE}
    */
    public int              emHasSafeRope;
    /**
     * 预留字节
     */
    public byte[]           szReserved = new byte[24];

    public NET_SAFETY_ROPE_INFO() {
    }
}

