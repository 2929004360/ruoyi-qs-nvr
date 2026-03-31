package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

import static com.ruoyi.dahua.lib.NetSDKLib.NET_COMMON_STRING_128;

/**
 * 电视墙使能信息
 *
 * @author ： 47040
 * @since ： Created in 2020/10/19 11:17
 */
public class NET_MONITORWALL_ENABLE_INFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 使能
     */
    public int              bEnable;
    /**
     * 电视墙名称
     */
    public byte[]           szName = new byte[NET_COMMON_STRING_128];

    public NET_MONITORWALL_ENABLE_INFO() {
        dwSize = this.size();
    }
}

