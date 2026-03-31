package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 规则温度信息
*/
public class NET_RADIOMETRY_TEMP_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 返回测温类型
    */
    public byte[]           szType = new byte[16];
    /**
     * 温度单位(当前配置的温度单位)
    */
    public byte[]           szTemperatureUnit = new byte[16];
    /**
     * 规则名称
    */
    public byte[]           szName = new byte[64];
    /**
     * 点的温度或者平均温度
    */
    public float            fTemperAver;
    /**
     * 最高温度
    */
    public float            fTemperMax;
    /**
     * 最低温度
    */
    public float            fTemperMin;
    /**
     * 预置点编号
    */
    public int              nPresetId;
    /**
     * 规则编号
    */
    public int              nRuleId;
    /**
     * 规则框里面黑体靶面大小
    */
    public int              nBlackBodySize;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_RADIOMETRY_TEMP_INFO() {
    }
}

