package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 历史库人体图片信息
*/
public class NET_HISTORY_HUMAN_IMAGE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 图片大小,单位:字节
    */
    public int              nLength;
    /**
     * 图片宽度
    */
    public int              nWidth;
    /**
     * 图片高度
    */
    public int              nHeight;
    /**
     * 文件路径
    */
    public byte[]           szFilePath = new byte[260];

    public NET_HISTORY_HUMAN_IMAGE_INFO() {
    }
}

