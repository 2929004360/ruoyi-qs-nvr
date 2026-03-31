package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 普通配置本地GUI锁定策略
*/
public class CFG_LOCAL_POLICY_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 登陆失败可尝试次数使能
    */
    public int              bLockLoginEnable;
    /**
     * 清零周期，如果规定时间内登陆次数未超过可尝试次数，尝试次数清零
    */
    public int              nCheckDuration;
    /**
     * 登陆失败可尝试次数
    */
    public int              nLockLoginTimes;
    /**
     * 登陆失败锁定时间
    */
    public int              nLoginFailLockTime;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[128];

    public CFG_LOCAL_POLICY_INFO() {
    }
}

