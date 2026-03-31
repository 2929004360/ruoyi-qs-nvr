package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * @author 291189
 * @version 1.0
 * @description  关联图片
 * @date 2021/9/9
 */
public class RELATED_IMAGE extends NetSDKLib.SdkStructure {
 /** 图片类型	0表示包裹所有者的人脸图片 1表示包裹违禁品图片*/
    public			int            nImageType;
 /** 在二进制数据块中的偏移*/
    public			int            nOffset;
 /** 图片大小,单位：字节*/
    public			int            nLength;
    public  byte[]          szReserved = new byte[128];           // 预留字节
}

