package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * className：ALARM_COLDSPOT_WARNING_INFO
 * description：
 * author：251589
 * createTime：2021/5/12 20:53
 *
 * @version v1.0
 */

public class ALARM_COLDSPOT_WARNING_INFO extends NetSDKLib.SdkStructure {
    /**
     *  0:开始 1:停止
     */
    public int              nAction;
    /**
     *  视频通道号
     */
    public int              nChannelID;
    /**
     *  冷点的坐标,坐标值 0~8192
     */
    public NetSDKLib.NET_POINT stuCoordinate;
    /**
     *  冷点温度值
     */
    public float            fColdSpotValue;
    /**
     *  温度单位(当前配置的温度单位),见 NET_TEMPERATURE_UNIT
     */
    public int              nTemperatureUnit;
    /**
     * 事件公共扩展字段结构体
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
}

