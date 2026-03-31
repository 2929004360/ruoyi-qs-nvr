package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_QueryNetStat接口,查询类型为NET_APP_DATA_STAT时的输出参数(获取协议栈统计数据) 
* @origin autoTool
* @date 2023/06/19 09:24:47
*/
public class NET_OUT_NETAPP_NET_DATA_STAT extends NetSDKLib.SdkStructure {
    public			int            dwSize;
/** 
接收相关统计数据,意义与ifconfig同
*/
    public			NET_NETAPP_RECEIVE_STAT stuReceiveStat = new NET_NETAPP_RECEIVE_STAT();
/** 
传输相关统计数据,意义与ifconfig同
*/
    public			NET_NETAPP_TRANSMIT_STAT stuTransmitStat = new NET_NETAPP_TRANSMIT_STAT();
/** 
网卡速度,单位Mbps
*/
    public			int            nSpeed;

public NET_OUT_NETAPP_NET_DATA_STAT(){
		this.dwSize=this.size();
}
}

