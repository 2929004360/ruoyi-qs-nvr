package com.ruoyi.dahua.module;

import com.ruoyi.dahua.lib.NetSDKLib.DEVICE_IP_SEARCH_INFO;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.NetSDKLib.fSearchDevicesCB;
import com.ruoyi.dahua.lib.NetSDKLib.fSearchDevicesCBEx;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.lib.enumeration.EM_SEND_SEARCH_TYPE;
import com.ruoyi.dahua.lib.structure.NET_IN_STARTSERACH_DEVICE;
import com.ruoyi.dahua.lib.structure.NET_OUT_STARTSERACH_DEVICE;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


/**
 * 设备搜索接口实现
 * 主要功能有 ： 设备组播和广播搜索、设备IP单播搜索
 */
public class DeviceSearchModule {

	/**
	 * 设备组播和广播搜索
	 * @throws SocketException 
	 */
	public static LLong multiBroadcastDeviceSearch(fSearchDevicesCBEx cbSearchDevices,String szlocalIp) throws SocketException {
		NET_IN_STARTSERACH_DEVICE pInparm = new NET_IN_STARTSERACH_DEVICE();
		
		pInparm.cbSearchDevices=cbSearchDevices;		
		System.arraycopy(szlocalIp.getBytes(), 0, pInparm.szLocalIp, 0, szlocalIp.getBytes().length);
		pInparm.emSendType=EM_SEND_SEARCH_TYPE.EM_SEND_SEARCH_TYPE_MULTICAST_AND_BROADCAST.ordinal();
		
		Pointer pInBuf =new Memory(pInparm.size());
		ToolKits.SetStructDataToPointer(pInparm, pInBuf, 0);
		
		NET_OUT_STARTSERACH_DEVICE pOutparm =new NET_OUT_STARTSERACH_DEVICE();
		
		Pointer pOutBuf =new Memory(pOutparm.size());
		ToolKits.SetStructDataToPointer(pOutparm, pOutBuf, 0);
		
		return LoginModule.netsdk.CLIENT_StartSearchDevicesEx(pInBuf, pOutBuf);
	}
	
	/**
	 * 停止设备组播和广播搜索
	 */
	public static void stopDeviceSearch(LLong m_DeviceSearchHandle) {
		if(m_DeviceSearchHandle.longValue() == 0) {
			return;
		}
		
		LoginModule.netsdk.CLIENT_StopSearchDevices(m_DeviceSearchHandle);
		m_DeviceSearchHandle.setValue(0);
	}
	
	/**
	 * 设备IP单播搜索
	 * @param startIP 起始IP
	 * @param nIpNum IP个数，最大 256
	 * @throws SocketException 
	 */
	public static boolean unicastDeviceSearch(String localIp,String startIP, int nIpNum, fSearchDevicesCB cbSearchDevices) throws SocketException {
		String[] szIPStr = startIP.split("\\.");
		
		DEVICE_IP_SEARCH_INFO deviceSearchInfo = new DEVICE_IP_SEARCH_INFO();
		deviceSearchInfo.nIpNum = nIpNum;
		for(int i = 0; i < deviceSearchInfo.nIpNum; i++) {
			System.arraycopy(getIp(szIPStr, i).getBytes(), 0, deviceSearchInfo.szIPArr[i].szIP, 0, getIp(szIPStr, i).getBytes().length);
		}
		if(LoginModule.netsdk.CLIENT_SearchDevicesByIPs(deviceSearchInfo, cbSearchDevices, null, localIp, 6000)) {
			System.out.println("SearchDevicesByIPs Succeed!");
			return true;
		}
		return false;
	}
	
	public static String getIp(String[] ip, int num) {
		String szIp = "";
		if(Integer.parseInt(ip[3]) >= 255) {
			szIp = ip[0] + "." + ip[1] + "." + String.valueOf(Integer.parseInt(ip[2]) + 1) + "." + String.valueOf(Integer.parseInt(ip[3]) + num - 255);
		} else {
			szIp = ip[0] + "." + ip[1] + "." + ip[2] + "." + String.valueOf(Integer.parseInt(ip[3]) + num);
		}
		
		return szIp;
	}
	
	/**
	 * 获取多网卡IP 
	 */
	public static List<String> getHostAddress() throws SocketException {
		
	    List<String> ipList = new ArrayList<String>();
	    if(NetworkInterface.getNetworkInterfaces() == null) {
	    	return ipList;
	    }	    
	    Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();	   
	    
	    while (networkInterfaces.hasMoreElements()) {
	        NetworkInterface networkInterface = networkInterfaces.nextElement();
	        Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
	        
	        while (inetAddresses.hasMoreElements()) {
	            InetAddress inetAddress = inetAddresses.nextElement();	            	         
	            
	            if (inetAddress.isLoopbackAddress()) {//回路地址，如127.0.0.1
//	                System.out.println("loop addr:" + inetAddress);
	            } else if (inetAddress.isLinkLocalAddress()) {//169.254.x.x
//	                System.out.println("link addr:" + inetAddress);
	            } else if(inetAddress instanceof Inet4Address){
	                //非链接和回路真实ip	            	 	 				
	            		 String localname = inetAddress.getHostName();
			             String localip = inetAddress.getHostAddress();
			             ipList.add(localip);	 					 				                					
	            }
	        }
	    }	    
		return ipList;
	}
}
