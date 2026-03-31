package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 260611
 * @description ： 雷达RFID卡片信息配置
 * @since ： Created in 2022/02/16 14:42
 */

public class NET_CFG_RADAR_RFIDCARD_INFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * RFID卡片数量
     */
    public int              nCardNum;
    /**
     * RFID卡片配置信息
     */
    public NET_RADAR_RFIDCARD_CONFIG[] stuCardInfo = (NET_RADAR_RFIDCARD_CONFIG[]) new NET_RADAR_RFIDCARD_CONFIG().toArray(256);

    public NET_CFG_RADAR_RFIDCARD_INFO() {
        this.dwSize = this.size();
    }
}

