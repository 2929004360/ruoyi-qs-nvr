package com.ruoyi.dahua.frame.vto;

import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.NetSDKLib.fHaveReConnect;
import com.sun.jna.Pointer;

import javax.swing.*;

/**
 * 网络连接恢复，设备重连成功回调 通过 CLIENT_SetAutoReconnect 设置该回调函数，当已断线的设备重连成功时，SDK会调用该函数
 * 
 * @author 47081
 *
 */
public class DefaultHaveReconnect implements fHaveReConnect {
	private static DefaultHaveReconnect INSTANCE;
	private JFrame frame;

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	private DefaultHaveReconnect() {
		// TODO Auto-generated constructor stub
	}

	public static DefaultHaveReconnect getINSTANCE() {
		if (INSTANCE == null) {
			INSTANCE = new DefaultHaveReconnect();
		}
		return INSTANCE;
	}

	@Override
	public void invoke(LLong lLoginID, String pchDVRIP, int nDVRPort, Pointer dwUser) {
		System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);

		// 重连提示
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (frame != null) {
					frame.setTitle(Res.string().getPTZ() + " : " + Res.string().getOnline());
				}
			}
		});
	}
}
