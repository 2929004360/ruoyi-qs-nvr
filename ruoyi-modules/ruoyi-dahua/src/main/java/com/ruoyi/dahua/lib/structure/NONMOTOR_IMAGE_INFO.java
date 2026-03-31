package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 非机动车抠图信息
*/
public class NONMOTOR_IMAGE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 在二进制数据块中的偏移
    */
    public int              nOffset;
    /**
     * 图片大小,单位字节
    */
    public int              nLength;

    public NONMOTOR_IMAGE_INFO() {
    }
}

