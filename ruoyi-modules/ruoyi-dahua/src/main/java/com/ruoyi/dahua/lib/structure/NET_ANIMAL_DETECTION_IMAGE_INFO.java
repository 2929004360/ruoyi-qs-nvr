package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 抠图信息
*/
public class NET_ANIMAL_DETECTION_IMAGE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 抠图长度
    */
    public int              nLength;
    /**
     * 抠图路径
    */
    public byte[]           szFilePath = new byte[128];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[252];

    public NET_ANIMAL_DETECTION_IMAGE_INFO() {
    }
}

