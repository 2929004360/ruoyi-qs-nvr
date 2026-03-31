package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 单个WLAN配置Network
 * @date 2023/03/16 11:15:26
 */
public class CFG_WLAN_NETWORK extends NetSDKLib.SdkStructure {
	/**
	 * IP
	 */
    public byte[]           szIPAddress = new byte[40];
	/**
	 * 子网掩码
	 */
    public byte[]           szSubnetMask = new byte[40];
	/**
	 * 网关
	 */
    public byte[]           szDefaultGateway = new byte[40];
	/**
	 * 是否开启DHCP
	 */
    public int              bDhcpEnable;
	/**
	 * DNS服务器
	 */
    public BYTE_40[]        szDnsServers = new BYTE_40[2];

	public CFG_WLAN_NETWORK() {
		for (int i = 0; i < szDnsServers.length; i++) {
			szDnsServers[i] = new BYTE_40();
		}
	}
}

