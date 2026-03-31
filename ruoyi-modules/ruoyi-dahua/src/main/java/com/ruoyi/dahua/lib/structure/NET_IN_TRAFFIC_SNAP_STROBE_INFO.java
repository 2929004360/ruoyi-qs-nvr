package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 智能交通外接道闸信息入参
 * @date 2020/12/14
 */
public class NET_IN_TRAFFIC_SNAP_STROBE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 * */
    public int              dwSize;
	/**
	 * 通道号
	 * */ 
    public int              nChannel;

	public NET_IN_TRAFFIC_SNAP_STROBE_INFO() {
	  this.dwSize = this.size();
	}
}

