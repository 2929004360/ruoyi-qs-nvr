package com.ruoyi.dahua.frame;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.module.LoginModule;
import com.sun.jna.Pointer;

import javax.swing.*;
import java.util.Vector;

class RealPlayFrame{
	private static final long serialVersionUID = 1L;
	
	private Vector<String> chnlist = new Vector<String>();

	// 设备断线通知回调
	private static DisConnect disConnect       = new DisConnect(); 
	
	// 网络连接恢复
	private static HaveReConnect haveReConnect = new HaveReConnect();

	//登陆参数
	private String s_strIp		   = "192.168.1.104";/*"172.23.12.17";*/ //"192.168.7.61";
	private Integer s_nPort        = new Integer("37777");
	private String s_strUser       = "admin";
	private String s_strPassword   = "hx14725";
	
	public RealPlayFrame() {
		LoginModule.init(disConnect, haveReConnect);   // 打开工程，初始化
		
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        	e.printStackTrace();
        }

		login();
	}
	
	/////////////////面板///////////////////
	// 设备断线回调: 通过 CLIENT_Init 设置该回调函数，当设备出现断线时，SDK会调用该函数
	private static class DisConnect implements NetSDKLib.fDisConnect {
		public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
			System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);
			System.out.println("设备断线");
		}
	}
	
	// 网络连接恢复，设备重连成功回调
	// 通过 CLIENT_SetAutoReconnect 设置该回调函数，当已断线的设备重连成功时，SDK会调用该函数
	private static class HaveReConnect implements NetSDKLib.fHaveReConnect {
		public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
			System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);
			System.out.println("网络连接恢复，设备重连成功");
		}
	}
	
	// 登录
	public boolean login() {
		if(LoginModule.login(s_strIp, s_nPort, s_strUser, s_strPassword))  {
			for(int i = 1; i < LoginModule.m_stDeviceInfo.byChanNum + 1; i++) {
				chnlist.add("通道" + " " + i);
			}

			System.out.println("login success"+chnlist);

		} else {
			System.out.println("登录失败, 帐号已被锁定"+"----"+JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}

public class RealPlay {
	public static void main(String[] args) {	
		new RealPlayFrame();
	}
}
