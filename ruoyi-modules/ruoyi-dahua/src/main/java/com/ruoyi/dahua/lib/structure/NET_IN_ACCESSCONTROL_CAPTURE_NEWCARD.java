package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_AccessControlCaptureNewCard 输入结构体
 * @date 2022/12/30 10:55:15
 */
public class NET_IN_ACCESSCONTROL_CAPTURE_NEWCARD extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 授权时间,单位秒,0表示由服务端控制时间
	 */
    public int              nAllowTime;

	public NET_IN_ACCESSCONTROL_CAPTURE_NEWCARD() {
		this.dwSize = this.size();
	}
}

