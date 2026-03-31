package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.EM_RADAR_GETCAPS_AREASUB_TYPE;
import com.ruoyi.dahua.lib.enumeration.EM_RADAR_GETCAPS_RADAR_TYPE;

/**
 * @author 260611
 * @description 获取雷达能力出参(对应 EM_RADAR_OPERATE_TYPE_GETCAPS)
 * @date 2022/08/04 10:13:32
 */
public class NET_OUT_RADAR_GETCAPS extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 雷达探测距离，实际距离扩大100倍,单位米
     */
    public int              nDetectionRange;
    /**
     * 雷达探测角度，实际角度扩大100倍,单位度
     */
    public int              nDetectionAngle;
    /**
     * 雷达人探测距离，实际距离扩大100倍,单位米
     */
    public int              nDetectionHuman;
    /**
     * 下列扩展能力是否有效(老设备不支持获取下列能力，从而无法用于判断是否支持下列能力)
     */
    public int              bExValid;
    /**
     * 雷达功率配置能力
     */
    public NET_RADAR_CAPACITY_CAP stuCapacityCap = new NET_RADAR_CAPACITY_CAP();
    /**
     * 雷达场景配置能力
     */
    public NET_RADAR_SCENE_CAP stuSceneCap = new NET_RADAR_SCENE_CAP();
    /**
     * 雷达信道配置能力
     */
    public NET_RADAR_CHANNEL_CAP stuChannelCap = new NET_RADAR_CHANNEL_CAP();
    /**
     * 雷达场景变更能力
     */
    public NET_RADAR_MOVEDDETECT_CAP stuMovedDetectCap = new NET_RADAR_MOVEDDETECT_CAP();
    /**
     * 雷达控制盒支持的协议能力
     */
    public NET_RADAR_PROTOCAL_CAP stuProtocalCap = new NET_RADAR_PROTOCAL_CAP();
    /**
     * 雷达探测范围形状 {@link EM_RADAR_GETCAPS_AREASUB_TYPE}
     */
    public int              emAreaSubType;
    /**
     * 雷达类型 {@link EM_RADAR_GETCAPS_RADAR_TYPE}
     */
    public int              emRadarType;

    public NET_OUT_RADAR_GETCAPS() {
        this.dwSize = this.size();
    }
}

