package com.ruoyi.dahua.module;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.ToolKits;
import com.sun.jna.ptr.IntByReference;

/**
 * 下载录像接口实现
 * 主要有 ： 查询录像、下载录像、设置码流类型功能
 */
public class DownLoadRecordModule {
	// 下载句柄
	public static LLong m_hDownLoadHandle = new LLong(0);
	
	// 查找录像文件
	public static boolean queryRecordFile(int nChannelId, 
									   NetSDKLib.NET_TIME stTimeStart, 
									   NetSDKLib.NET_TIME stTimeEnd, 
									   NetSDKLib.NET_RECORDFILE_INFO[] stFileInfo,
									   IntByReference nFindCount) {
		// RecordFileType 录像类型 0:所有录像  1:外部报警  2:动态监测报警  3:所有报警  4:卡号查询   5:组合条件查询   
		// 6:录像位置与偏移量长度   8:按卡号查询图片(目前仅HB-U和NVS特殊型号的设备支持)  9:查询图片(目前仅HB-U和NVS特殊型号的设备支持)  
		// 10:按字段查询    15:返回网络数据结构(金桥网吧)  16:查询所有透明串数据录像文件
		int nRecordFileType = 0; 
		boolean bRet = LoginModule.netsdk.CLIENT_QueryRecordFile(LoginModule.m_hLoginHandle, nChannelId, 
										nRecordFileType, stTimeStart, stTimeEnd, null, stFileInfo, 
										stFileInfo.length * stFileInfo[0].size(), nFindCount, 5000, false);
		
		if(bRet) {
			System.out.println("QueryRecordFile  Succeed! \n" + "查询到的视频个数：" + nFindCount.getValue());
		} else {
			System.err.println("QueryRecordFile  Failed!" + ToolKits.getErrorCodePrint());
			return false;
		}
		return true;
	}
	
	/**
	 *  设置回放时的码流类型
	 * @param m_streamType 码流类型
	 */
	public static void setStreamType(int m_streamType) {
        
        IntByReference steamType = new IntByReference(m_streamType);// 0-主辅码流,1-主码流,2-辅码流
        int emType = NetSDKLib.EM_USEDEV_MODE.NET_RECORD_STREAM_TYPE;       

        boolean bret = LoginModule.netsdk.CLIENT_SetDeviceMode(LoginModule.m_hLoginHandle, emType, steamType.getPointer());
        if (!bret) {
        	System.err.println("Set Stream Type Failed, Get last error." + ToolKits.getErrorCodePrint());
        } else {
        	System.out.println("Set Stream Type  Succeed!");
        }
	}
	
	public static LLong downloadRecordFile(int nChannelId,    
										     int nRecordFileType,
										     NetSDKLib.NET_TIME stTimeStart, 
										     NetSDKLib.NET_TIME stTimeEnd, 
										     String SavedFileName,
										     NetSDKLib.fTimeDownLoadPosCallBack cbTimeDownLoadPos) {
		
		m_hDownLoadHandle = LoginModule.netsdk.CLIENT_DownloadByTimeEx(LoginModule.m_hLoginHandle, nChannelId, nRecordFileType, 
															stTimeStart, stTimeEnd, SavedFileName, 
															cbTimeDownLoadPos, null, null, null, null);
		if(m_hDownLoadHandle.longValue() != 0) {
			System.out.println("Downloading RecordFile!");
		} else {
			System.err.println("Download RecordFile Failed!" + ToolKits.getErrorCodePrint());
		}
		return m_hDownLoadHandle;
	}
	
	public static void stopDownLoadRecordFile(LLong m_hDownLoadHandle) {
		if (m_hDownLoadHandle.longValue() == 0) {		
			return;
		}
		LoginModule.netsdk.CLIENT_StopDownload(m_hDownLoadHandle);
	}
}
