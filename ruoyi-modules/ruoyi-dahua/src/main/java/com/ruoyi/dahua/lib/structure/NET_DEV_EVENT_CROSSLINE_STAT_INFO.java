package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  事件类型 EVENT_IVS_CROSSLINE_STAT (绊线计数事件)对应的数据块描述信息( 注意该事件暂时不支持单独使用，仅配合“开放智能事件(EVENT_IVS_OPEN_INTELLI)”使用) 
* @date 2023/02/06 16:07:27
*/
public class NET_DEV_EVENT_CROSSLINE_STAT_INFO extends NetSDKLib.SdkStructure {
/** 
通道号
*/
    public			int            nChannelID;
/** 
0:脉冲,1:开始, 2:停止
*/
    public			int            nAction;
/** 
扩展协议字段
*/
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
/** 
事件发生的时间
*/
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
/** 
事件ID
*/
    public			int            nEventID;
/** 
事件名称
*/
    public			byte[]         szName = new byte[128];
/** 
智能事件所属大类
*/
    public			byte[]         szClass = new byte[16];
/** 
相对事件时间戳,(单位是毫秒)
*/
    public			double         dbPTS;
/** 
事件时间毫秒数
*/
    public			int            nUTCMS;
/** 
智能事件规则编号
*/
    public			int            nRuleID;
/** 
区域ID
*/
    public			int            nAreaID;
/** 
绊线方向 {@link com.ruoyi.dahua.lib.enumeration.EM_STAT_DIRECTION}
*/
    public			int            emDirection;
/** 
目标绊线总数
*/
    public			int            nNumber;
/** 
详细计数信息个数
*/
    public			int            nStatDetailNum;
/** 
详细计数信息
*/
    public			NET_STAT_DETAIL[] stuStatDetail = new NET_STAT_DETAIL[16];
/** 
预留字节
*/
    public			byte[]         szReserved = new byte[1024];

public NET_DEV_EVENT_CROSSLINE_STAT_INFO(){
		for(int i=0;i<stuStatDetail.length;i++){
			stuStatDetail[i]=new NET_STAT_DETAIL();
			}
}
}

