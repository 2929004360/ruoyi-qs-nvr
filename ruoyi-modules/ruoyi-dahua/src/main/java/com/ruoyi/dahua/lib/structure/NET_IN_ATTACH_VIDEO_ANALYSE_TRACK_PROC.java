package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/** 
* @author 291189
* @description  CLIENT_AttachVideoAnalyseTrackProc 输入参数 
* @origin autoTool
* @date 2023/09/22 13:56:25
*/
public class NET_IN_ATTACH_VIDEO_ANALYSE_TRACK_PROC extends NetSDKLib.SdkStructure {
/** 
/ 此结构体大小,必须赋值
*/
    public			int            dwSize;
/** 
/ 通道号
*/
    public			int            nChannelId;
/** 
/ 回调函数
*/
    public			NetSDKLib.fVideoAnalyseTrackProc cbVideoAnalyseTrackProc;
/** 
/ 用户信息
*/
    public Pointer          dwUser;

public			NET_IN_ATTACH_VIDEO_ANALYSE_TRACK_PROC(){
		this.dwSize=this.size();
}
}

