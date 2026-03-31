package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_QueryNetStat接口,查询类型为NET_APP_LINK_STAT 时的输出参数(获取物理链路状态) 
* @origin autoTool
* @date 2023/06/19 09:29:25
*/
public class NET_OUT_NETAPP_LINK_STATUS extends NetSDKLib.SdkStructure {
    public			int            dwSize;
/** 
是否正在工作
*/
    public			int            bWorking;
/** 
IP是否冲突
*/
    public			int            bIPConflict;

public NET_OUT_NETAPP_LINK_STATUS(){
		this.dwSize=this.size();
}
}

