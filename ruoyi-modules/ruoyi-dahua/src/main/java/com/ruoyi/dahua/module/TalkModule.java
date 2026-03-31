package com.ruoyi.dahua.module;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.ToolKits;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

/**
 * \if ENGLISH_LANG
 * Talk Interface
 * contains:start talk、stop talk and audio data callback implement class  
 * \else
 * 语音对讲接口实现
 * 包含: 开始通话、结束通话、语音对讲的数据回调实现类
 * \endif
 */
public class TalkModule {

	public static LLong m_hTalkHandle = new LLong(0); // 语音对讲句柄 
	
	private static boolean m_bRecordStatus    = false; 			// 是否正在录音
	
	/**
	 * \if ENGLISH_LANG
	 * Start Talk
	 * \else
	 * 开始通话
	 * \endif
	 */
	public static boolean startTalk(int transferType, int chn,LLong m_hLoginHandle) {
	
		// 设置语音对讲编码格式
		NetSDKLib.NETDEV_TALKDECODE_INFO talkEncode = new NetSDKLib.NETDEV_TALKDECODE_INFO();
		talkEncode.encodeType = NetSDKLib.NET_TALK_CODING_TYPE.NET_TALK_PCM;
		talkEncode.dwSampleRate = 8000;
		talkEncode.nAudioBit = 16;
		talkEncode.nPacketPeriod = 25;
		talkEncode.write();
		if(LoginModule.netsdk.CLIENT_SetDeviceMode(m_hLoginHandle, NetSDKLib.EM_USEDEV_MODE.NET_TALK_ENCODE_TYPE, talkEncode.getPointer())) {
			System.out.println("Set Talk Encode Type Succeed!");
		} else {
			System.err.println("Set Talk Encode Type Failed!" + ToolKits.getErrorCodePrint());
			return false;
		}
		
		// 设置语音对讲喊话参数
		NetSDKLib.NET_SPEAK_PARAM speak = new NetSDKLib.NET_SPEAK_PARAM();
        speak.nMode = 0;
        speak.bEnableWait = 0;
        speak.nSpeakerChannel = 0;
        speak.write();
        
        if (LoginModule.netsdk.CLIENT_SetDeviceMode(m_hLoginHandle, NetSDKLib.EM_USEDEV_MODE.NET_TALK_SPEAK_PARAM, speak.getPointer())) {
        	System.out.println("Set Talk Speak Mode Succeed!");
        } else {
        	System.err.println("Set Talk Speak Mode Failed!" + ToolKits.getErrorCodePrint());
			return false;
        }
		
		// 设置语音对讲是否为转发模式
		NetSDKLib.NET_TALK_TRANSFER_PARAM talkTransfer = new NetSDKLib.NET_TALK_TRANSFER_PARAM();
		talkTransfer.bTransfer = transferType;
		talkTransfer.write();
		if(LoginModule.netsdk.CLIENT_SetDeviceMode(m_hLoginHandle, NetSDKLib.EM_USEDEV_MODE.NET_TALK_TRANSFER_MODE, talkTransfer.getPointer())) {
			System.out.println("Set Talk Transfer Mode Succeed!");
		} else {
			System.err.println("Set Talk Transfer Mode Failed!" + ToolKits.getErrorCodePrint());
			return false;
		}
		
		if (talkTransfer.bTransfer == 1) {  // 转发模式设置转发通道
			
			IntByReference nChn = new IntByReference(chn);
			if(LoginModule.netsdk.CLIENT_SetDeviceMode(m_hLoginHandle, NetSDKLib.EM_USEDEV_MODE.NET_TALK_TALK_CHANNEL, nChn.getPointer())) {
				System.out.println("Set Talk Channel Succeed!");
			} else {
				System.err.println("Set Talk Channel Failed!" + ToolKits.getErrorCodePrint());
				return false;
			}
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
				stopTalk();
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
	public static void stopTalk() {
		if(m_hTalkHandle.longValue() == 0) {
			return;
		}
		
		if (m_bRecordStatus){
			LoginModule.netsdk.CLIENT_RecordStop();
			m_bRecordStatus = false;
		}
		
		if(LoginModule.netsdk.CLIENT_StopTalkEx(m_hTalkHandle)) {
			m_hTalkHandle.setValue(0);
		}else {
			System.err.println("Stop Talk Failed!" + ToolKits.getErrorCodePrint());
    	}
	}
	
	/**
	 * \if ENGLISH_LANG
	 * Audio Data Callback
	 * \else
	 * 语音对讲的数据回调
	 * \endif
	 */
	private static class AudioDataCB implements NetSDKLib.pfAudioDataCallBack {
		
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
