package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_QueryNetStat接口,查询类型为NET_APP_LINK_STAT 时的输入参数(获取物理链路状态) 
* @origin autoTool
* @date 2023/06/19 09:28:37
*/
public class NET_IN_NETAPP_LINK_STATUS extends NetSDKLib.SdkStructure {
    public			int            dwSize;
/** 
网卡名
*/
    public			byte[]         szEthName = new byte[64];

public NET_IN_NETAPP_LINK_STATUS(){
		this.dwSize=this.size();
}
}

