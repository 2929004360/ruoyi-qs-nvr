package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 输入通道扩展信息
 * @date 2022/11/24 20:08:17
 */
public class NET_VIDEOCHANNELS_INPUT_EX extends NetSDKLib.SdkStructure {
	/**
	 * 球机通道个数
	 */
    public int              nSDCount;
	/**
	 * 支持云台程序的通道数量(扩展)
	 */
    public short[]          nSD = new short[1024];
	/**
	 * 支持云台程序的通道数量
	 */
    public int              nPTZCount;
	/**
	 * 支持云台程序的通道号(扩展)
	 */
    public short[]          nPTZ = new short[1024];
	/**
	 * 预留字段
	 */
    public byte[]           reserved = new byte[1024];

	public NET_VIDEOCHANNELS_INPUT_EX() {
	}
}

