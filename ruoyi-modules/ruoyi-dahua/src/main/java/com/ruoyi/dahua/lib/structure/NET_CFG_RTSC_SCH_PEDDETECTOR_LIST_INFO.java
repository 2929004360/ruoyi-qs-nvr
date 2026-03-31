package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 行人检测器列表, 对应配置项NET_EM_CFG_RTSC_SCH_PEDDETECTOR_LIST
 * @date 2021/09/27
 */
public class NET_CFG_RTSC_SCH_PEDDETECTOR_LIST_INFO extends NetSDKLib.SdkStructure {
    /**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  最大行人检测器数
     */
    public int              nMaxPedestrianDetectors;
    /**
     *  行人检测器列表个数
     */
    public int              nPedDetectorNum;
    /**
     *  行人检测器列表
     */
    public NET_PED_DETECTOR_INFO[] stuPedDetectorInfo = (NET_PED_DETECTOR_INFO[]) new NET_PED_DETECTOR_INFO().toArray(16);

    public NET_CFG_RTSC_SCH_PEDDETECTOR_LIST_INFO(){
        this.dwSize = this.size();
    }
}

