package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 结果详细信息
 * @date 2022/11/21 17:53:18
 */
public class NET_COUNT_DETAIL_INFO extends NetSDKLib.SdkStructure {
    /**
     * 图片的编号(0开始)
     */
    public int              nPictureID;
    /**
     * 该图片的结果数量
     */
    public int              nCount;
    /**
     * 保留字节
     */
    public byte[]           szReserved = new byte[256];

    public NET_COUNT_DETAIL_INFO() {
    }
}

