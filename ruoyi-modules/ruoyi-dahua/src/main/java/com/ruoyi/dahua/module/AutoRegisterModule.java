package com.ruoyi.dahua.module;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.*;
import com.ruoyi.dahua.lib.ToolKits;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import java.awt.*;

public class AutoRegisterModule {
	// 监听服务句柄
	public static LLong mServerHandler = new LLong(0);	
	
	// 设备信息
	public static NET_DEVICEINFO_Ex m_stDeviceInfo = new NET_DEVICEINFO_Ex();
	
	// 语音对讲句柄
	public static LLong m_hTalkHandle = new LLong(0);
	private static boolean m_bRecordStatus    = false;
	
	/**
	 * 开启服务
	 * @param address 本地IP地址
	 * @param port 本地端口, 可以任意
	 * @param callback 回调函数
	 */
	public static LLong startServer(String address, int port, fServiceCallBack callback) {

		LLong mServerHandler = LoginModule.netsdk.CLIENT_ListenServer(address, port, 1000, callback, null);
		if (0 == mServerHandler.longValue()) {
			System.err.println("Failed to start server." + ToolKits.getErrorCodePrint());
		} else {
			System.out.printf("Start server, [Server address %s][Server port %d]\n", address, port);
		}
		return mServerHandler;
	}
	
	/**
	 * 结束服务
	 */
	public static boolean stopServer() {
		boolean bRet = false;
		
		if(mServerHandler.longValue() != 0) {
			bRet = LoginModule.netsdk.CLIENT_StopListenServer(mServerHandler);	
			mServerHandler.setValue(0);
			System.out.println("Stop server!");
		}
		
		return bRet;
	}
	
	/**
	 * 登录设备(主动注册登陆接口)
	 * @param m_strIp  设备IP
	 * @param m_nPort  设备端口号
	 * @param m_strUser  设备用户名
	 * @param m_strPassword  设备密码
	 * @param deviceId  设备ID
	 * @return
	 */
	public static LLong login(String m_strIp, int m_nPort, String m_strUser, String m_strPassword, String deviceIds) {	
		Pointer deviceId = ToolKits.GetGBKStringToPointer(deviceIds);		
		//IntByReference nError = new IntByReference(0);
		//入参
		NET_IN_LOGIN_WITH_HIGHLEVEL_SECURITY pstInParam=new NET_IN_LOGIN_WITH_HIGHLEVEL_SECURITY();
		pstInParam.nPort=m_nPort;
		pstInParam.szIP=m_strIp.getBytes();
		pstInParam.szPassword=m_strPassword.getBytes();
		pstInParam.szUserName=m_strUser.getBytes();
		pstInParam.emSpecCap = 2;// 主动注册方式
		pstInParam.pCapParam=deviceId;
		//出参
		NET_OUT_LOGIN_WITH_HIGHLEVEL_SECURITY pstOutParam=new NET_OUT_LOGIN_WITH_HIGHLEVEL_SECURITY();
		pstOutParam.stuDeviceInfo=m_stDeviceInfo;
		/*LLong m_hLoginHandle = LoginModule.netsdk.CLIENT_LoginEx2(m_strIp, m_nPort, m_strUser, m_strPassword, 
								    tcpSpecCap, deviceId, m_stDeviceInfo, nError);*/
		LLong m_hLoginHandle=LoginModule.netsdk.CLIENT_LoginWithHighLevelSecurity(pstInParam, pstOutParam);
		return m_hLoginHandle;
	}
	
	/**
	 * 登出设备
	 * @param m_hLoginHandle  登陆句柄
	 * @return
	 */
	public static boolean logout(LLong m_hLoginHandle) {
		boolean bRet = false;
		if(m_hLoginHandle.longValue() != 0) {
			bRet = LoginModule.netsdk.CLIENT_Logout(m_hLoginHandle);
			m_hLoginHandle.setValue(0);	
		}

		return bRet;
	}
	
	/**
	 * 开始预览
	 * @param m_hLoginHandle 登陆句柄
	 * @param channel  通道号
	 * @param stream  码流类型
	 * @param realPlayWindow  拉流窗口
	 * @return
	 */
	public static LLong startRealPlay(LLong m_hLoginHandle, int channel, int stream, Panel realPlayWindow) {
		LLong m_hPlayHandle = LoginModule.netsdk.CLIENT_RealPlayEx(m_hLoginHandle, channel, Native.getComponentPointer(realPlayWindow), stream);
	
	    if(m_hPlayHandle.longValue() == 0) {
	  	    System.err.println("Failed to start realplay." + ToolKits.getErrorCodePrint());
	    } else {
	  	    System.out.println("Success to start realplay"); 
	    }
	    
	    return m_hPlayHandle;
	} 
	
	/**
	 * 停止预览
	 * @param m_hPlayHandle 实时预览句柄
	 * @return
	 */
	public static boolean stopRealPlay(LLong m_hPlayHandle) {
		boolean bRet = false;
		if(m_hPlayHandle.longValue() != 0) {
			bRet = LoginModule.netsdk.CLIENT_StopRealPlayEx(m_hPlayHandle);
			m_hPlayHandle.setValue(0);
		}
		
		return bRet;
	}
	
	/**
	 * 远程抓图
	 * @param m_hLoginHandle 登陆句柄
	 * @param chn  通道号
	 * @return
	 */
	public static boolean snapPicture(LLong m_hLoginHandle, int chn) {
		// 发送抓图命令给前端设备，抓图的信息
		SNAP_PARAMS msg = new SNAP_PARAMS();
		msg.Channel = chn;  			// 抓图通道
		msg.mode = 0;    			    // 抓图模式
		msg.Quality = 3;				// 画质
		msg.InterSnap = 0; 	            // 定时抓图时间间隔
		msg.CmdSerial = 0;  			// 请求序列号，有效值范围 0~65535，超过范围会被截断为  
		
		IntByReference reserved = new IntByReference(0);
		
		if (!LoginModule.netsdk.CLIENT_SnapPictureEx(m_hLoginHandle, msg, reserved)) { 
			System.err.printf("SnapPictureEx Failed!" + ToolKits.getErrorCodePrint());
			return false;
		} else { 
			System.out.println("SnapPictureEx success"); 
		}
		return true;
	}
	
	/**
	 *设置抓图回调函数， 图片主要在m_SnapReceiveCB中返回
	 * @param m_SnapReceiveCB
	 */
	public static void setSnapRevCallBack(fSnapRev m_SnapReceiveCB){
		LoginModule.netsdk.CLIENT_SetSnapRevCallBack(m_SnapReceiveCB, null);
	}
	
	/**
	 * 获取网络协议
	 * @param m_hLoginHandle 登录句柄
	 * @return
	 */
	public static CFG_DVRIP_INFO getDVRIPConfig(LLong m_hLoginHandle) {
		CFG_DVRIP_INFO msg = new CFG_DVRIP_INFO();
		
		if(!ToolKits.GetDevConfig(m_hLoginHandle, -1, NetSDKLib.CFG_CMD_DVRIP, msg)) {
			return null;
		}
		
		return msg;
	}
	
	/**
	 * 网络协议配置
	 * @param m_hLoginHandle 登陆句柄
	 * @param enable  使能
	 * @param address 服务器地址
	 * @param nPort  服务器端口号
	 * @param deviceId  设备ID
	 * @param info 获取到的网络协议配置
	 * @return
	 */
	public static boolean setDVRIPConfig(LLong m_hLoginHandle, boolean enable, String address, int nPort, byte[] deviceId, CFG_DVRIP_INFO info) {
		CFG_DVRIP_INFO msg = info;
		// 主动注册配置个数
		msg.nRegistersNum = 1;  
		
		 // 主动注册使能
		msg.stuRegisters[0].bEnable = enable? 1:0; 
	
		// 服务器个数
		msg.stuRegisters[0].nServersNum = 1;
		
		// 服务器地址
		ToolKits.StringToByteArray(address, msg.stuRegisters[0].stuServers[0].szAddress);
	    
		// 服务器端口号
		msg.stuRegisters[0].stuServers[0].nPort = nPort;
	    
	    // 设备ID
		ToolKits.ByteArrayToByteArray(deviceId, msg.stuRegisters[0].szDeviceID);
		
		return ToolKits.SetDevConfig(m_hLoginHandle, -1, NetSDKLib.CFG_CMD_DVRIP, msg);
	}
	
	/**
	 * \if ENGLISH_LANG
	 * Start Talk
	 * \else
	 * 开始通话
	 * \endif
	 */
	public static boolean startTalk(LLong m_hLoginHandle) {
	
		// 设置语音对讲编码格式
		NETDEV_TALKDECODE_INFO talkEncode = new NETDEV_TALKDECODE_INFO();
		talkEncode.encodeType = NET_TALK_CODING_TYPE.NET_TALK_PCM;
		talkEncode.dwSampleRate = 8000;
		talkEncode.nAudioBit = 16;
		talkEncode.nPacketPeriod = 25;
		talkEncode.write();
		if(LoginModule.netsdk.CLIENT_SetDeviceMode(m_hLoginHandle, EM_USEDEV_MODE.NET_TALK_ENCODE_TYPE, talkEncode.getPointer())) {
			System.out.println("Set Talk Encode Type Succeed!");
		} else {
			System.err.println("Set Talk Encode Type Failed!" + ToolKits.getErrorCodePrint());
			return false;
		}
		
		// 设置对讲模式
		NET_SPEAK_PARAM speak = new NET_SPEAK_PARAM();
        speak.nMode = 0;
        speak.bEnableWait = 0;
        speak.nSpeakerChannel = 0;
        speak.write();
        
        if (LoginModule.netsdk.CLIENT_SetDeviceMode(m_hLoginHandle, EM_USEDEV_MODE.NET_TALK_SPEAK_PARAM, speak.getPointer())) {
        	System.out.println("Set Talk Speak Mode Succeed!");
        } else {
        	System.err.println("Set Talk Speak Mode Failed!" + ToolKits.getErrorCodePrint());
			return false;
        }
		
		// 设置语音对讲是否为转发模式
		NET_TALK_TRANSFER_PARAM talkTransfer = new NET_TALK_TRANSFER_PARAM();
		talkTransfer.bTransfer = 0;   // 是否开启语音对讲转发模式, 1-true; 0-false
		talkTransfer.write();
		if(LoginModule.netsdk.CLIENT_SetDeviceMode(m_hLoginHandle, EM_USEDEV_MODE.NET_TALK_TRANSFER_MODE, talkTransfer.getPointer())) {
			System.out.println("Set Talk Transfer Mode Succeed!");
		} else {
			System.err.println("Set Talk Transfer Mode Failed!" + ToolKits.getErrorCodePrint());
			return false;
		}
		
		m_hTalkHandle = LoginModule.netsdk.CLIENT_StartTalkEx(m_hLoginHandle, AudioDataCB.getInstance(), null);
		
	    if(m_hTalkHandle.longValue() == 0) {
	  	    System.err.println("Start Talk Failed!" + ToolKits.getErrorCodePrint());	  
	  	    return false;
	    } else {
	  	    System.out.println("Start Talk Success");
			if(LoginModule.netsdk.CLIENT_RecordStart()){
				System.out.println("Start Record Success");
				m_bRecordStatus = true;
			} else {
				System.err.println("Start Local Record Failed!" + ToolKits.getErrorCodePrint());
				stopTalk(m_hTalkHandle);				
				return false;
			}
	    }
		
		return true;
	}
	
	/**
	 * \if ENGLISH_LANG
	 * Stop Talk
	 * \else
	 * 结束通话
	 * \endif
	 */
	public static void stopTalk(LLong m_hTalkHandle) {
		if(m_hTalkHandle.longValue() == 0) {
			return;
		}
		
		if (m_bRecordStatus){
			LoginModule.netsdk.CLIENT_RecordStop();
			m_bRecordStatus = false;
		}
		
		if(!LoginModule.netsdk.CLIENT_StopTalkEx(m_hTalkHandle)) {			
			System.err.println("Stop Talk Failed!" + ToolKits.getErrorCodePrint());
    	} else {
    		m_hTalkHandle.setValue(0);
    	}
	}
	
	/**
	 * \if ENGLISH_LANG
	 * Audio Data Callback
	 * \else
	 * 语音对讲的数据回调
	 * \endif
	 */
	private static class AudioDataCB implements pfAudioDataCallBack {
		
		private AudioDataCB() {}
		private static AudioDataCB audioCallBack = new AudioDataCB();
		
		public static AudioDataCB getInstance() {
			return audioCallBack;
		}
		
		public void invoke(LLong lTalkHandle, Pointer pDataBuf, int dwBufSize, byte byAudioFlag, Pointer dwUser){
			
			if(lTalkHandle.longValue() != m_hTalkHandle.longValue()) {
				return;
			}
			
			if (byAudioFlag == 0) { // 将收到的本地PC端检测到的声卡数据发送给设备端
				
				LLong lSendSize = LoginModule.netsdk.CLIENT_TalkSendData(m_hTalkHandle, pDataBuf, dwBufSize);
				if(lSendSize.longValue() != (long)dwBufSize) {
					System.err.println("send incomplete" + lSendSize.longValue() + ":" + dwBufSize);
				} 
			}else if (byAudioFlag == 1) { // 将收到的设备端发送过来的语音数据传给SDK解码播放
				LoginModule.netsdk.CLIENT_AudioDecEx(m_hTalkHandle, pDataBuf, dwBufSize);
			}
		}
	}
}
