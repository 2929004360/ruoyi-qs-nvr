package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetWaterRadarObjectInfo 输出参数
*/
public class NET_OUT_GET_WATERRADAR_OBJECTINFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 水位 单位 cm
    */
    public float            fWaterLevel;

    public NET_OUT_GET_WATERRADAR_OBJECTINFO() {
        this.dwSize = this.size();
    }
}

