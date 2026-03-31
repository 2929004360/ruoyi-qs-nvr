package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 排除区域信息
*/
public class NET_EXCLUDE_REGION_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 排除区域,参见结构体定义 {@link NET_CFG_POLYGON}
    */
    public NET_CFG_POLYGON[] stuExcludeRegion = new NET_CFG_POLYGON[20];
    /**
     * 排除区域有效顶点数
    */
    public int              nDetectRegionPoint;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[60];

    public NET_EXCLUDE_REGION_INFO() {
        for(int i = 0; i < stuExcludeRegion.length; i++){
            stuExcludeRegion[i] = new NET_CFG_POLYGON();
        }
    }
}

