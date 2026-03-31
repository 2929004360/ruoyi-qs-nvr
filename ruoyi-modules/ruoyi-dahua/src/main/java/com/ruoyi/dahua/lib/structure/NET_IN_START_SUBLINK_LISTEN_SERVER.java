package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/** 
CLIENT_StartSubLinkListenServer 接口输入参数
*/
public class NET_IN_START_SUBLINK_LISTEN_SERVER extends NetSDKLib.SdkStructure {
/** 
/< 结构体大小
*/
    public			int            dwSize;
/** 
/< 本地服务信息
*/
    public			NET_LOCAL_SERVER_NET_INFO stuLoaclServerInfo = new NET_LOCAL_SERVER_NET_INFO();
/** 
/< 子连接监听服务回调函数
*/
    public			NetSDKLib.fSubLinkServiceCallBack cbSubLinkServiceCallBack;
/** 
/< 用户数据 LDWORD
*/
    public Pointer          dwUserData;

public NET_IN_START_SUBLINK_LISTEN_SERVER(){
    this.dwSize=this.size();
}
}

