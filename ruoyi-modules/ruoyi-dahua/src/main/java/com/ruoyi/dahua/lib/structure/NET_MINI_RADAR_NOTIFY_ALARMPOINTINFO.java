package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 上报的mini雷达报警点信息
 * @date 2022/08/04 17:03:46
 */
public class NET_MINI_RADAR_NOTIFY_ALARMPOINTINFO extends NetSDKLib.SdkStructure {
    /**
     * 订阅的SID
     */
    public int              nSID;
    /**
     * mini雷达报警点的数量
     */
    public int              nAlarmPointNum;
    /**
     * mini雷达报警点信息
     */
    public NET_MINI_RADAR_ALARMPOINT_INFO[] stuAlarmPoint = new NET_MINI_RADAR_ALARMPOINT_INFO[64];
    /**
     * 保留字节
     */
    public byte[]           szReserved = new byte[256];

    public NET_MINI_RADAR_NOTIFY_ALARMPOINTINFO() {
        for (int i = 0; i < stuAlarmPoint.length; i++) {
            stuAlarmPoint[i] = new NET_MINI_RADAR_ALARMPOINT_INFO();
        }
    }
}

