package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 设置车位指示灯亮灯计划出参
*/
public class NET_OUT_SET_PARKING_SPACE_LIGHT_PLAN extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;

    public NET_OUT_SET_PARKING_SPACE_LIGHT_PLAN() {
        this.dwSize = this.size();
    }
}

