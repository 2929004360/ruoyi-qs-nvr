package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 区域异常防区信息扩展
*/
public class NET_AREA_STATUS_EX extends NetSDKLib.SdkStructure
{
    /**
     * 区域号
    */
    public int              nArea;
    /**
     * 防区个数扩展
    */
    public int              nZoneRetEx;
    /**
     * 防区异常状态,参见结构体定义 {@link NET_ZONE_STATUS}
    */
    public NET_ZONE_STATUS[] stuZoneStatusEx = new NET_ZONE_STATUS[256];
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[1024];

    public NET_AREA_STATUS_EX() {
        for(int i = 0; i < stuZoneStatusEx.length; i++){
            stuZoneStatusEx[i] = new NET_ZONE_STATUS();
        }
    }
}

