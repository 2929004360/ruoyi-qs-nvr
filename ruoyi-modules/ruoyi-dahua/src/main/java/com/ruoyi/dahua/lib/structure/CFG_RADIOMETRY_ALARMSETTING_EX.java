package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 测温点报警设置Ex
*/
public class CFG_RADIOMETRY_ALARMSETTING_EX extends NetSDKLib.SdkStructure
{
    /**
     * 预报警阈值
    */
    public float            fPreThreshold;
    /**
     * 预报警温度持续时间；单位：秒
    */
    public int              nPreDuration;
    /**
     * 预报警阈值使能
    */
    public int              bUsefPreThreshold;
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[252];

    public CFG_RADIOMETRY_ALARMSETTING_EX() {
    }
}

