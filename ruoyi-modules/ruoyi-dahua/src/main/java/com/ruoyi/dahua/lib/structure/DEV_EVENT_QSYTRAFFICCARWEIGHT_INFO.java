package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 事件类型 EVENT_IVS_QSYTRAFFICCARWEIGHT (交通卡口称重事件)对应的数据块描述信息
 * @date 2023/04/07 17:12:26
 */
public class DEV_EVENT_QSYTRAFFICCARWEIGHT_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 称重系统车辆信息
	 */
    public NET_CAR_WEIGHT_INFO stCarWeightInfo = new NET_CAR_WEIGHT_INFO();
	/**
	 * 交通卡口信息
	 */
    public NetSDKLib.DEV_EVENT_TRAFFICJUNCTION_INFO stJunctionInfo = new NetSDKLib.DEV_EVENT_TRAFFICJUNCTION_INFO();

	public DEV_EVENT_QSYTRAFFICCARWEIGHT_INFO() {
	}
}

