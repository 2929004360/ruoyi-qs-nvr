package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 接入点信息
 * @date 2023/03/16 14:10:09
 */
public class NET_WLAN_ACCESSPOINT_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 无线网络名称
	 */
    public byte[]           szSSID = new byte[36];
	/**
	 * 信号强度 范围0-100
	 */
    public int              nStrength;
	/**
	 * 认证模式0:OPEN;1:SHARED;2:WPA;3:WPA-PSK;4:WPA2;5:WPA2-PSK;
	 * 6:WPA-NONE(用在adhoc网络模式), 7-11是混合模式,选择其中任何一种都可以进行连接 7:WPA-PSK | WPA2-PSK;
	 * 8:WPA | WPA2; 9:WPA | WPA-PSK; 10:WPA2 | WPA2-PSK; 11:WPA | WPA-PSK |WPA2
	 * |WPA2-PSK ;12: UnKnown
	 */
    public int              nAuthMode;
	/**
	 * 0:NONE, 1:WEP, 2:TKIP, 3:AES(CCMP), 4:TKIP+AES( mix Mode), 5:UnKnown
	 */
    public int              nEncrAlgr;
    public byte[]           reserved = new byte[1016];

	public NET_WLAN_ACCESSPOINT_INFO() {
	}
}

