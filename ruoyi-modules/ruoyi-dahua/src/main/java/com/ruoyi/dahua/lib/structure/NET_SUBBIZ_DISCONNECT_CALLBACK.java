package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description 分压连接断线回调信息
 * @date 2022/11/03 10:09:28
 */
public class NET_SUBBIZ_DISCONNECT_CALLBACK extends NetSDKLib.SdkStructure {
	/**
	 * 设备网络信息
	 */
    public NET_DEV_NETWORK_INFO stuDevNetInfo = new NET_DEV_NETWORK_INFO();
	/**
	 * 用户数据
	 */
    public Pointer          dwUserData;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[1024];

	public NET_SUBBIZ_DISCONNECT_CALLBACK() {
	}
}

