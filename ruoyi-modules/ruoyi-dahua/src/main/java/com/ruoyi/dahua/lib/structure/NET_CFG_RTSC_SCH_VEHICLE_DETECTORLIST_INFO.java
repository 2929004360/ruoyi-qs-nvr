package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 检测器列表，对应配置项NET_EM_CFG_RTSC_SCH_VEHICLE_DETECTORLIST
 * @date 2021/09/27
 */
public class NET_CFG_RTSC_SCH_VEHICLE_DETECTORLIST_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  最大车辆检测器数
     */
    public int              nMaxVehicleDetectors;
    /**
     *  检测器列表个数
     */
    public int              nVehicleDetectorNum;
    /**
     *  检测器列表
     */
    public NET_SIGNAL_VEHICLE_DETECTOR_INFO[] stuVehicleDetectorInfo = (NET_SIGNAL_VEHICLE_DETECTOR_INFO[]) new NET_SIGNAL_VEHICLE_DETECTOR_INFO().toArray(32);

    public NET_CFG_RTSC_SCH_VEHICLE_DETECTORLIST_INFO(){
        this.dwSize = this.size();
    }
}

