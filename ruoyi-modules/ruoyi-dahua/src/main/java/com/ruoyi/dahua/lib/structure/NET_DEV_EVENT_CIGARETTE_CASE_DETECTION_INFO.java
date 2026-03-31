package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  事件类型 EVENT_IVS_CIGARETTE_CASE_DETECTION (烟盒检测事件)对应的数据块描述信息 
* @date 2022/12/14 14:33:00
*/
public class NET_DEV_EVENT_CIGARETTE_CASE_DETECTION_INFO extends NetSDKLib.SdkStructure {
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
事件名称
*/
    public			byte[]         szName = new byte[128];
/** 
时间戳(单位是毫秒)
*/
    public			double         PTS;
/** 
智能事件所属大类 {@link com.ruoyi.dahua.lib.enumeration.EM_CLASS_TYPE}
*/
    public			int            emClassType;
/** 
事件时间毫秒数
*/
    public			int            nUTCMS;
/** 
事件ID
*/
    public			int            nEventID;
/** 
事件组ID，一次检测的多个nGroupID相同
*/
    public			int            nGroupID;
/** 
一个事件组内的抓拍张数,一次检测的多个nCountInGroup相同
*/
    public			int            nCountInGroup;
/** 
一个事件组内的抓拍序号，从1开始
*/
    public			int            nIndexInGroup;
/** 
销售香烟信息数量
*/
    public			int            nCigaretteCaseNum;
/** 
销售香烟信息
*/
    public			NET_CIGARETTE_CASE_INFO[] stuCigaretteCaseInfo = new NET_CIGARETTE_CASE_INFO[50];
/** 
预留字节
*/
    public			byte[]         szReserved = new byte[1024];

public NET_DEV_EVENT_CIGARETTE_CASE_DETECTION_INFO(){
		for(int i=0;i<stuCigaretteCaseInfo.length;i++){
			stuCigaretteCaseInfo[i]=new NET_CIGARETTE_CASE_INFO();
			}
}
}

