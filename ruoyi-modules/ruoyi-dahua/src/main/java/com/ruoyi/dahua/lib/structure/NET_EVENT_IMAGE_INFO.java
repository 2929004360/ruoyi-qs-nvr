package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421557
 * @description 事件抠图信息
 * @origin autoTool
 * @date 2023/10/23 11:00:18
 */
public class NET_EVENT_IMAGE_INFO extends NetSDKLib.SdkStructure {
    /**
     * / 图片长度
     */
    public int              nImageLength;
    /**
     * / 图片路径
     */
    public byte[]           szImagePath = new byte[128];
    /**
     * / 保留字段
     */
    public byte[]           bReserved = new byte[256];

    public NET_EVENT_IMAGE_INFO() {
    }
}

