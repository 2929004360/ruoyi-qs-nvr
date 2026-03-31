package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 历史数据主题列表
 * @date 2022/04/20 10:16:55
 */
public class NET_THINGS_START_HISTORYDATA_TOPICS extends NetSDKLib.SdkStructure {
    /**
     * 历史数据具体内容，即历史数据分类由物模型定义决定,可以不填，默认下""
     */
    public byte[]           szTopics = new byte[32];
    /**
     * 保留字节
     */
    public byte[]           szReserve = new byte[256];
}

