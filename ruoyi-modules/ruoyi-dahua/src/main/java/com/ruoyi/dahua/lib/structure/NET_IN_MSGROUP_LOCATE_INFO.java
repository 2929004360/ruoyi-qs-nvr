package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 联动主从跟踪组入参
 * @date 2022/09/14 13:55:52
 */
public class NET_IN_MSGROUP_LOCATE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 主从跟踪组句柄
	 */
    public int              dwToken;
	/**
	 * 主机坐标
	 */
    public NetSDKLib.DH_POINT stuPoint = new NetSDKLib.DH_POINT();
	/**
	 * 鱼眼圆心坐标与半径, 依次为[横坐标, 纵坐标, 半径]
	 */
    public short[]          snMasterInfo = new short[3];

	public NET_IN_MSGROUP_LOCATE_INFO() {
		this.dwSize = this.size();
	}
}

