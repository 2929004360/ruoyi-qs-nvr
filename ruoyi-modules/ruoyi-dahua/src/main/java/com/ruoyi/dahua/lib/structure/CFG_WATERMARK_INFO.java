package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 水印配置
*/
public class CFG_WATERMARK_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号(0开始)
    */
    public int              nChannelID;
    /**
     * 使能开关
    */
    public int              bEnable;
    /**
     * 码流类型(1～n)，0－所有码流
    */
    public int              nStreamType;
    /**
     * 数据类型，1－文字，2－图片
    */
    public int              nDataType;
    /**
     * 字符串水印数据
    */
    public byte[]           pData = new byte[4096];

    public CFG_WATERMARK_INFO() {
    }
}

