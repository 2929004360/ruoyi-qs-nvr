package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 图片描述信息
*/
public class NET_MAP_PIC_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 图片格式: png, jpg, bmp
    */
    public byte[]           szFormat = new byte[16];
    /**
     * 图片宽度
    */
    public int              nWidth;
    /**
     * 图片高度
    */
    public int              nHeight;
    /**
     * 保留字段
    */
    public byte[]           szReserved = new byte[512];

    public NET_MAP_PIC_INFO() {
    }
}

