package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  环境光线配置 
* @origin autoTool
* @date 2023/11/30 16:17:33
*/
public class CFG_VIDEOIN_BACKLIGHT_INFO extends NetSDKLib.SdkStructure {
/**
 *环境光线配置信息,0-白天1-夜晚 2-普通
*/
    public			CFG_VIDEOIN_BACKLIGHT_BASE[] stuVideoInBackLight = new CFG_VIDEOIN_BACKLIGHT_BASE[3];

public			CFG_VIDEOIN_BACKLIGHT_INFO(){
		for(int i=0;i<stuVideoInBackLight.length;i++){
			stuVideoInBackLight[i]=new CFG_VIDEOIN_BACKLIGHT_BASE();
			}
}
}

