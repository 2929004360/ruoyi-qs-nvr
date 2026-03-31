package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  单个通道对应的透雾配置信息 
* @origin autoTool
* @date 2023/11/30 15:52:11
*/
public class CFG_VIDEOINDEFOG_LIST extends NetSDKLib.SdkStructure {
/** 
每个通道实际透雾配置个数
*/
    public			int            nVideoInDefogNum;
/** 
每个通道对应的透雾配置数组
*/
    public			CFG_VIDEOINDEFOG[] stVideoInDefog = new CFG_VIDEOINDEFOG[3];

public CFG_VIDEOINDEFOG_LIST(){
		for(int i=0;i<stVideoInDefog.length;i++){
			stVideoInDefog[i]=new CFG_VIDEOINDEFOG();
			}
}
}

