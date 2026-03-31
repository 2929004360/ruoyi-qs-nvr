package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 车身抠图
 * @date 2022/07/20 16:46:40
 */
public class NET_TRAFFICCAR_ORIGINAL_VEHICLE extends NetSDKLib.SdkStructure {
    /**
     * 在二进制数据块中的偏移
     */
    public int              nOffset;
    /**
     * 数据大小,单位：字节
     */
    public int              nLength;
    /**
     * 在上传图片数据中的图片序号
     */
    public int              nIndexInData;
    /**
     * 保留字节
     */
    public byte[]           szReserved = new byte[60];

    public NET_TRAFFICCAR_ORIGINAL_VEHICLE() {
    }
}

