package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 摄像头设备
 * @date 2023/05/11 14:09:04
 */
public class NET_LAMP_EXTERNAL_DEVICE_CAMERA_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 相机IP
	 */
    public byte[]           szIp = new byte[32];
	/**
	 * 相机登录用户名
	 */
    public byte[]           szUser = new byte[32];
	/**
	 * 相机登录密码
	 */
    public byte[]           szPwd = new byte[32];
	/**
	 * 相机通道号
	 */
    public int              nChannel;
	/**
	 * 相机连接状态
	 */
    public int              bConnectedState;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[256];

	public NET_LAMP_EXTERNAL_DEVICE_CAMERA_INFO() {
	}
}

