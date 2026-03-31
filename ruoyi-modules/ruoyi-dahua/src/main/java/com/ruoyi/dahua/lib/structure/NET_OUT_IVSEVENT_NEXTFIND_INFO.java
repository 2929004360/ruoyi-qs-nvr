package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/** 
* @author 291189
* @description  CLIENT_IVSEventNextFind接口出参 
* @date 2022/11/02 13:51:15
*/
public class NET_OUT_IVSEVENT_NEXTFIND_INFO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;
/** 
pstuEventInfo的个数, 等于 NET_IN_IVSEVENT_NEXTFIND_INFO 的nLimit字段
*/
    public			int            dwEventCnt;
/** 
事件信息数组,用户分配内存,大小为sizeof(NET_IVSEVENT_EVENT_INFO)*dwEventCnt
*/
    public Pointer          pstuEventInfo;
/** 
返回的pstuEventInfo个数
*/
    public			int            dwRetEventCnt;

public NET_OUT_IVSEVENT_NEXTFIND_INFO(){
		this.dwSize=this.size();
}
}

