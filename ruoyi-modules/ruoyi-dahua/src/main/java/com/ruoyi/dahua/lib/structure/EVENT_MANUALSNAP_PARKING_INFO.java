package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 停车位数据信息
 * @date 2023/01/11 15:02:23
 */
public class EVENT_MANUALSNAP_PARKING_INFO extends NetSDKLib.SdkStructure {
    /**
     * 通道号，从0开始, -1表示未知通道
     */
    public int              nChannel;
    /**
     * 车位是否有车标记：0：未知 1：有车 2：无车
     */
    public int              nStatus;
    /**
     * 车牌号码
     */
    public byte[]           szPlateNumber = new byte[64];
    /**
     * 车牌号码
     */
    public byte[]           szParkingNo = new byte[32];
    /**
     * 保留字节
     */
    public byte[]           szReserved = new byte[128];

    public EVENT_MANUALSNAP_PARKING_INFO() {
    }
}

