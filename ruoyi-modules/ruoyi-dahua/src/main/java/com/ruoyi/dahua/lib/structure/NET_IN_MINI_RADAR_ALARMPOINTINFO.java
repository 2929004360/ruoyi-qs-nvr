package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description 订阅mini雷达的报警点信息入参(对应接口 CLIENT_AttachMiniRadarAlarmPointInfo)
 * @date 2022/08/04 16:59:36
 */
public class NET_IN_MINI_RADAR_ALARMPOINTINFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 雷达报警点信息回调
     */
    public NetSDKLib.fMiniRadarAlarmPointInfoCallBack cbAlarmPointInfo;
    /**
     * 用户数据
     */
    public Pointer          dwUser;

    public NET_IN_MINI_RADAR_ALARMPOINTINFO() {
        this.dwSize = this.size();
    }
}

