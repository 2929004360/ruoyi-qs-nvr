package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  视频算法类型检测 
* @date 2022/09/15 15:38:18
*/
public class CFG_VIDEO_ALGORITHMTYPE_DETECTION extends NetSDKLib.SdkStructure {
/** 
使能配置
*/
    public			int            bEnable;
/** 
人脸算法阀值
*/
    public			int            nFaceAlgorithmThreshold;
/** 
人脸算法百分占比，总100
*/
    public			CFG_VIDEO_ALGORITHMTYPE_ALGORITHM stuFaceAlgorithm = new CFG_VIDEO_ALGORITHMTYPE_ALGORITHM();
/** 
人体算法阀值
*/
    public			int            nHumanBodyAlgorithmThreshold;
/** 
人体算法百分占比，总100
*/
    public			CFG_VIDEO_ALGORITHMTYPE_ALGORITHM stuHumanBodyAlgorithm = new CFG_VIDEO_ALGORITHMTYPE_ALGORITHM();
/** 
车辆算法阀值
*/
    public			int            nVehicleAlgorithmThreshold;
/** 
车辆算法百分占比，总100
*/
    public			CFG_VIDEO_ALGORITHMTYPE_ALGORITHM stuVehicleAlgorithm = new CFG_VIDEO_ALGORITHMTYPE_ALGORITHM();
/** 
车牌算法阀值
*/
    public			int            nPlateNumAlgorithmThreshold;
/** 
车牌算法百分占比，总100
*/
    public			CFG_VIDEO_ALGORITHMTYPE_ALGORITHM stuPlateNumAlgorithm = new CFG_VIDEO_ALGORITHMTYPE_ALGORITHM();

public CFG_VIDEO_ALGORITHMTYPE_DETECTION(){
}
}

