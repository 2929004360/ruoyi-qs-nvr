package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 区域流量统计信息
*/
public class NET_FLOW_PACKET_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 区域ID
    */
    public int              nAreaID;
    /**
     * 区域是否统计行人、非机动车bit0：统计区域内行人数量，1表示统计，0表示不统计 bit1：统计区域内非机动车数量，1表示统计，0表示不统计
    */
    public int              nAreaMask;
    /**
     * 区域内非机动车数量，AreaMask的bit1位为1时有效
    */
    public int              nNonMotorNum;
    /**
     * 区域内行人数量，AreaMask的bit0位为1时有效
    */
    public int              nPedNum;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[512];

    public NET_FLOW_PACKET_INFO() {
    }
}

