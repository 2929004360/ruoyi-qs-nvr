package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 防护服相关属性状态信息
 * @date 2023/04/21 16:09:59
 */
public class NET_PROTECTIVE_SUIT_INFO extends NetSDKLib.SdkStructure {
    /**
     * 是否有穿着防护服, 0: 未知, 1: 没有, 2: 有, 3:不存在指定颜色的防护服
     */
    public int              nHasProtectiveSuit;
    /**
     * 防护服颜色 {@link com.ruoyi.dahua.lib.enumeration.EM_CLOTHES_COLOR}
     */
    public int              emProtectiveSuitColor;
    /**
     * 预留字节
     */
    public byte[]           szReserved = new byte[24];

    public NET_PROTECTIVE_SUIT_INFO() {
    }
}

