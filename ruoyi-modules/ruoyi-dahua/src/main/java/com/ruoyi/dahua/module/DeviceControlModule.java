package com.ruoyi.dahua.module;

import com.ruoyi.dahua.lib.NetSDKLib.CtrlType;
import com.ruoyi.dahua.lib.NetSDKLib.NET_TIME;
import com.ruoyi.dahua.lib.ToolKits;

import java.text.SimpleDateFormat;


/**
 * \if ENGLISH_LANG
 * Device Control Interface
 * contains:reboot device、setup device time and query device time
 * \else
 * 设备控制接口实现
 * 包含: 重启、时间同步、获取时间功能
 * \endif
 */
public class DeviceControlModule {
    
	/**
	 * \if ENGLISH_LANG
	 * Reboot Device
	 * \else
	 * 重启设备
	 * \endif
	 */
    public static boolean reboot() {
    	
        if (!LoginModule.netsdk.CLIENT_ControlDevice(LoginModule.m_hLoginHandle, CtrlType.CTRLTYPE_CTRL_REBOOT, null, 3000)) {
        	System.err.println("CLIENT_ControlDevice Failed!" + ToolKits.getErrorCodePrint());
    		return false;
        }
        return true;
    }

    /**
	 * \if ENGLISH_LANG
	 * Setup Device Time
	 * \else
	 * 时间同步
	 * \endif
	 */
    public static boolean setTime(String date) {
    	NET_TIME deviceTime = new NET_TIME();
    	if (date == null) {
    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = dateFormat.format(new java.util.Date());
    	}
    	
    	String[] dateTime = date.split(" ");
        String[] arrDate = dateTime[0].split("-");
        String[] arrTime = dateTime[1].split(":");
        deviceTime.dwYear = Integer.parseInt(arrDate[0]);
        deviceTime.dwMonth = Integer.parseInt(arrDate[1]);
        deviceTime.dwDay = Integer.parseInt(arrDate[2]);
        deviceTime.dwHour = Integer.parseInt(arrTime[0]);
        deviceTime.dwMinute = Integer.parseInt(arrTime[1]);
        deviceTime.dwSecond = Integer.parseInt(arrTime[2]);

        if (!LoginModule.netsdk.CLIENT_SetupDeviceTime(LoginModule.m_hLoginHandle, deviceTime)) {
    		System.err.println("CLIENT_SetupDeviceTime Failed!" + ToolKits.getErrorCodePrint());
    		return false;
        }
        return true;
    }
    
    /**
  	 * \if ENGLISH_LANG
  	 * Get Device Current Time
  	 * \else
  	 * 获取设备当前时间
  	 * \endif
  	 */
    public static String getTime() {
    	NET_TIME deviceTime = new NET_TIME();
    	
    	if (!LoginModule.netsdk.CLIENT_QueryDeviceTime(LoginModule.m_hLoginHandle, deviceTime, 3000)) {
    		System.err.println("CLIENT_QueryDeviceTime Failed!" + ToolKits.getErrorCodePrint());
    		return null;
    	}

    	String date = deviceTime.toStringTime();
    	date = date.replace("/", "-");
    	
    	return date;
    }
}
