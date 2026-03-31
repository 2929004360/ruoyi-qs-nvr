package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/** 
* @author 291189
* @description  接口 CLIENT_AttachVehiclesDistributionData 的输入参数 
* @origin autoTool
* @date 2023/07/03 14:05:42
*/
public class NET_IN_ATTACH_VEHICLES_DISTRIBUTION_DATA extends NetSDKLib.SdkStructure {
/** 
此结构体大小
*/
    public			int            dwSize;
/** 
接口超时等待时间
*/
    public			int            dwWaitTime;
/** 
通道号
*/
    public			int            nChannelID;
/** 
回调函数
*/
    public			NetSDKLib.fNotifyVehiclesDistributionData cbNotifySnapData;
/** 
用户自定义参数
*/
    public Pointer          dwUser;

public NET_IN_ATTACH_VEHICLES_DISTRIBUTION_DATA(){
		this.dwSize=this.size();
}
}

