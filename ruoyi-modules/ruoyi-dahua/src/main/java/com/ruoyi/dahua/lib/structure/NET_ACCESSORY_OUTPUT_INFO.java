package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 输出信息
*/
public class NET_ACCESSORY_OUTPUT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 输出状态, 0:未知, 1:关闭, 2:开启
    */
    public int              nStatus;
    /**
     * 输出模式, 0:未知, 1:稳态, 2:脉冲
    */
    public int              nMode;
    /**
     * 脉冲持续时间（输出模块为脉冲时生效）
    */
    public int              nPulseDuration;
    /**
     * 预留字段
    */
    public byte[]           szReserved = new byte[64];

    public NET_ACCESSORY_OUTPUT_INFO() {
    }
}

