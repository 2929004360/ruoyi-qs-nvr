package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 晃动检测相关配置信息
*/
public class NET_CFG_SWAY_ABNORMAL_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 晃动检测使能项
    */
    public int              bEnabled;
    /**
     * 灵敏度
    */
    public int              nSensitivity;
    /**
     * 左右晃动检测阈值,单位m/s^2
    */
    public double           dbLeftAndRightThreshold;
    /**
     * 前后晃动检测阈值,单位m/s^2
    */
    public double           dbFrontAndBackThreshold;
    /**
     * 左上下晃动检测阈值,单位m/s^2
    */
    public double           dbUpAndDownThreshold;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[512];

    public NET_CFG_SWAY_ABNORMAL_INFO() {
    }
}

