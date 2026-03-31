package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  事件类型EVENT_IVS_VEHICLEDETECT(机动车事件)对应的规则配置 
* @origin autoTool
* @date 2023/05/29 11:40:48
*/
public class NET_VEHICLEDETECT_RULE_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
车辆抓拍阈值,是一个百分数，表示当车辆检测框高度占画面百分比大于这个阈值时，触发规则进行抓拍。
*/
    public			int            nSnapThreshold;
/** 
检测区顶点数
*/
    public			int            nDetectRegionPoint;
/** 
检测区
*/
    public			POINTCOORDINATE[] stuDetectRegion = new POINTCOORDINATE[20];
/** 
排除区域数
*/
    public			int            nExcludeRegionNum;
/** 
排除区域
*/
    public			DH_POLY_POINTS[] stuExcludeRegion = new DH_POLY_POINTS[10];
/** 
合规报警规则
*/
    public			NET_COMPLIANT_INFO stuCompliant = new NET_COMPLIANT_INFO();
/** 
是否提取机动车特征向量
*/
    public			int            bFeatureExtractEnable;
/** 
是否使用尺寸过滤器
*/
    public			int            bSizeFileter;
/** 
规则特定的尺寸过滤器，提高判断精度
*/
    public NET_CFG_SIZEFILTER_INFO stuSizeFileter = new NET_CFG_SIZEFILTER_INFO();

public NET_VEHICLEDETECT_RULE_INFO(){
		for(int i=0;i<stuDetectRegion.length;i++){
			stuDetectRegion[i]=new POINTCOORDINATE();
			}
		for(int i=0;i<stuExcludeRegion.length;i++){
			stuExcludeRegion[i]=new DH_POLY_POINTS();
			}
		this.dwSize=this.size();
}
}

