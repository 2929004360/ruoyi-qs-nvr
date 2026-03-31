package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  图片信息 
* @date 2022/06/28 19:24:08
*/
public class NET_IMAGE_INFO extends NetSDKLib.SdkStructure {
/** 
图片类型 {@link com.ruoyi.dahua.lib.enumeration.EM_PIC_TYPE}
*/
    public			int            emPicType;
/** 
在二进制数据块中的偏移
*/
    public			int            nOffset;
/** 
图片大小,单位:字节
*/
    public			int            nLength;
/** 
图片路径
*/
    public			byte[]         szFilePath = new byte[256];
/** 
预留字节
*/
    public			byte[]         byReserved = new byte[1024];

public NET_IMAGE_INFO(){
}
}

