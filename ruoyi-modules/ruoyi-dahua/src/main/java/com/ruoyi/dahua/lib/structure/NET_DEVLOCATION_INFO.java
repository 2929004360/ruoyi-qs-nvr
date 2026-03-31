package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description GPS信息
 * @date 2023/05/24 10:24:52
 */
public class NET_DEVLOCATION_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 经度 单位百万分之一度，范围0-360度
	 */
    public int              unLongitude;
	/**
	 * 纬度 单位百万分之一度，范围0-360度
	 */
    public int              unLatitude;
	/**
	 * 海拔 单位米
	 */
    public double           dbAltitude;
	/**
	 * true：GPS信息按照配置上报
	 */
    public int              bConfigEnable;
	/**
	 * 设备高度 单位米
	 */
    public float            fHeight;

	public NET_DEVLOCATION_INFO() {
	}
}

