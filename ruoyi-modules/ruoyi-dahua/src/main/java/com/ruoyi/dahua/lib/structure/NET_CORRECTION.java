package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 灯光补偿信息
 * @origin autoTool
 * @date 2023/10/19 17:27:54
 */
public class NET_CORRECTION extends NetSDKLib.SdkStructure {
    /**
     * /是否支持灯光补偿
     */
    public int              bSupported;
    /**
     * /补偿范围最大值
     */
    public int              nRange;
    /**
     * / 保留字节
     */
    public byte[]           byReserved = new byte[128];

    public NET_CORRECTION() {
    }
}

