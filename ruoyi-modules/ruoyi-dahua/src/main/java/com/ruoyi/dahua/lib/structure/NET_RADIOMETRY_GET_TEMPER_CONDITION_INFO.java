package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 获取测温项温度的条件
*/
public class NET_RADIOMETRY_GET_TEMPER_CONDITION_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 测温项的名称
    */
    public byte[]           szName = new byte[64];
    /**
     * 预置点编号
    */
    public int              nPresetId;
    /**
     * 规则编号
    */
    public int              nRuleId;
    /**
     * 通道号
    */
    public int              nChannel;
    /**
     * 返回测温类型,参见枚举定义 EM_RADIOMETRY_TEMPER_TYPE
    */
    public int              emType;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_RADIOMETRY_GET_TEMPER_CONDITION_INFO() {
    }
}

