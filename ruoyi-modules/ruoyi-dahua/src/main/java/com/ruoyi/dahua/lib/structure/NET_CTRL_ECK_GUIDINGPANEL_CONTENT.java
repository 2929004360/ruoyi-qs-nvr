package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  设置诱导屏显示内容(DH_CTRL_ECK_GUIDINGPANEL_CONTENT) 
* @origin autoTool
* @date 2023/06/27 16:54:42
*/
public class NET_CTRL_ECK_GUIDINGPANEL_CONTENT extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
显示内容数量
*/
    public			int            nPanelNum;
/** 
显示内容
*/
    public			NET_ECK_PANEL_CONTENT[] stuContentInfo = new NET_ECK_PANEL_CONTENT[12];

public			NET_CTRL_ECK_GUIDINGPANEL_CONTENT(){
		for(int i=0;i<stuContentInfo.length;i++){
			stuContentInfo[i]=new NET_ECK_PANEL_CONTENT();
			}
		this.dwSize=this.size();
}
}

