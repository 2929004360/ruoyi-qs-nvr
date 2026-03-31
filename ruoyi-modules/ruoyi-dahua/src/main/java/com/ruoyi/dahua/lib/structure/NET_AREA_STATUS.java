package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 区域异常防区信息
*/
public class NET_AREA_STATUS extends NetSDKLib.SdkStructure
{
    /**
     * 区域号
    */
    public int              nArea;
    /**
     * 防区个数
    */
    public int              nZoneRet;
    /**
     * 防区异常状态,参见结构体定义 {@link NET_ZONE_STATUS}
    */
    public NET_ZONE_STATUS[] stuZoneStatus = new NET_ZONE_STATUS[72];
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[1024];

    public NET_AREA_STATUS() {
        for(int i = 0; i < stuZoneStatus.length; i++){
            stuZoneStatus[i] = new NET_ZONE_STATUS();
        }
    }
}

