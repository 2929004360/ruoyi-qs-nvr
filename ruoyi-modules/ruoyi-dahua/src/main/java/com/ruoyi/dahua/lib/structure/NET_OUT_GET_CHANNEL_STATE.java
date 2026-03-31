package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 291189
 * @description CLIENT_GetChannelState 出参
 * @date 2022/07/04 11:17:42
 */
public class NET_OUT_GET_CHANNEL_STATE extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 智能业务状态信息实际个数
	 */
    public int              nStateNum;
	/**
	 * 用户申请智能业务状态信息最大个数
	 */
    public int              nMaxStateNum;
	/**
	 * 字节对齐
	 */
    public byte[]           szReserved = new byte[4];
	/**
	 * 智能业务状态信息 需要用户申请内存空间 大小 sizeof(NET_CHANNEL_STATE_INFO)*nMaxStateNum
	 */
    public Pointer          pstuState;
	/**
	 * 智能业务状态信息扩展 需要用户申请内存空间 大小 sizeof(NET_CHANNEL_STATE_INFO_EX)*nMaxStateNum
	 */
    public Pointer          pstuStateEx;

	public NET_OUT_GET_CHANNEL_STATE() {
		this.dwSize = this.size();
	}
}

