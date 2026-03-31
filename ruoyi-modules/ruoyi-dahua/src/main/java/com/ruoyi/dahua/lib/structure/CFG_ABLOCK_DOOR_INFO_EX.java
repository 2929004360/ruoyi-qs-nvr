package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 门禁的AB互锁的组
*/
public class CFG_ABLOCK_DOOR_INFO_EX extends NetSDKLib.SdkStructure
{
    /**
     * 有效互锁门的个数
    */
    public int              nDoorCount;
    /**
     * 互锁的门的通道号
    */
    public int[]            nDoor = new int[32];
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[68];

    public CFG_ABLOCK_DOOR_INFO_EX() {
    }
}

