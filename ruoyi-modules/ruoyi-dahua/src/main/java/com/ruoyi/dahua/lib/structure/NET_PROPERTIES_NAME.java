package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 物模型标识
 * @date 2022/04/20 11:31:59
 */
public class NET_PROPERTIES_NAME extends NetSDKLib.SdkStructure {
    /**
     * "*","BJPZ_DDBJLDMS"、"SBJCXX_CPLX"、"SBJCXX_SBLX"等
     */
    public byte[]           szPropertiesName = new byte[64];
    /**
     * 预留字节
     */
    public byte[]           szReserved = new byte[1024];
}

