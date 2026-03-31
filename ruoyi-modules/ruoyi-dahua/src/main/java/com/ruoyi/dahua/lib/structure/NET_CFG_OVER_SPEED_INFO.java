package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 超速报警相关配置信息
*/
public class NET_CFG_OVER_SPEED_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 速度报警阈值,单位m/s
    */
    public double           dbSpeedLimit;
    /**
     * 加速度报警阈值，单位m/s^2
    */
    public double           dbAccelerationThreshold;
    /**
     * 超速报警使能
    */
    public int              bEnabled;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[512];

    public NET_CFG_OVER_SPEED_INFO() {
    }
}

