package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  雷达区域检测事件(对应 DH_ALARM_RADAR_REGIONDETECTION) 
* @date 2022/09/01 20:26:50
*/
public class ALARM_RADAR_REGIONDETECTION_INFO extends NetSDKLib.SdkStructure {
/** 
事件动作1:Start 2:Stop
*/
    public			int            nAction;
/** 
事件发生的时间
*/
    public NET_TIME_EX      stuTime = new NET_TIME_EX();
/** 
通道号
*/
    public			int            nChannelID;
/** 
检测到的对象个数
*/
    public			int            nObjectNum;
/** 
雷达检测对象列表
*/
    public			NET_RADAR_DETECT_OBJECT[] stuObjects = new NET_RADAR_DETECT_OBJECT[100];
/** 
事件名称
*/
    public			byte[]         szName = new byte[128];
/** 
事件触发的预置点号
*/
    public			int            nPresetID;
/** 
检测区域顶点数
*/
    public			int            nDetectRegionNum;
/** 
检测区域,[0,8191]
*/
    public			NetSDKLib.NET_POINT[] stuDetectRegion = new NetSDKLib.NET_POINT[20];
/** 
报警类型 {@link com.ruoyi.dahua.lib.enumeration.EM_RADAR_ALARM_TYPE}
*/
    public			int            emAlarmType;
/** 
经度，扩大1000000倍，小数点后6位有效，不足6位用0补齐，例如120125400代表120.1254
*/
    public			int            nLongitude;
/** 
纬度，扩大1000000倍，小数点后6位有效，不足6位用0补齐，例如120125400代表120.1254
*/
    public			int            nLatitude;
/** 
智能事件规则编号，用于表示哪个规则触发的事件。
*/
    public			int            nRuleID;
/** 
RFID卡片数量
*/
    public			int            nCardNum;
/** 
RFID卡片信息
*/
    public			NET_RADAR_REGIONDETECTION_RFIDCARD_INFO[] stuCardInfo = new NET_RADAR_REGIONDETECTION_RFIDCARD_INFO[256];
/** 
报警等级，0 表示未知， 1表示预警警告，2表示报警
*/
    public			int            nAlarmLevel;
/** 
报警标志位，bit0表示是否超速，bit1表示是否AIS匹配 第2bit位表示是否禁行 第3bit位表示是否逆行
*/
    public			int            nAlarmFlag;
/** 
报警输入通道号
*/
    public			int            nAlarmChannel;
/** 
事件编号，用来唯一标志一个事件
*/
    public			int            nEventID;
/** 
触发事件目标的速度，用整型传输，扩大100倍 单位m/s
*/
    public			int            nSpeed;
/** 
触发事件目标的id,范围[0,63]
*/
    public			int            nTrackID;
/** 
触发事件目标的类型的掩码: 0x00未识别目标 0x01目标为人 0x02目标为交通工具 0x03目标为树 0x04目标为建筑物 0x05目标为屏幕 0x06目标为动物 0x07目标为大船 0x08目标为中船 0x09目标为小船
*/
    public			int            nObjectType;
/** 
车道/航道方向 -1:未知 0:无效 1:上行 2:下行
*/
    public			int            nUpDownGoing;
/** 
事件公共扩展字段结构体
*/
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
/** 
当前触发事件目标的像素极坐标值--距离，扩大100倍的结果,单位米
*/
    public			int            nDistance;
/** 
当前触发事件目标的极坐标值--角度，扩大100倍的结果，单位度
*/
    public			int            nAngle;
/** 
预留字节
*/
    public			byte[]         byReserved = new byte[992];

public ALARM_RADAR_REGIONDETECTION_INFO(){
		for(int i=0;i<stuObjects.length;i++){
			stuObjects[i]=new NET_RADAR_DETECT_OBJECT();
			}
		for(int i=0;i<stuDetectRegion.length;i++){
			stuDetectRegion[i]=new NetSDKLib.NET_POINT();
			}
		for(int i=0;i<stuCardInfo.length;i++){
			stuCardInfo[i]=new NET_RADAR_REGIONDETECTION_RFIDCARD_INFO();
			}
}
}

