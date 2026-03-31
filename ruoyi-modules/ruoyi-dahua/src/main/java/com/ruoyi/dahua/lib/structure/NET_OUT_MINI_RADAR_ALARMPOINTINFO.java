package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 订阅mini雷达的报警点信息出参(对应接口 CLIENT_AttachMiniRadarAlarmPointInfo)
 * @date 2022/08/04 16:59:37
 */
public class NET_OUT_MINI_RADAR_ALARMPOINTINFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 订阅的SID
     */
    public int              nSID;

    public NET_OUT_MINI_RADAR_ALARMPOINTINFO() {
        this.dwSize = this.size();
    }
}

