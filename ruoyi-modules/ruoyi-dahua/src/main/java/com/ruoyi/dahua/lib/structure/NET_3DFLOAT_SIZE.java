package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;


/** 
* @author 291189
* @description  三维尺寸类型 
* @origin autoTool
* @date 2023/02/14 20:02:01
*/
public class NET_3DFLOAT_SIZE extends NetSDKLib.SdkStructure {
/** 
长度，单位米
*/
    public			float          fLength;
/** 
宽度，单位米
*/
    public			float          fWidth;
/** 
高度，单位米
*/
    public			float          fHeigth;
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[28];

public			NET_3DFLOAT_SIZE(){
}

    @Override
    public String toString() {
        return "{" +
                "fLength=" + fLength +
                ", fWidth=" + fWidth +
                ", fHeigth=" + fHeigth +
                '}';
    }
}

