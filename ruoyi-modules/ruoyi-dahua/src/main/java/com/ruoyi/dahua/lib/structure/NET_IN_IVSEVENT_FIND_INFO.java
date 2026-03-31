package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_IVSEventFind接口入参 
* @date 2022/11/02 13:47:07
*/
public class NET_IN_IVSEVENT_FIND_INFO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;
/** 
报警诊断类型
0:全部
1:未处理
2:正确
3:错误
4:重复正确报警
5:重复错误报警
*/
    public			int            nVaild;
/** 
查询开始时间
*/
    public NET_TIME         stStartTime = new NET_TIME();
/** 
查询结束时间
*/
    public NET_TIME         stEndTime = new NET_TIME();
/** 
通道号列表
*/
    public			int[]          nChannelList = new int[128];
/** 
nChannelList中有效通道号个数,填0表示查询所有通道号
*/
    public			int            dwChannelCnt;
/** 
dwEventCodeList中有效的事件类型个数
*/
    public			int            dwEventCodeCnt;
/** 
智能事件类型列表,参考 EVENT_IVS_ALL 这个宏下面的智能事件宏定义
注意,并非所有的事件都能查询,需要根据所用的IVS设备支持的情况,填写对应的智能事件类型
*/
    public			int[]          dwEventCodeList = new int[128];

public NET_IN_IVSEVENT_FIND_INFO(){
		this.dwSize=this.size();
}
}

