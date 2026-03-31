package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/** 
* @author 291189
* @description  多通道回放预览参数 
* @origin autoTool
* @date 2023/05/16 14:49:19
*/
public class NET_MULTI_PLAYBACK_PARAM extends NetSDKLib.SdkStructure {
    public			int            dwSize;
/** 
预览通道号
*/
    public			int[]          nChannels = new int[64];
/** 
预览通道数
*/
    public			int            nChannelNum;
/** 
回放文件类型,0：普通录象；1：报警录象；2：移动检测；3：卡号录象；4：图片
*/
    public			int            nType;
/** 
回放开始时间
*/
    public NET_TIME         stStartTime = new NET_TIME();
/** 
回放结束时间
*/
    public NET_TIME         stEndTime = new NET_TIME();
/** 
帧率,1~25
*/
    public			int            nFPS;
/** 
码流值,192~1024
*/
    public			int            nBitRate;
/** 
分辨率,"D1"、"HD1"、"2CIF"、"CIF"、"QCIF"
*/
    public			byte[]         szResolution = new byte[64];
/** 
超时等待时间
*/
    public			int            nWaitTime;
/** 
视频播放窗口句柄
*/
    public Pointer          hWnd;
/** 
视频数据回调
*/
    public			NetSDKLib.fDataCallBack fDownLoadDataCallBack;
/** 
视频数据用户数据
*/
    public			Pointer        dwDataUser;
/** 
回放进度回调
*/
    public			NetSDKLib.fDownLoadPosCallBack cbDownLoadPos;
/** 
进度回调用户数据
*/
    public			Pointer        dwPosUser;

public NET_MULTI_PLAYBACK_PARAM(){
		this.dwSize=this.size();
}
}

