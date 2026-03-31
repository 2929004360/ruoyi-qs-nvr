package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  事件类型EVENT_IVS_REMOTE_APPROVAL_ALARM(金融远程审批事件)对应的数据块描述信息 
* @date 2022/08/10 10:37:46
*/
public class NET_DEV_EVENT_REMOTE_APPROVAL_ALARM_INFO extends NetSDKLib.SdkStructure {
/** 
通道号
*/
    public			int            nChannelID;
/** 
1:开始 2:停止
*/
    public			int            nAction;
/** 
事件名称
*/
    public			byte[]         szName = new byte[128];
/** 
时间戳(单位是毫秒)
*/
    public			double         PTS;
/** 
事件发生的时间
*/
    public NET_TIME_EX      UTC = new NET_TIME_EX();
/** 
事件ID
*/
    public			int            nEventID;
/** 
审批单号
*/
    public			int            nApprovalNo;
/** 
操作类型，-1:未知，0:存箱, 1:取箱
*/
    public			int            nType;
/** 
审批超时时间，单位秒
*/
    public			int            nTimeout;
/** 
存取的舱位下标数量
*/
    public			int            nCabinListNum;
/** 
存取的舱位下标数组
*/
    public			int[]          nCabinList = new int[32];
/** 
存取人ID
*/
    public			byte[]         szAccessorID = new byte[64];
/** 
存取人名称
*/
    public			byte[]         szAccessorName = new byte[64];
/** 
复核人ID
*/
    public			byte[]         szReviewerID = new byte[64];
/** 
复核人名称
*/
    public			byte[]         szReviewerName = new byte[64];
/** 
图片信息数组
*/
    public			NET_IMAGE_INFO_EX2[] stuImageInfo = new NET_IMAGE_INFO_EX2[32];
/** 
图片信息个数
*/
    public			int            nImageInfoNum;
/** 
预留字段
*/
    public			byte[]         szReserved = new byte[508];

public NET_DEV_EVENT_REMOTE_APPROVAL_ALARM_INFO(){
		for(int i=0;i<stuImageInfo.length;i++){
			stuImageInfo[i]=new NET_IMAGE_INFO_EX2();
			}
}
}

