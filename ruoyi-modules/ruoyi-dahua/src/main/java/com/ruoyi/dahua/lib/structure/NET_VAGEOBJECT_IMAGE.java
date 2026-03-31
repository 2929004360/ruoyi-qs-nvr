package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 物体截图
 * @date 2021/10/09
 */
public class NET_VAGEOBJECT_IMAGE extends NetSDKLib.SdkStructure {
	 /**
     *  在二进制数据块中的偏移
     */
    public int              nOffset;
    /**
     *  图片大小,单位：字节
     */
    public int              nLength;
    /**
     *  图片宽度
     */
    public int              nWidth;
    /**
     *  图片高度
     */
    public int              nHeight;
    /**
     *  文件路径
     */
    public byte[]           szFilePath = new byte[260];
}

