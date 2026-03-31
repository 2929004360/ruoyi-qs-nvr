package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 显示区域相对于原显示窗口的坐标
*/
public class NET_DISPLAYRREGION extends NetSDKLib.SdkStructure
{
    /**
     * 显示区域相对于原显示窗口的左坐标
    */
    public double           dleft;
    /**
     * 显示区域相对于原显示窗口的右坐标
    */
    public double           dright;
    /**
     * 显示区域相对于原显示窗口的上坐标
    */
    public double           dtop;
    /**
     * 显示区域相对于原显示窗口的下坐标
    */
    public double           dbottom;

    public NET_DISPLAYRREGION() {
    }
}

