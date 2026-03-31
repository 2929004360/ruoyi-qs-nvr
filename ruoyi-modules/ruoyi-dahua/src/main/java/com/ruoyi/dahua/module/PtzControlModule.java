package com.ruoyi.dahua.module;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * 云台控制接口实现
 * 主要有 ：八个方向控制、变倍、变焦、光圈功能
 */
public class PtzControlModule {

	/**
	 * 向上
	 */
	public static boolean ptzControlUpStart(int nChannelID, int lParam1, int lParam2) {
		return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
									NetSDKLib.NET_PTZ_ControlType.NET_PTZ_UP_CONTROL, 
									lParam1, lParam2, 0, 0);
	}
	public static boolean ptzControlUpEnd(int nChannelID) {
		return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
									 NetSDKLib.NET_PTZ_ControlType.NET_PTZ_UP_CONTROL, 
									 0, 0, 0, 1);
	}
	
	/**
	 * 向下
	 */
	public static boolean ptzControlDownStart(int nChannelID, int lParam1, int lParam2) {
		return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
											NetSDKLib.NET_PTZ_ControlType.NET_PTZ_DOWN_CONTROL, 
											lParam1, lParam2, 0, 0);
	}
	public static boolean ptzControlDownEnd(int nChannelID) {
		return 	LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
											 NetSDKLib.NET_PTZ_ControlType.NET_PTZ_DOWN_CONTROL, 
											 0, 0, 0, 1);
	}
	
	/**
	 * 向左
	 */
	public static boolean ptzControlLeftStart(int nChannelID, int lParam1, int lParam2) {
		return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
											NetSDKLib.NET_PTZ_ControlType.NET_PTZ_LEFT_CONTROL, 
											lParam1, lParam2, 0, 0);	
	}
	public static boolean ptzControlLeftEnd(int nChannelID) {
		return	LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
											 NetSDKLib.NET_PTZ_ControlType.NET_PTZ_LEFT_CONTROL, 
											 0, 0, 0, 1);	
	}
	
	/**
	 * 向右
	 */
	public static boolean ptzControlRightStart(int nChannelID, int lParam1,int lParam2) {
		return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
											NetSDKLib.NET_PTZ_ControlType.NET_PTZ_RIGHT_CONTROL, 
											lParam1, lParam2, 0, 0);
	}
	public static boolean ptzControlRightEnd(int nChannelID) {
		return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
											 NetSDKLib.NET_PTZ_ControlType.NET_PTZ_RIGHT_CONTROL, 
											 0, 0, 0, 1);		
	}
	
	/**
	 * 向左上
	 */
	public static boolean ptzControlLeftUpStart(int nChannelID, int lParam1, int lParam2) {
		return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
											NetSDKLib.NET_EXTPTZ_ControlType.NET_EXTPTZ_LEFTTOP, 
											lParam1, lParam2, 0, 0);
	}
	public static boolean ptzControlLeftUpEnd(int nChannelID) {
		return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
											 NetSDKLib.NET_EXTPTZ_ControlType.NET_EXTPTZ_LEFTTOP, 
											 0, 0, 0, 1);	
	}
	
	/**
	 * 向右上
	 */
	public static boolean ptzControlRightUpStart(int nChannelID, int lParam1, int lParam2) {
		return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
											NetSDKLib.NET_EXTPTZ_ControlType.NET_EXTPTZ_RIGHTTOP, 
											lParam1, lParam2, 0, 0);
	}
	public static boolean ptzControlRightUpEnd(int nChannelID) {
		return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
											 NetSDKLib.NET_EXTPTZ_ControlType.NET_EXTPTZ_RIGHTTOP, 
											 0, 0, 0, 1);	
	}

	/**
	 * 向左下
	 */
	public static boolean ptzControlLeftDownStart(int nChannelID, int lParam1, int lParam2) {
		return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
													NetSDKLib.NET_EXTPTZ_ControlType.NET_EXTPTZ_LEFTDOWN, 
													lParam1, lParam2, 0, 0);
	}
	public static boolean ptzControlLeftDownEnd(int nChannelID) {
		return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
										 NetSDKLib.NET_EXTPTZ_ControlType.NET_EXTPTZ_LEFTDOWN, 
										 0, 0, 0, 1);
	}
	
	/**
	 * 向右下
	 */
	public static boolean ptzControlRightDownStart(int nChannelID, int lParam1, int lParam2) {
		return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
													NetSDKLib.NET_EXTPTZ_ControlType.NET_EXTPTZ_RIGHTDOWN, 
													lParam1, lParam2, 0, 0);
	}
	public static boolean ptzControlRightDownEnd(int nChannelID) {
		return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
										 NetSDKLib.NET_EXTPTZ_ControlType.NET_EXTPTZ_RIGHTDOWN, 
										 0, 0, 0, 1);
	}
	
    /**
     * 变倍+
     */
    public static boolean ptzControlZoomAddStart(int nChannelID, int lParam2) {
        return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
							        	   NetSDKLib.NET_PTZ_ControlType.NET_PTZ_ZOOM_ADD_CONTROL, 
							        	   0, lParam2, 0, 0);
    }
    public static boolean ptzControlZoomAddEnd(int nChannelID) {
        return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
					            		    NetSDKLib.NET_PTZ_ControlType.NET_PTZ_ZOOM_ADD_CONTROL, 
					            		    0, 0, 0, 1);
    }

    /**
     * 变倍-
     */
    public static boolean ptzControlZoomDecStart(int nChannelID, int lParam2) {
       return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
							        	    NetSDKLib.NET_PTZ_ControlType.NET_PTZ_ZOOM_DEC_CONTROL, 
							        	    0, lParam2, 0, 0);
    }
    public static boolean ptzControlZoomDecEnd(int nChannelID) {
        return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
					            		     NetSDKLib.NET_PTZ_ControlType.NET_PTZ_ZOOM_DEC_CONTROL, 
					            		     0, 0, 0, 1);
    }

    /**
     * 变焦+
     */
    public static boolean ptzControlFocusAddStart(int nChannelID, int lParam2) {
    	return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
									        	    NetSDKLib.NET_PTZ_ControlType.NET_PTZ_FOCUS_ADD_CONTROL, 
									        	    0, lParam2, 0, 0);
    }
    public static boolean ptzControlFocusAddEnd(int nChannelID) {
    	return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
				            		     NetSDKLib.NET_PTZ_ControlType.NET_PTZ_FOCUS_ADD_CONTROL, 
				            		     0, 0, 0, 1);
    }

    /**
     * 变焦-
     */
    public static boolean ptzControlFocusDecStart(int nChannelID, int lParam2) {
        return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
							        	    NetSDKLib.NET_PTZ_ControlType.NET_PTZ_FOCUS_DEC_CONTROL, 
							        	    0, lParam2, 0, 0);
    }
    public static boolean ptzControlFocusDecEnd(int nChannelID) {
        return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
					            		     NetSDKLib.NET_PTZ_ControlType.NET_PTZ_FOCUS_DEC_CONTROL, 
					            		     0, 0, 0, 1);
    }

    /**
     * 光圈+
     */
    public static boolean ptzControlIrisAddStart(int nChannelID, int lParam2) {
        return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
							        	    NetSDKLib.NET_PTZ_ControlType.NET_PTZ_APERTURE_ADD_CONTROL, 
							        	    0, lParam2, 0, 0);
    }
    public static boolean ptzControlIrisAddEnd(int nChannelID) {
        return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
					            		     NetSDKLib.NET_PTZ_ControlType.NET_PTZ_APERTURE_ADD_CONTROL, 
					            		     0, 0, 0, 1);
    }

    /**
     * 光圈-
     */
    public static boolean ptzControlIrisDecStart(int nChannelID, int lParam2) {
        return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
									        	    NetSDKLib.NET_PTZ_ControlType.NET_PTZ_APERTURE_DEC_CONTROL, 
									        	    0, lParam2, 0, 0);
    }
    public static boolean ptzControlIrisDecEnd(int nChannelID) {
        return LoginModule.netsdk.CLIENT_DHPTZControlEx(LoginModule.m_hLoginHandle, nChannelID, 
					            		     NetSDKLib.NET_PTZ_ControlType.NET_PTZ_APERTURE_DEC_CONTROL, 
					            		     0, 0, 0, 1);
    }
}
