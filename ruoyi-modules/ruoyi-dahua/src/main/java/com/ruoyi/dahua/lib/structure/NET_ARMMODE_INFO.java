package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 布撤防信息
 * @date 2021/11/08
 */
public class NET_ARMMODE_INFO extends NetSDKLib.SdkStructure {
    /**
     *  布撤防状态,参考枚举{ @link EM_ARM_STATE}
     */
    public int              emArmState;
    /**
     *  保留字节
     */
    public byte[]           byReserved = new byte[1024];
}

