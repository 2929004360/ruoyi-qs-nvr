package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_GetZoneArmMode 接口入参 
* @origin autoTool
* @date 2023/08/09 11:16:07
*/
public class NET_IN_GET_ZONE_ARMODE_INFO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;
/** 
要操作的防区号列表个数
*/
    public			int            nZoneNum;
/** 
防区号，数组中第一个元素为-1表示获取所有通道的布撤防状态
*/
    public			int[]          nZones = new int[256];

public			NET_IN_GET_ZONE_ARMODE_INFO(){
		this.dwSize=this.size();
}
}

