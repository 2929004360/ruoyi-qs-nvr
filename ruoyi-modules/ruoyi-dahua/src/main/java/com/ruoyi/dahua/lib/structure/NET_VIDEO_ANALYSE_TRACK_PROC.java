package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

import static com.ruoyi.dahua.lib.NetSDKLib.POINTERSIZE;

/** 
* @author 291189
* @description  外部轨迹的信息 
* @origin autoTool
* @date 2023/09/22 13:56:25
*/
public class NET_VIDEO_ANALYSE_TRACK_PROC extends NetSDKLib.SdkStructure {
/** 
/ 结构体大小
*/
    public			int            dwSize;
/** 
/ 通道号
*/
    public			int            nChannelId;
/** 
/ 视频目标信息
*/
    public			NET_VIDEO_TRACK_OBJECT_INFO[] stuTrackObject = new NET_VIDEO_TRACK_OBJECT_INFO[128];
/** 
/ 视频目标个数
*/
    public			int            nTrackObjectNum;
/** 
/ 视频目标信息，内存大小为sizeof(NET_VIDEO_TRACK_OBJECT_INFO_EX)*nTrackObjectNum, refer to {@link NET_VIDEO_TRACK_OBJECT_INFO_EX}
*/
    public Pointer          pstuTrackObjectEx;
/** 
/ 保留字节
*/
    public			byte[]         szReserved = new byte[256-POINTERSIZE];

public NET_VIDEO_ANALYSE_TRACK_PROC(){
		for(int i=0;i<stuTrackObject.length;i++){
			stuTrackObject[i]=new NET_VIDEO_TRACK_OBJECT_INFO();
			}
		this.dwSize=this.size();
}
}

