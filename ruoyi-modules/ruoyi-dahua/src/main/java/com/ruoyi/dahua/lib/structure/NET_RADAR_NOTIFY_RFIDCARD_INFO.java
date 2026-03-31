package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 260611
 * @description ： 上报的雷达RFID信息
 * @since ： Created in 2022/02/11 15:37
 */

public class NET_RADAR_NOTIFY_RFIDCARD_INFO extends NetSDKLib.SdkStructure {
    /**
     *
     */
    public int              nChannel;
    /**
     *
     */
    public int              nCardNum;
    /**
     * RFID卡片信息
     */
    public NET_RADAR_RFIDCARD_INFO[] stuCardInfo = (NET_RADAR_RFIDCARD_INFO[]) new NET_RADAR_RFIDCARD_INFO().toArray(256);
    /**
     * 保留字节
     */
    public byte[]           byReserved = new byte[1024];
}

