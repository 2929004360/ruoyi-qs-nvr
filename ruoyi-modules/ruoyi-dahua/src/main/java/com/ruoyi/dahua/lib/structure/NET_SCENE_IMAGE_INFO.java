package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 全景广角图
*/
public class NET_SCENE_IMAGE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 在二进制数据块中的偏移
    */
    public int              nOffSet;
    /**
     * 图片大小,单位字节
    */
    public int              nLength;
    /**
     * 预留字节
    */
    public byte[]           byReserved = new byte[128];

    public NET_SCENE_IMAGE_INFO() {
    }
}

