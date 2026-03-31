package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  聚焦设置基本信息单元 
* @date 2022/11/01 11:16:54
*/
public class CFG_VIDEO_IN_FOCUS extends NetSDKLib.SdkStructure {
/** 
通道号
*/
    public			int            nChannelIndex;
/** 
配置使用个数
*/
    public			int            nVideoInFocusRealNum;
/** 
通道聚焦配置单元信息
*/
    public			CFG_VIDEO_IN_FOCUS_UNIT[] stVideoInFocusUnit = new CFG_VIDEO_IN_FOCUS_UNIT[32];

public			CFG_VIDEO_IN_FOCUS(){
		for(int i=0;i<stVideoInFocusUnit.length;i++){
			stVideoInFocusUnit[i]=new CFG_VIDEO_IN_FOCUS_UNIT();
			}
}
}

