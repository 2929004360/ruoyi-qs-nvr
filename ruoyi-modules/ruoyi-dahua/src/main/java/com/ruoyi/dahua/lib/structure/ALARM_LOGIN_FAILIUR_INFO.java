package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  登陆失败事件 
* @origin autoTool
* @date 2023/09/06 16:45:08
*/
public class ALARM_LOGIN_FAILIUR_INFO extends NetSDKLib.SdkStructure {
    public			int            dwSize;
/** 
0:开始 1:停止
*/
    public			int            nAction;
/** 
序号
*/
    public			int            nSequence;
/** 
事件名,填用户名称
*/
    public			byte[]         szName = new byte[128];
/** 
登录类型
*/
    public			byte[]         szType = new byte[128];
/** 
来源IP地址
*/
    public			byte[]         szAddr = new byte[128];
/** 
用户登陆失败错误码
*/
    public			int            nError;
/** 
事件公共扩展字段结构体
*/
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();

public ALARM_LOGIN_FAILIUR_INFO(){
		this.dwSize=this.size();
}
}

