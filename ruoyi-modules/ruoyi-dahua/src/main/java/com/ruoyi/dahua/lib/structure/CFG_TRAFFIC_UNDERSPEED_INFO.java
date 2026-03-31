package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  事件类型 EVENT_IVS_TRAFFIC_UNDERSPEED (交通违章-欠速)对应的规则配置 
* @date 2022/09/07 15:17:26
*/
public class CFG_TRAFFIC_UNDERSPEED_INFO extends NetSDKLib.SdkStructure {
/** 
规则名称,不同规则不能重名
*/
    public			byte[]         szRuleName = new byte[128];
/** 
规则使能
*/
    public			byte           bRuleEnable;
/** 
保留字段
*/
    public			byte[]         bReserved = new byte[3];
/** 
相应物体类型个数
*/
    public			int            nObjectTypeNum;
/** 
相应物体类型列表
*/
    public			byte[]         szObjectTypes = new byte[16*128];
/** 
云台预置点编号	0~65535
*/
    public			int            nPtzPresetId;
/** 
车道编号
*/
    public			int            nLane;
/** 
速度上限
*/
    public			int            nSpeedUpperLimit;
/** 
速度下限
*/
    public			int            nSpeedLowerLimit;
/** 
最短触发时间	单位：秒
*/
    public			int            nMinDuration;
/** 
报警联动
*/
    public NetSDKLib.CFG_ALARM_MSG_HANDLE stuEventHandler = new NetSDKLib.CFG_ALARM_MSG_HANDLE();
/** 
事件响应时间段
*/
    public			NetSDKLib.CFG_TIME_SECTION[] stuTimeSection = new NetSDKLib.CFG_TIME_SECTION[7*10];

public CFG_TRAFFIC_UNDERSPEED_INFO(){
		for(int i=0;i<stuTimeSection.length;i++){
			stuTimeSection[i]=new NetSDKLib.CFG_TIME_SECTION();
			}
}
}

