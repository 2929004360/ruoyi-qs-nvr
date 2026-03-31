package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/** 
* @author 291189
* @description CLIENT_AttachCustomSnapInfo 接口输入参数
* @date 2022/03/31 11:28:46
*/
public class NET_IN_ATTACH_CUSTOM_SNAP_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
订阅通道号
*/
    public			int            nChannelID;
/** 
自定义定时抓图订阅回调函数
*/
    public			NetSDKLib.fAttachCustomSnapInfo cbCustomSnapInfo;
/** 
用户数据
*/
    public Pointer          dwUser;

public NET_IN_ATTACH_CUSTOM_SNAP_INFO(){
this.dwSize=this.size();
}
}

