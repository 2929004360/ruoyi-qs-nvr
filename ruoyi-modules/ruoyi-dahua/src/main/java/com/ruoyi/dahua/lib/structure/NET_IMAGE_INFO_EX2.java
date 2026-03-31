package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 图片类型
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 18:04
 */
public class NET_IMAGE_INFO_EX2 extends NetSDKLib.SdkStructure {
    public int              emType;                               // 图片类型
    /**
     *  在二进制数据块中的偏移
     */
    public int              nOffset;
    /**
     *  图片大小,单位:字节
     */
    public int              nLength;
    /**
     *  用于字节对齐
     */
    public byte             byReserverd[] = new byte[4];
    /**
     *  图片存储位置
     */
    public byte             szPath[] = new byte[256];
}

