package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  考场录像查询 ( CLIENT_FindFileEx + DH_FILE_QUERY_EXAM ) 
* @date 2022/05/17 10:42:12
*/
public class NET_MEDIAFILE_EXAM_PARAM extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
通道号从0开始,-1表示查询所有通道
*/
    public			int            nChannelID;
/** 
开始时间
*/
    public NET_TIME         stuStartTime = new NET_TIME();
/** 
结束时间
*/
    public NET_TIME         stuEndTime = new NET_TIME();
/** 
考生姓名
*/
    public			byte[]         szName = new byte[64];
/** 
考生考号
*/
    public			byte[]         szNumber = new byte[64];
/** 
摄像头IP
*/
    public			byte[]         szCameraIP = new byte[32];

public NET_MEDIAFILE_EXAM_PARAM(){
		this.dwSize=this.size();
}
}

