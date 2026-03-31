package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description 远程通道是否同步本地端"周期一键布撤防"配置,对应配置枚举
 *              NET_EM_CFG_REMOTE_DISABLE_LINKAGE_TIME_SECTION_SYNC
 * @date 2022/10/14 14:10:58
 */
public class NET_CFG_REMOTE_DISABLE_LINKAGE_TIME_SECTION_SYNC_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 远程通道是否同步本地端"周期一键布撤防"配置功能总开关
	 */
    public int              bEnable;
	/**
	 * 需要同步的通道号,由用户分配内存,指针对应int[]数组
	 */
    public Pointer          pRemoteChannels;
	/**
	 * pRemoteChannels指向的有效通道个数
	 */
    public int              nRemoteChannelsCnt;
	/**
	 * 获取配置时,返回的有效通道号个数
	 */
    public int              nRetRemoteChannelsCnt;

	public NET_CFG_REMOTE_DISABLE_LINKAGE_TIME_SECTION_SYNC_INFO() {
		this.dwSize = this.size();
	}
}

