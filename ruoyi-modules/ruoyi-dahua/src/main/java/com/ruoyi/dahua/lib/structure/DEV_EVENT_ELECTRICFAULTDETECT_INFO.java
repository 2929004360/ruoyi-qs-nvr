package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  仪表类缺陷检测事件 
* @date 2022/06/28 19:44:57
*/
public class DEV_EVENT_ELECTRICFAULTDETECT_INFO extends NetSDKLib.SdkStructure {
/** 
智能事件所属大类 {@link com.ruoyi.dahua.lib.enumeration.EM_CLASS_TYPE}
*/
    public			int            emClassType;
/** 
视频通道号
*/
    public			int            nChannel;
/** 
智能事件规则编号，用于标示哪个规则触发的事件
*/
    public			int            nRuleID;
/** 
事件ID
*/
    public			int            nEventID;
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
预置点ID
*/
    public			int            nPresetID;
/** 
事件时间毫秒数
*/
    public			int            nUTCMS;
/** 
对应设备所使能的检测规则
*/
    public			int[]          emEnableRules = new int[16];
/** 
设备所使能的检测规则个数
*/
    public			int            nEnableRulesNum;
/** 
挂空悬浮物检测异常输出结果个数
*/
    public			int            nAirborneDetectNum;
/** 
挂空悬浮物检测异常输出结果
*/
    public			NET_AIRBORNE_DETECT[] stuAirborneDetectInfo = new NET_AIRBORNE_DETECT[8];
/** 
鸟巢检测结果
*/
    public			NET_NEST_DETECT[] stuNestDetectInfo = new NET_NEST_DETECT[8];
/** 
鸟巢检测结果个数
*/
    public			int            nNestDetectNum;
/** 
表盘检测结果个数
*/
    public			int            nDialDetectNum;
/** 
表盘检测结果
*/
    public			NET_DIAL_DETECT[] stuDialDetectInfo = new NET_DIAL_DETECT[8];
/** 
渗漏检测结果
*/
    public			NET_LEAKAGE_DETECT[] stuLeakageDetectInfo = new NET_LEAKAGE_DETECT[8];
/** 
渗漏检测结果个数
*/
    public			int            nLeakageDetectNum;
/** 
箱门检测结果个数
*/
    public			int            nDoorDetectNum;
/** 
箱门检测结果
*/
    public			NET_DOOR_DETECT[] stuDoorDetectInfo = new NET_DOOR_DETECT[8];
/** 
呼吸器检测结果
*/
    public			NET_RESPIRATOR_DETECT[] stuRespiratorDetectInfo = new NET_RESPIRATOR_DETECT[8];
/** 
呼吸器检测个数
*/
    public			int            nRespiratorDetectNum;
/** 
吸烟检测结果个数
*/
    public			int            nSmokingDetectNum;
/** 
吸烟检测结果
*/
    public			NET_SMOKING_DETECT[] stuSmokingDetectInfo = new NET_SMOKING_DETECT[8];
/** 
大图
*/
    public NetSDKLib.SCENE_IMAGE_INFO stuSceneImageInfo = new NetSDKLib.SCENE_IMAGE_INFO();
/** 
绝缘子检测结果
*/
    public			NET_INSULATOR_DETECT[] stuInsulatorDetectInfo = new NET_INSULATOR_DETECT[8];
/** 
绝缘子检测结果个数
*/
    public			int            nInsulatorDetectNum;
/** 
盖板检测结果个数
*/
    public			int            nCoverPlateDetectNum;
/** 
盖板检测结果
*/
    public			NET_COVER_PLATE_DETECT[] stuCoverPlateDetectInfo = new NET_COVER_PLATE_DETECT[8];
/** 
压板检测结果
*/
    public			NET_PRESSING_PLATE_DETECT[] stuPressingPlateDetectInfo = new NET_PRESSING_PLATE_DETECT[8];
/** 
压板检测结果个数
*/
    public			int            nPressingPlateDetectNum;
/** 
金属锈蚀结果个数
*/
    public			int            nMetalCorrosionNum;
/** 
金属锈蚀结果
*/
    public			NET_METAL_CORROSION[] stuMetalCorrosionInfo = new NET_METAL_CORROSION[8];
/** 
预留字段
*/
    public			byte[]         bReserved = new byte[1024];

public DEV_EVENT_ELECTRICFAULTDETECT_INFO(){
		for(int i=0;i<stuAirborneDetectInfo.length;i++){
			stuAirborneDetectInfo[i]=new NET_AIRBORNE_DETECT();
			}
		for(int i=0;i<stuNestDetectInfo.length;i++){
			stuNestDetectInfo[i]=new NET_NEST_DETECT();
			}
		for(int i=0;i<stuDialDetectInfo.length;i++){
			stuDialDetectInfo[i]=new NET_DIAL_DETECT();
			}
		for(int i=0;i<stuLeakageDetectInfo.length;i++){
			stuLeakageDetectInfo[i]=new NET_LEAKAGE_DETECT();
			}
		for(int i=0;i<stuDoorDetectInfo.length;i++){
			stuDoorDetectInfo[i]=new NET_DOOR_DETECT();
			}
		for(int i=0;i<stuRespiratorDetectInfo.length;i++){
			stuRespiratorDetectInfo[i]=new NET_RESPIRATOR_DETECT();
			}
		for(int i=0;i<stuSmokingDetectInfo.length;i++){
			stuSmokingDetectInfo[i]=new NET_SMOKING_DETECT();
			}
		for(int i=0;i<stuInsulatorDetectInfo.length;i++){
			stuInsulatorDetectInfo[i]=new NET_INSULATOR_DETECT();
			}
		for(int i=0;i<stuCoverPlateDetectInfo.length;i++){
			stuCoverPlateDetectInfo[i]=new NET_COVER_PLATE_DETECT();
			}
		for(int i=0;i<stuPressingPlateDetectInfo.length;i++){
			stuPressingPlateDetectInfo[i]=new NET_PRESSING_PLATE_DETECT();
			}
		for(int i=0;i<stuMetalCorrosionInfo.length;i++){
			stuMetalCorrosionInfo[i]=new NET_METAL_CORROSION();
			}
}
}

