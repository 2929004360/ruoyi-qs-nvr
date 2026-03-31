package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 自动控制信息
*/
public class NET_SLEEPING_CONTROL_AUTO_CTRL extends NetSDKLib.SdkStructure
{
    /**
     * 低电休眠开关
    */
    public int              bPowerSaveEnable;
    /**
     * 自动进入休眠模式的电量百分比阈值，取值范围[1,100]
    */
    public int              nSleepElectricity;
    /**
     * 保留字节
    */
    public byte[]           szResvered = new byte[256];

    public NET_SLEEPING_CONTROL_AUTO_CTRL() {
    }
}

