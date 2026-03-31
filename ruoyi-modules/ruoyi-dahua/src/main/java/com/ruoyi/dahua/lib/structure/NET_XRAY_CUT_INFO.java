package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 切包信息
 * @origin autoTool
 * @date 2023/11/27 16:01:55
 */
public class NET_XRAY_CUT_INFO extends NetSDKLib.SdkStructure {
    /**
     * / 切包的设备本地时间戳，从1970-1-1 00:00:00开始的毫秒数
     */
    public long             nTimeMS;
    /**
     * / 保留字节
     */
    public byte[]           szReserved = new byte[56];

    public NET_XRAY_CUT_INFO() {
    }
}

