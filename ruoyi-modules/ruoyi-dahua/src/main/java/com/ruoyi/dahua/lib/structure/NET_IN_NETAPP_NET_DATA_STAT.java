package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_QueryNetStat接口,查询类型为NET_APP_DATA_STAT时的输入参数(获取协议栈统计数据) 
* @origin autoTool
* @date 2023/06/19 09:23:51
*/
public class NET_IN_NETAPP_NET_DATA_STAT extends NetSDKLib.SdkStructure {
    public			int            dwSize;
/** 
网卡名
*/
    public			byte[]         szEthName = new byte[64];

public NET_IN_NETAPP_NET_DATA_STAT(){
		this.dwSize=this.size();
}
}

