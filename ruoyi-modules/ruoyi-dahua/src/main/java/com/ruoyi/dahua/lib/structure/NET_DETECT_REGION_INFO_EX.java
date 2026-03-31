package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 检测区域信息
*/
public class NET_DETECT_REGION_INFO_EX extends NetSDKLib.SdkStructure
{
    /**
     * 检测区域个数
    */
    public int              nDetectRegionCount;
    /**
     * 检测区域,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX[]   stuDetectRegion = new NET_POINT_EX[20];
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[428];

    public NET_DETECT_REGION_INFO_EX() {
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NET_POINT_EX();
        }
    }
}

