package com.ruoyi.dahua.module;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.ToolKits;

/**
 * 智能交通接口实现
 * 主要有 ：智能订阅、开闸、关闸功能
 */
public class TrafficEventModule {
	// 智能订阅句柄
	public static LLong m_hAttachHandle = new LLong(0);
	
	/**
	 * 新版本开闸
	 */
	public static boolean New_OpenStrobe() {		
		NetSDKLib.NET_CTRL_OPEN_STROBE openStrobe = new NetSDKLib.NET_CTRL_OPEN_STROBE();
		openStrobe.nChannelId = 0;
		String plate = new String("浙A888888");
		
		System.arraycopy(plate.getBytes(), 0, openStrobe.szPlateNumber, 0, plate.getBytes().length);
		openStrobe.write();
		if (LoginModule.netsdk.CLIENT_ControlDeviceEx(LoginModule.m_hLoginHandle, NetSDKLib.CtrlType.CTRLTYPE_CTRL_OPEN_STROBE, openStrobe.getPointer(), null, 3000)) {
		    System.out.println("Open Success!");
		} else {
			System.err.println("Failed to Open." + ToolKits.getErrorCodePrint());
			return false;
		} 
		openStrobe.read();
		
		return true;
	}
	
	/**
	 * 新版本关闸
	 */
	public static void New_CloseStrobe() {	
		NetSDKLib.NET_CTRL_CLOSE_STROBE closeStrobe = new NetSDKLib.NET_CTRL_CLOSE_STROBE();
        closeStrobe.nChannelId = 0;
        closeStrobe.write();
        if (LoginModule.netsdk.CLIENT_ControlDeviceEx(LoginModule.m_hLoginHandle, NetSDKLib.CtrlType.CTRLTYPE_CTRL_CLOSE_STROBE, closeStrobe.getPointer(), null, 3000)) {
        	System.out.println("Close Success!");
        } else {
        	System.err.println("Failed to Close." + ToolKits.getErrorCodePrint());
        }
        closeStrobe.read();
	}
	
    /**
     * 手动抓图按钮事件
     */
    public static boolean manualSnapPicture(int chn) { 	
    	NetSDKLib.MANUAL_SNAP_PARAMETER snapParam = new NetSDKLib.MANUAL_SNAP_PARAMETER();
    	snapParam.nChannel = chn;
    	String sequence = "11111"; // 抓图序列号，必须用数组拷贝
    	System.arraycopy(sequence.getBytes(), 0, snapParam.bySequence, 0, sequence.getBytes().length);
    	
    	snapParam.write();
    	boolean bRet = LoginModule.netsdk.CLIENT_ControlDeviceEx(LoginModule.m_hLoginHandle, NetSDKLib.CtrlType.CTRLTYPE_MANUAL_SNAP, snapParam.getPointer(), null, 5000);
    	if (!bRet) {
    		System.err.println("Failed to manual snap, last error " + ToolKits.getErrorCodePrint());
    		return false;
    	} else {
    		System.out.println("Seccessed to manual snap");
    	}
    	snapParam.read();
    	return true;
    }
	
    /**
     * 订阅实时上传智能分析数据
     * @return 
     */
    public static boolean attachIVSEvent(int ChannelId, NetSDKLib.fAnalyzerDataCallBack m_AnalyzerDataCB) { 	
    	/**
		 * 说明：
		 * 	通道数可以在有登录是返回的信息 m_stDeviceInfo.byChanNum 获取
		 *  下列仅订阅了0通道的智能事件.
		 */
		int bNeedPicture = 1; // 是否需要图片

        m_hAttachHandle = LoginModule.netsdk.CLIENT_RealLoadPictureEx(LoginModule.m_hLoginHandle, ChannelId,  NetSDKLib.EVENT_IVS_ALL, 
				bNeedPicture , m_AnalyzerDataCB , null , null);
		if( m_hAttachHandle.longValue() != 0  ) {
			System.out.println("CLIENT_RealLoadPictureEx Success  ChannelId : \n" + ChannelId);
		} else {
			System.err.println("CLIENT_RealLoadPictureEx Failed!" + ToolKits.getErrorCodePrint());
			return false;
		}
		
		return true;
    }
    
    /**
     * 停止上传智能分析数据－图片
     */
    public static void detachIVSEvent() {
        if (0 != m_hAttachHandle.longValue()) {
        	LoginModule.netsdk.CLIENT_StopLoadPic(m_hAttachHandle);
            System.out.println("Stop detach IVS event");
            m_hAttachHandle.setValue(0);
        }
    }
}
