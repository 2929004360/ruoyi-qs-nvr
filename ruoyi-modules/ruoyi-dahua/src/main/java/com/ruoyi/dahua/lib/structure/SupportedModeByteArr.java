package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description  网络模式字符串对应字节数组
 * @date 2021/09/17
 */
public class SupportedModeByteArr extends NetSDKLib.SdkStructure {
	/**
	 *  网络模式字符串对应字节数组
	 */
    public byte[]           supportedModeByteArr = new byte[NetSDKLib.NET_MAX_MODE_LEN];
}

