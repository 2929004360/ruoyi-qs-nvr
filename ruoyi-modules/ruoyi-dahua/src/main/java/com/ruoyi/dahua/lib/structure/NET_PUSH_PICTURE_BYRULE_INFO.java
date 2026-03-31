package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  智能分析图片信息 
* @date 2022/06/28 19:28:32
*/
public class NET_PUSH_PICTURE_BYRULE_INFO extends NetSDKLib.SdkStructure {
/** 
文件ID
*/
    public			byte[]         szFileID = new byte[128];
/** 
文件数据在二进制数据中的偏移, 单位:字节 (URL和Offset/Length应该是两者有且只有一个)
*/
    public			int            nOffset;
/** 
文件数据长度, 单位:字节 (URL和Offset/Length应该是两者有且只有一个)
*/
    public			int            nLength;
/** 
分析规则信息
*/
    public NetSDKLib.NET_ANALYSE_RULE stuRuleInfo = new NetSDKLib.NET_ANALYSE_RULE();
/** 
用户定义数据，通过client.notifyTaskResult回调中”UserDefineData”字段带回
*/
    public			byte[]         szUserDefineData = new byte[512];
/** 
模型远程文件url地址，目前支持http方式下载
*/
    public			byte[]         szModelUrl = new byte[512];
/** 
远程实时视频流信息
*/
    public			NET_REMOTE_STREAM_INFO stuRemoteStreamInfo = new NET_REMOTE_STREAM_INFO();
/** 
能源SDT仪器仪表使用;0：深度学习 1：建模方式 2:图片比较方式,通过PicUr下载图片下发给算法,进行对应检测
*/
    public			int            nDetectType;
/** 
图片远程文件url地址个数
*/
    public			int            nPicUrlNum;
/** 
图片远程文件url地址,目前支持http方式下载
*/
    public			byte[]         szPicUrl = new byte[64*512];
/** 
保留字节
*/
    public			byte[]         byReserved = new byte[256];

public NET_PUSH_PICTURE_BYRULE_INFO(){
}
}

