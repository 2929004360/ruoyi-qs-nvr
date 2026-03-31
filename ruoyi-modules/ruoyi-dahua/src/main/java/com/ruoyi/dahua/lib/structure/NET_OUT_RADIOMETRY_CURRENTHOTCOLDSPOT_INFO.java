package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  冷（最低的温度）、热（最高的温度）点信息 
* @origin autoTool
* @date 2023/08/07 13:51:18
*/
public class NET_OUT_RADIOMETRY_CURRENTHOTCOLDSPOT_INFO extends NetSDKLib.SdkStructure {
    public			int            dwSize;
/** 
热成像当前冷（最低的温度）、热（最高的温度）点信息
*/
    public			NET_RADIOMETRY_CURRENTHOTCOLDSPOT_INFO stuCurrentHotColdSpotInfo = new NET_RADIOMETRY_CURRENTHOTCOLDSPOT_INFO();

public			NET_OUT_RADIOMETRY_CURRENTHOTCOLDSPOT_INFO(){
		this.dwSize=this.size();
}
}

