package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @创建人 291189
 * @创建时间 2021/5/28
 * @描述
 */
public class CFG_AUTO_HOMING extends NetSDKLib.SdkStructure {
    /**
     * 自动归位开关
     */
    public int              bEnable;
    /**
     * 空闲时间,表示空闲的时间，单位为秒
     */
    public int              nTime;
}

