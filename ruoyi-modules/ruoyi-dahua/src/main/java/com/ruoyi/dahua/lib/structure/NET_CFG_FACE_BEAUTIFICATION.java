package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 人Lian美化
*/
public class NET_CFG_FACE_BEAUTIFICATION extends NetSDKLib.SdkStructure
{
    /**
     * 使能
    */
    public int              bEnable;
    /**
     * 降噪等级取值为0~100，默认值50, 等级0与人Lian美化使能不开效果一致，等级越高，降噪程度越大，对人Lian美化效果越高
    */
    public int              nBeautyLevel;
    /**
     * 0:手动，1：自动，默认手动
    */
    public int              nModelType;
    /**
     * 人Lian边缘锐度参数[0, 100]，默认50
    */
    public int              nEeLevel;
    /**
     * 黑边抑制，取值范围[0, 100] 默认 50，值越大，黑边越明显
    */
    public int              nBlackEdge;
    /**
     * 白边抑制，取值范围[0, 100],默认 50 ，值越大，白边越明显
    */
    public int              nWhiteEdge;
    /**
     * 亮度等级，取值[0,100], 默认50，值越大，亮度越高
    */
    public int              nBrightness;
    /**
     * 颜色等级，取值[0,100], 默认50，值越大，颜色越鲜艳
    */
    public int              nSaturation;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[128];

    public NET_CFG_FACE_BEAUTIFICATION() {
    }
}

