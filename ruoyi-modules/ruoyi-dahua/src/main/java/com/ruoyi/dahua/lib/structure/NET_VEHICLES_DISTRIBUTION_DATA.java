package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  统计区车辆，排队信息 
* @origin autoTool
* @date 2023/07/03 14:46:48
*/
public class NET_VEHICLES_DISTRIBUTION_DATA extends NetSDKLib.SdkStructure {
/** 
预置点
*/
    public			int            nPtzPresetId;
/** 
规则ID
*/
    public			int            nRuleId;
/** 
规则类型
197 : videoAnalyseCongestionDetection  车流拥堵规则配置（针对道路场景）

198 : videoAnalyseVehicleLimitDetection  车辆上限统计检测规则
 */
    public			int            nRuleType;
/** 
车辆数量
*/
    public			int            nVehiclesNum;
/** 
排队长度，单位：米
*/
    public			int            nQueueLen;
/** 
区域坐标 多边形中每个顶点的坐标坐标归一化到[0,8191]区间
*/
    public			NET_UINT_POINT[] stuRegion = new NET_UINT_POINT[32];
/** 
区域坐标个数
*/
    public			int            nRegionNum;
/** 
区域车辆上限
*/
    public			int            nVehiclesLimit;
/** 
规则名称
*/
    public			byte[]         szRuleName = new byte[32];

public			NET_VEHICLES_DISTRIBUTION_DATA(){
		for(int i=0;i<stuRegion.length;i++){
			stuRegion[i]=new NET_UINT_POINT();
			}
}
}

