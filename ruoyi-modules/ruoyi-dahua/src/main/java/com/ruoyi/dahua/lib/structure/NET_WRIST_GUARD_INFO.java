package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 防割护腕相关属性状态信息
 * @date 2023/04/21 16:09:59
 */
public class NET_WRIST_GUARD_INFO extends NetSDKLib.SdkStructure {
    /**
     * 是否穿防割护腕, 0:未知，1:未穿防割护腕，2:穿了防割护腕
     */
    public int              nHasWristGuard;
    /**
     * 防割护腕检测结果 , 0-合规 1-不合规 2-未知
     */
    public int              nHasLegalWristGuard;
    /**
     * 预留字节
     */
    public byte[]           szReserved = new byte[24];

    public NET_WRIST_GUARD_INFO() {
    }
}

