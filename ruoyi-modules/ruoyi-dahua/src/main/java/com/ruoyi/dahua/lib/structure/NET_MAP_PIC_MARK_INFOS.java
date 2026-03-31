package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 地图已标记信息
*/
public class NET_MAP_PIC_MARK_INFOS extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannel;
    /**
     * 地图位置点信息,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX     stuPosition = new NET_POINT_EX();
    /**
     * 保留字段
    */
    public byte[]           szReserved = new byte[128];

    public NET_MAP_PIC_MARK_INFOS() {
    }
}

