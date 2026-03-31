package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  视频算法类型检测 
* @date 2022/08/03 14:43:06
*/
public class NET_VIDEO_ALGORITHMTYPE_DETECTIONRESULT extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
是否满足人脸算法条件
*/
    public			int            bFaceAlgorithm;
/** 
人脸算法分值
*/
    public			int            nFaceAlgorithmValue;
/** 
是否满足人体算法条件
*/
    public			int            bHumanBodyAlgorithm;
/** 
人体算法分值
*/
    public			int            nHumanBodyAlgorithmValue;
/** 
是否满足车辆算法条件
*/
    public			int            bVehicleAlgorithm;
/** 
车辆算法分值
*/
    public			int            nVehicleAlgorithmValue;
/** 
是否满足车牌算法条件
*/
    public			int            bPlateNumAlgorithm;
/** 
车牌算法分值
*/
    public			int            nPlateNumAlgorithmValue;

public NET_VIDEO_ALGORITHMTYPE_DETECTIONRESULT(){
		this.dwSize=this.size();
}
}

