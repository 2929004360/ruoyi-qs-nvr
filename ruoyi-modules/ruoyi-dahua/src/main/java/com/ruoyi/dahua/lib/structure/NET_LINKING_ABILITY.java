package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 支持灯光联动的能力集
 * @origin autoTool
 * @date 2023/10/19 17:27:53
 */
public class NET_LINKING_ABILITY extends NetSDKLib.SdkStructure {
    /**
     * /支持的非智能事件
     */
    public int[]            emAnSupportEvents = new int[10];
    /**
     * /支持的非智能事件数量
     */
    public int              nSupportEventsLen;
    /**
     * /支持的智能规则
     */
    public int[]            emAnSupportIntelliScence = new int[40];
    /**
     * /支持的智能规则数量
     */
    public int              nSupportIntelliScenceLen;
    /**
     * / 保留字节
     */
    public byte[]           byReserved = new byte[128];

    public NET_LINKING_ABILITY() {
    }
}

