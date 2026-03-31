package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 设备中可用于提取信息的可用读头能力信息
*/
public class NET_DEV_COLLECT_ATTRIBUTE extends NetSDKLib.SdkStructure
{
    /**
     * 最大规则组数量, nTypes bit1=1时有效
    */
    public int              nDoorIndex;
    /**
     * 通道编号数量
    */
    public int              nReaderInfoNum;
    /**
     * 所挂门通道编号下面，可用于读取信息的读头编号,从1开始
    */
    public int[]            nReaderInfo = new int[16];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[128];

    public NET_DEV_COLLECT_ATTRIBUTE() {
    }
}

