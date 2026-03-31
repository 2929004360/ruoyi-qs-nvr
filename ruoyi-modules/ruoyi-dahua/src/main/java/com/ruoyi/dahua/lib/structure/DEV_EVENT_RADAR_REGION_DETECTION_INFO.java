package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  事件类型EVENT_IVS_RADAR_REGION_DETECTION(雷达警戒区检测事件)对应的数据块描述信息 
* @date 2022/09/01 20:07:33
*/
public class DEV_EVENT_RADAR_REGION_DETECTION_INFO extends NetSDKLib.SdkStructure {
/** 
通道号
*/
    public			int            nChannelID;
/** 
0:脉冲 1:开始 2:停止
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
智能事件规则编号，用于标示哪个规则触发的事件
*/
    public			int            nRuleID;
/** 
智能事件所属大类 {@link com.ruoyi.dahua.lib.enumeration.EM_CLASS_TYPE}
*/
    public			int            emClassType;
/** 
事件对应文件信息
*/
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo = new NetSDKLib.NET_EVENT_FILE_INFO();
/** 
检测到的对象个数
*/
    public			int            nObjectNum;
/** 
雷达检测对象列表
*/
    public			NET_RADAR_DETECT_OBJECT[] stuObjects = new NET_RADAR_DETECT_OBJECT[100];
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
全景广角图（当图片超过1张时 使用stuSceneImageEx）
*/
    public NetSDKLib.SCENE_IMAGE_INFO_EX stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO_EX();
/** 
报警类型 {@link com.ruoyi.dahua.lib.enumeration.EM_RADAR_ALARM_TYPE}
*/
    public			int            emAlarmType;
/** 
报警等级
*/
    public			byte[]         szAlarmLevel = new byte[16];
/** 
报警输入通道号
*/
    public			int            nAlarmChannel;
/** 
RFID卡片信息个数
*/
    public			int            nRFIDCardIdNum;
/** 
RFID卡片信息，最多支持256张卡片信息
*/
    public			NET_RFID_CARD_INFO[] stuRFIDCardId = new NET_RFID_CARD_INFO[256];
/** 
全景广角图（扩展为10张）
*/
    public			NetSDKLib.SCENE_IMAGE_INFO_EX[] stuSceneImageEx = new NetSDKLib.SCENE_IMAGE_INFO_EX[10];
/** 
全景广角图个数
*/
    public			int            nstuSceneImageExNum;
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
报警标志位，第0bit位表示是否超速，第1bit位表示是否AIS匹配 第2bit位表示是否禁行 第3bit位表示是否逆行
*/
    public			int            nAlarmFlag;
/** 
经度，用整型传输，1000000倍，小数点后6位有效，不足6位用0补齐
*/
    public			int            nLongitude;
/** 
纬度，用整型传输，1000000倍，小数点后6位有效，不足6位用0补齐
*/
    public			int            nLatitude;
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
    public			byte[]         byReserved = new byte[988];

public DEV_EVENT_RADAR_REGION_DETECTION_INFO(){
		for(int i=0;i<stuObjects.length;i++){
			stuObjects[i]=new NET_RADAR_DETECT_OBJECT();
			}
		for(int i=0;i<stuDetectRegion.length;i++){
			stuDetectRegion[i]=new NetSDKLib.NET_POINT();
			}
		for(int i=0;i<stuRFIDCardId.length;i++){
			stuRFIDCardId[i]=new NET_RFID_CARD_INFO();
			}
		for(int i=0;i<stuSceneImageEx.length;i++){
			stuSceneImageEx[i]=new NetSDKLib.SCENE_IMAGE_INFO_EX();
			}
}
}

