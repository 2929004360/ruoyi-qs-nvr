package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * mask输出信息类型映射
*/
public class NET_GROUND_THING_DETECTION_MAP_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 像素值掩码，1天空、2光伏、3水体、4道路、5建筑物、6耕地、7林地、8不透水地面、9其他
    */
    public int              nPixelValue;
    /**
     * pixelValue映射的目标粗类型，类型为VideoAnalyseObject，目前只会用到粗类型
    */
    public int              nCoarseType;
    /**
     * pixelValue映射的目标细类型，见coarseType对应的类型
    */
    public int              nSubType;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[60];

    public NET_GROUND_THING_DETECTION_MAP_INFO() {
    }
}

