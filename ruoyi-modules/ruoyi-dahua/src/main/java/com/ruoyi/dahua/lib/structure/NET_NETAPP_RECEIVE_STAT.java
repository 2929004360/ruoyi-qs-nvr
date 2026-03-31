package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  接收相关统计数据,意义与ifconfig同 
* @origin autoTool
* @date 2023/06/19 09:24:47
*/
public class NET_NETAPP_RECEIVE_STAT extends NetSDKLib.SdkStructure {
    public			int            dwSize;
    public			int            dwPackets;
    public			int            dwBytes;
    public			int            dwErrors;
    public			int            dwDroped;
    public			int            dwOverruns;
    public			int            dwFrame;
/** 
下行流量速度
*/
    public			int            dwSpeed;

public			NET_NETAPP_RECEIVE_STAT(){
		this.dwSize=this.size();
}
}

