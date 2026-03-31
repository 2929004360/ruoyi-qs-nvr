package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  辅车牌信息 
* @date 2022/05/30 14:16:16
*/
public class NET_EXTRA_PLATES extends NetSDKLib.SdkStructure {
/** 
车牌图片在二进制数据内偏移，单位字节
*/
    public			int            nOffset;
/** 
车牌图片长度，单位字节
*/
    public			int            nLength;
/** 
辅车牌号码，UTF8格式
*/
    public			byte[]         szText = new byte[64];
/** 
车牌类型 {@link com.ruoyi.dahua.lib.enumeration.EM_NET_PLATE_TYPE}
*/
    public			int            emCategory;
/** 
车牌颜色 {@link com.ruoyi.dahua.lib.enumeration.EM_NET_PLATE_COLOR_TYPE}
*/
    public			int            emColor;
/** 
辅车牌的包围盒，坐标已算上黑边高度车牌矩形框，绝对坐标，即真正的像素点坐标
*/
    public NET_RECT         stuArea = new NET_RECT();
/** 
预留字节
*/
    public			byte[]         bReserved = new byte[32];

public			NET_EXTRA_PLATES(){
}
}

