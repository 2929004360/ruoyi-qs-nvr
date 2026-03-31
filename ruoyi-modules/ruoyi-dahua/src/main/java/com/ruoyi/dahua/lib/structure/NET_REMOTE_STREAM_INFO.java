package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  远程实时视频源信息("analyseTaskManager.analysePushPictureFileByRule"协议使用) 
* @date 2022/06/28 19:28:32
*/
public class NET_REMOTE_STREAM_INFO extends NetSDKLib.SdkStructure {
/** 
视频流协议类型 {@link NetSDKLib.EM_STREAM_PROTOCOL_TYPE}
*/
    public			int            emStreamProtocolType;
/** 
用于字节对齐
*/
    public			byte[]         byReserved1 = new byte[4];
/** 
视频流地址
*/
    public			byte[]         szPath = new byte[256];
/** 
IP 地址
*/
    public			byte[]         szIp = new byte[64];
/** 
端口号
*/
    public			short          wPort;
/** 
用户名
*/
    public			byte[]         szUser = new byte[64];
/** 
密码
*/
    public			byte[]         szPwd = new byte[64];
/** 
通道号
*/
    public			int            nChannelID;
/** 
码流类型, 0:主码流; 1:辅1码流; 2:辅2码流;
*/
    public			int            nStreamType;
/** 
保留字节
*/
    public			byte[]         byReserved = new byte[1024];

public NET_REMOTE_STREAM_INFO(){
}
}

