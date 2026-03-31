package com.ruoyi.dahua.frame.vto;

import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.NetSDKLib.fDisConnect;
import com.sun.jna.Pointer;

/**
 * 设备断线回调函数，空实现。 建议回调函数使用单例模式
 * 
 * @author 47081
 *
 */
public class DefaultDisConnect implements fDisConnect {
	private static DefaultDisConnect INSTANCE;

	private DefaultDisConnect() {
		// TODO Auto-generated constructor stub
	}

	public static DefaultDisConnect GetInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DefaultDisConnect();
		}
		return INSTANCE;
	}

	@Override
	public void invoke(LLong lLoginID, String pchDVRIP, int nDVRPort, Pointer dwUser) {
		// TODO Auto-generated method stub
		System.out.printf("Device[%s] Port[%d] DisConnectCallBack!\n", pchDVRIP, nDVRPort);

	}
}
