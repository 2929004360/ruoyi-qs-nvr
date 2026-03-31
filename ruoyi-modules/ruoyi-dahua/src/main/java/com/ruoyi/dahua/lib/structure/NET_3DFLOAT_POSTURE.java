package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;


/** 
* @author 291189
* @description  3D姿态 
* @origin autoTool
* @date 2023/02/14 20:02:01
*/
public class NET_3DFLOAT_POSTURE extends NetSDKLib.SdkStructure {
/** 
偏航角，单位度
*/
    public			float          fYaw;
/** 
俯仰角，单位度
*/
    public			float          fPitch;
/** 
翻滚角，单位度
*/
    public			float          fRool;
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[28];

public			NET_3DFLOAT_POSTURE(){
}

    @Override
    public String toString() {
        return "{" +
                "fYaw=" + fYaw +
                ", fPitch=" + fPitch +
                ", fRool=" + fRool +
                '}';
    }
}

