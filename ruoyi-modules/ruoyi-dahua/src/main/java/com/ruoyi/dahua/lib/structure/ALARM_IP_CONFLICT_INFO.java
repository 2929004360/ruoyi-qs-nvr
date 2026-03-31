package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  IP冲突事件 
* @origin autoTool
* @date 2023/09/06 16:34:17
*/
public class ALARM_IP_CONFLICT_INFO extends NetSDKLib.SdkStructure {
    public			int            dwSize;
/** 
0:开始 1:停止
*/
    public			int            nAction;
/** 
事件触发时间
*/
    public NET_TIME         stuTime = new NET_TIME();
/** 
IP冲突网卡名
*/
    public			byte[]         szInterface = new byte[8];
/** 
主机Mac地址个数
*/
    public			int            nMacNum;
/** 
与Interface网卡IP地址冲突的主机Mac地址列表
*/
    public			byte[]         szMac = new byte[8*18];

public ALARM_IP_CONFLICT_INFO(){
		this.dwSize=this.size();
}
}

