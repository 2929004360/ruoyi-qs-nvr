package com.ruoyi.dahua.module;

import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.ToolKits;
import com.sun.jna.Native;

import java.awt.*;

/**
 * 实时预览接口实现
 * 主要有 ：开始拉流、停止拉流功能
 */
public class RealPlayModule {


	/**
	 * \if ENGLISH_LANG
	 * Start RealPlay
	 * \else
	 * 开始预览
	 * \endif
	 */
	public static LLong startRealPlay(int channel, int stream, Panel realPlayWindow) {
		LLong m_hPlayHandle = LoginModule.netsdk.CLIENT_RealPlayEx(
				LoginModule.m_hLoginHandle,
				channel,
				Native.getComponentPointer(realPlayWindow),
				stream);
	
	    if(m_hPlayHandle.longValue() == 0) {
	  	    System.err.println("开始实时预览失败，错误码" + ToolKits.getErrorCodePrint());
	    } else {
	  	    System.out.println("Success to start realplay"); 
	    }
	    
	    return m_hPlayHandle;
	} 
	
	/**
	 * \if ENGLISH_LANG
	 * Start RealPlay
	 * \else
	 * 停止预览
	 * \endif
	 */
	public static void stopRealPlay(LLong m_hPlayHandle) {
		if(m_hPlayHandle.longValue() == 0) {
			return;
		}
		
		boolean bRet = LoginModule.netsdk.CLIENT_StopRealPlayEx(m_hPlayHandle);
		if(bRet) {
			m_hPlayHandle.setValue(0);
		}
	}
}
