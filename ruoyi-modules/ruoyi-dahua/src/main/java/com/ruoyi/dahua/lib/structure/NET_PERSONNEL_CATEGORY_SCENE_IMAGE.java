package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 全景广角图
*/
public class NET_PERSONNEL_CATEGORY_SCENE_IMAGE extends NetSDKLib.SdkStructure
{
    /**
     * 在上传图片数据中的图片序号
    */
    public int              nIndexInData;
    /**
     * 在二进制数据块中的偏移
    */
    public int              nOffSet;
    /**
     * 图片大小,单位字节
    */
    public int              nLength;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[108];

    public NET_PERSONNEL_CATEGORY_SCENE_IMAGE() {
    }
}

