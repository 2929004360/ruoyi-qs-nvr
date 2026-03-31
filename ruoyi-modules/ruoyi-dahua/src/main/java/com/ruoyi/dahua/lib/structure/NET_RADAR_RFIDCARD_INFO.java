package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 260611
 * @description ： RFID卡片信息
 * @since ： Created in 2022/02/11 15:37
 */

public class NET_RADAR_RFIDCARD_INFO extends NetSDKLib.SdkStructure {
    /**
     * 卡片ID
     */
    public byte[]           szCardID = new byte[24];
    /**
     * 卡片行为, 参考 NET_EM_RADAR_RFIDCARD_ACTION
     */
    public int              emCardAction;
    /**
     * 卡片进入时间的UTC时间戳
     */
    public int              nCardEntryTime;
    /**
     * 卡片离开时间的UTC时间戳，卡片未离开则为0
     */
    public int              nCardQuitTime;
    /**
     * 保留字节
     */
    public byte[]           byReserved = new byte[116];
}

