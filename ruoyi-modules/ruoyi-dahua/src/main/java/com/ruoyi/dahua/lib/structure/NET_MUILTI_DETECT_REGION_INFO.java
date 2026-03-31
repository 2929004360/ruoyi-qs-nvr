package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 多个检测区域
*/
public class NET_MUILTI_DETECT_REGION_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 检测区ID
    */
    public int              nDetectRegionID;
    /**
     * 检测区域数量
    */
    public int              nDetectRegionNum;
    /**
     * 检测区,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX[]   stuDetectRegion = new NET_POINT_EX[20];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_MUILTI_DETECT_REGION_INFO() {
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NET_POINT_EX();
        }
    }
}

