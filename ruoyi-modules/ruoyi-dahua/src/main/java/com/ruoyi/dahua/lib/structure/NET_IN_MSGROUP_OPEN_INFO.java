package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 打开主从跟踪组入参
 * @date 2022/09/14 13:52:40
 */
public class NET_IN_MSGROUP_OPEN_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大
	 */
    public int              dwSize;
	/**
	 * 设备组号
	 */
    public int              nGroupID;
	/**
	 * 从机序号
	 */
    public int              nSlaveID;
	/**
	 * 对应主从组的主机序号, 例如三枪一球，则该字段取值0、1和2
	 */
    public int              nMasterID;
	/**
	 * 本地通道号，对应本地视频通道号，从0开始。（老设备及前端设备不支持多通道，设置为0）
	 */
    public int              nChannel;

	public NET_IN_MSGROUP_OPEN_INFO() {
		this.dwSize = this.size();
	}
}

