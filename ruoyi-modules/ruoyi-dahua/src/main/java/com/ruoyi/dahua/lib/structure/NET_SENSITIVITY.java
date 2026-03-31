package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 灯光灵敏度信息
 * @origin autoTool
 * @date 2023/10/19 17:27:54
 */
public class NET_SENSITIVITY extends NetSDKLib.SdkStructure {
    /**
     * /是否支持灯光灵敏度
     */
    public int              bSupported;
    /**
     * /灯光灵敏度最大值
     */
    public int              nRange;
    /**
     * / 保留字节
     */
    public byte[]           byReserved = new byte[128];

    public NET_SENSITIVITY() {
    }
}

