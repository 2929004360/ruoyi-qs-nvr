package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  屏蔽区的测温屏蔽区配置信息 
* @date 2022/11/07 10:53:23
*/
public class NET_CFG_THERMOMETRY_SHIELD_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小, 必填项
*/
    public			int            dwSize;
/** 
1 获取配置时，每个通道实际返回的测温屏蔽区配置个数
2 设置配置时，设置的配置个数
*/
    public			int            nRealInfoNumOfEachChennel;
/**
 每个通道的测温屏蔽区配置信息，每个通道最多支持12个屏蔽区
*/
    public			THERMOMETRY_SHIELD_INFO[] stuThermometryShieldInfo = new THERMOMETRY_SHIELD_INFO[12];

public NET_CFG_THERMOMETRY_SHIELD_INFO(){
		for(int i=0;i<stuThermometryShieldInfo.length;i++){
			stuThermometryShieldInfo[i]=new THERMOMETRY_SHIELD_INFO();
			}
		this.dwSize=this.size();
}
}

