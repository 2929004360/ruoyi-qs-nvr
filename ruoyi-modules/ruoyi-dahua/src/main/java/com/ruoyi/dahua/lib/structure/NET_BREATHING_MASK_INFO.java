package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 呼吸面罩相关属性状态信息
 * @date 2023/04/21 16:09:59
 */
public class NET_BREATHING_MASK_INFO extends NetSDKLib.SdkStructure {
    /**
     * 呼吸面罩检测结果, 0:未知，1:不合规，2:合规
     */
    public int              nHasLegalBreathingMask;
    /**
     * 是否有穿着呼吸面罩, 0: 未知 1: 没有 2: 有
     */
    public int              nHasBreathingMask;
    /**
     * 预留字节
     */
    public byte[]           byReserved = new byte[56];

    public NET_BREATHING_MASK_INFO() {
    }
}

