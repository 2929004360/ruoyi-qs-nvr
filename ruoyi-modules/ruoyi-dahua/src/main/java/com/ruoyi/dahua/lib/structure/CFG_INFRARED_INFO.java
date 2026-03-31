package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.EM_INFRARED_FUNC_MODE;
import com.ruoyi.dahua.lib.enumeration.EM_INFRARED_LIGHT_LEVEL;

/**
 * 红外功能配置
 * 对应配置项 {@link NetSDKLib#CFG_CMD_INFRARED_CONFIG}
 */
public class CFG_INFRARED_INFO extends NetSDKLib.SdkStructure {
    /**
     * 红外功能模式 枚举值 {@link EM_INFRARED_FUNC_MODE}
     */
    public int              emInfraredMode;
    /**
     * 红外亮度 枚举值 {@link EM_INFRARED_LIGHT_LEVEL}
     * 当且仅当 emInfraredMode 为 EM_INFRARED_FUNC_MODE_ON 即枚举值 1 时有效
     */
    public int              emLightLevel;
    /**
     * 保留字节
     */
    public byte[]           szReserved = new byte[1024];
}

