package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 探测器主电丢失事件(对应DH_ALARM_WIRELESSDEV_POWERLESS)
 * @date 2023/03/09 20:12:33
 */
public class ALARM_WIRELESSDEV_POWERLESS_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 0:脉冲 1:开始 2:停止
	 */
    public int              nAction;
	/**
	 * 探测器地址
	 */
    public int              nIndex;
	/**
	 * 事件发生的时间
	 */
    public NET_TIME_EX      stuLoacalTime = new NET_TIME_EX();
	/**
	 * 探测器类型
	 */
    public byte[]           szModel = new byte[32];
	/**
	 * 探测器名称
	 */
    public byte[]           szName = new byte[32];
	/**
	 * 探测器序列号
	 */
    public byte[]           szSN = new byte[32];
	/**
	 * 探测器所属区域名称
	 */
    public byte[]           szAreaName = new byte[32];
	/**
	 * 事件公共扩展字段结构体
	 */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
	/**
	 * 保留字节
	 */
    public byte[]           szReserverd = new byte[128];

	public ALARM_WIRELESSDEV_POWERLESS_INFO() {
	}
}

