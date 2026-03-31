package com.ruoyi.dahua.module;

import com.ruoyi.dahua.lib.NetSDKLib.NET_IN_INIT_DEVICE_ACCOUNT;
import com.ruoyi.dahua.lib.NetSDKLib.NET_OUT_INIT_DEVICE_ACCOUNT;
import com.ruoyi.dahua.lib.ToolKits;

/**
 * 设备初始化接口实现
 */
public class DeviceInitModule {
	/*
	 * 设备初始化
	 */
	public static boolean initDevAccount(String localIp,String szMac, String password, String cellPhone_mail, byte byPwdResetWay) {
		/**
		 *  入参
		 */
		NET_IN_INIT_DEVICE_ACCOUNT inInit = new NET_IN_INIT_DEVICE_ACCOUNT();
        // mac地址
        System.arraycopy(szMac.getBytes(), 0, inInit.szMac, 0, szMac.getBytes().length);
		
        // 用户名
        String username = "admin";
        System.arraycopy(username.getBytes(), 0, inInit.szUserName, 0, username.getBytes().length);

        // 密码，必须字母与数字结合，8位以上，否则设备不识别
        if(password.getBytes().length <= 127) {
        	System.arraycopy(password.getBytes(), 0, inInit.szPwd, 0, password.getBytes().length);
        } else if(password.getBytes().length > 127){
        	System.arraycopy(password.getBytes(), 0, inInit.szPwd, 0, 127);
        }

        // 设备支持的密码重置方式
        inInit.byPwdResetWay = byPwdResetWay;
        
        // bit0-支持预置手机号 bit1-支持预置邮箱
        if((byPwdResetWay >> 1 & 0x01) == 0) {  // 手机号
        	System.arraycopy(cellPhone_mail.getBytes(), 0, inInit.szCellPhone, 0, cellPhone_mail.getBytes().length);
        } else if((byPwdResetWay >> 1 & 0x01) == 1) {  // 邮箱
        	System.arraycopy(cellPhone_mail.getBytes(), 0, inInit.szMail, 0, cellPhone_mail.getBytes().length);
        }
        
		/**
		 *  出参
		 */
		NET_OUT_INIT_DEVICE_ACCOUNT outInit = new NET_OUT_INIT_DEVICE_ACCOUNT();
		
		if(!LoginModule.netsdk.CLIENT_InitDevAccount(inInit, outInit, 5000, localIp)) {
			System.err.println("初始化失败，" + ToolKits.getErrorCodePrint());
			return false;
		}
		
		return true;
	}
}
