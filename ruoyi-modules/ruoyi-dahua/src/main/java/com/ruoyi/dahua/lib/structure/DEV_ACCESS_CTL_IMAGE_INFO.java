package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description 图片信息
* @date 2022/03/07 11:45:31
*/
public class DEV_ACCESS_CTL_IMAGE_INFO extends NetSDKLib.SdkStructure {
/** 
图片类型  {@link NetSDKLib.EM_ACCESS_CTL_IMAGE_TYPE}
*/
    public			int            emType;
/** 
二进制块偏移字节
*/
    public			int            nOffSet;
/** 
图片大小
*/
    public			int            nLength;
/** 
图片宽度(单位:像素)
*/
    public			int            nWidth;
/** 
图片高度(单位:像素)
*/
    public			int            nHeight;
/** 
包围盒
*/
    public NET_RECT         stuBoundingBox = new NET_RECT();
/** 
保留字节
*/
    public			byte[]         byReserved = new byte[48];
}

