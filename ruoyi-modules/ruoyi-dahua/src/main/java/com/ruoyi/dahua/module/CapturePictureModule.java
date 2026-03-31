package com.ruoyi.dahua.module;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.ToolKits;
import com.sun.jna.ptr.IntByReference;

/**
 * \if ENGLISH_LANG
 * Capture Picture Interface
 * contains:local、remote、timer and stop capture picture
 * \else
 * 抓图接口实现
 * 包含: 本地、远程、定时和停止抓图
 * \endif
 */
public class CapturePictureModule {

	/**
	 * \if ENGLISH_LANG
	 * Local Capture Picture
	 * \else
	 * 本地抓图
	 * \endif
	 */
	public static boolean localCapturePicture(LLong hPlayHandle, String picFileName) {
				
		if (!LoginModule.netsdk.CLIENT_CapturePictureEx(hPlayHandle, picFileName, NetSDKLib.NET_CAPTURE_FORMATS.NET_CAPTURE_JPEG)) { 
			System.err.printf("CLIENT_CapturePicture Failed!" + ToolKits.getErrorCodePrint());
			return false;
		} else { 
			System.out.println("CLIENT_CapturePicture success"); 
		}
		return true;
	}
	
	/**
	 * \if ENGLISH_LANG
	 * Remote Capture Picture
	 * \else
	 * 远程抓图
	 * \endif
	 */
	public static boolean remoteCapturePicture(int chn) {
		return snapPicture(chn, 0, 0);
	}
	
	/**
	 * \if ENGLISH_LANG
	 * Timer Capture Picture
	 * \else
	 * 定时抓图
	 * \endif
	 */
	public static boolean timerCapturePicture(int chn) {
		return snapPicture(chn, 1, 2);
	}
	
	/**
	 * \if ENGLISH_LANG
	 * Stop Timer Capture Picture
	 * \else
	 * 停止定时抓图
	 * \endif
	 */
	public static boolean stopCapturePicture(int chn) {
		return snapPicture(chn, -1, 0);
	}
	
	/**
	 * \if ENGLISH_LANG
	 * Capture Picture (except local capture picture, others all call this interface)
	 * \else
	 * 抓图 (除本地抓图外, 其他全部调用此接口)
	 * \endif
	 */
	private static boolean snapPicture(int chn, int mode, int interval) {
		// send caputre picture command to device
		NetSDKLib.SNAP_PARAMS stuSnapParams = new NetSDKLib.SNAP_PARAMS(); 
		stuSnapParams.Channel = chn;  			// channel
		stuSnapParams.mode = mode;    			// capture picture mode
		stuSnapParams.Quality = 3;				// picture quality
		stuSnapParams.InterSnap = interval; 	// timer capture picture time interval
		stuSnapParams.CmdSerial = 0;  			// request serial  
		
		IntByReference reserved = new IntByReference(0);
		if (!LoginModule.netsdk.CLIENT_SnapPictureEx(LoginModule.m_hLoginHandle, stuSnapParams, reserved)) { 
			System.err.printf("CLIENT_SnapPictureEx Failed!" + ToolKits.getErrorCodePrint());
			return false;
		} else { 
			System.out.println("CLIENT_SnapPictureEx success"); 
		}
		return true;
	}
	
	/**
	 * \if ENGLISH_LANG
	 * Set Capture Picture Callback
	 * \else
	 * 设置抓图回调函数
	 * \endif
	 */
	public static void setSnapRevCallBack(NetSDKLib.fSnapRev cbSnapReceive){ 
		LoginModule.netsdk.CLIENT_SetSnapRevCallBack(cbSnapReceive, null);
	}
}
