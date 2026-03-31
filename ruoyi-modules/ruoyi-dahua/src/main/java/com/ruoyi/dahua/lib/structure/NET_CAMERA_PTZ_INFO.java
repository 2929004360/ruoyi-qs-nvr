package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
获取角度
*/
public class NET_CAMERA_PTZ_INFO extends NetSDKLib.SdkStructure {
/** 
预置点的坐标和放大倍数
*/
    public PTZ_SPACE_UNIT   stuPosition = new PTZ_SPACE_UNIT();
/** 
热成像横向视场角,单位度,实际角度乘以100
*/
    public			int            nAngelH;
/** 
热成像纵向视场角,单位度,实际角度乘以100
*/
    public			int            nAngelV;
/** 
预留字段
*/
    public			byte[]         szreserved = new byte[60];
}

