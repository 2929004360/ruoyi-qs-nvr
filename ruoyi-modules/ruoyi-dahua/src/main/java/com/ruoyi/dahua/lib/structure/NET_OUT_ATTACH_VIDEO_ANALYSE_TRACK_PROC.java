package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_AttachVideoAnalyseTrackProc 输出参数 
* @origin autoTool
* @date 2023/09/22 13:56:25
*/
public class NET_OUT_ATTACH_VIDEO_ANALYSE_TRACK_PROC extends NetSDKLib.SdkStructure {
/** 
/ 此结构体大小,必须赋值
*/
    public			int            dwSize;

public			NET_OUT_ATTACH_VIDEO_ANALYSE_TRACK_PROC(){
		this.dwSize=this.size();
}
}

