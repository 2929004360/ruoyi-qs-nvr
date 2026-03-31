package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  对应检测类型(NET_DIAGNOSIS_PTZ_MOVING)云台移动检测结果 
* @date 2022/08/03 14:26:52
*/
public class NET_PTZ_MOVING_DETECTIONRESULT extends NetSDKLib.SdkStructure {
/** 
此结构体大小
*/
    public			int            dwSize;
/** 
向上运动检测结果 {@link com.ruoyi.dahua.lib.enumeration.EM_PTZ_MOVING_RESULT_TYPE}
*/
    public			int            emPTZMovingUp;
/** 
向下运动检测结果 {@link com.ruoyi.dahua.lib.enumeration.EM_PTZ_MOVING_RESULT_TYPE}
*/
    public			int            emPTZMovingDown;
/** 
向左运动检测结果 {@link com.ruoyi.dahua.lib.enumeration.EM_PTZ_MOVING_RESULT_TYPE}
*/
    public			int            emPTZMovingLeft;
/** 
向右运动检测结果 {@link com.ruoyi.dahua.lib.enumeration.EM_PTZ_MOVING_RESULT_TYPE}
*/
    public			int            emPTZMovingRight;
/** 
变倍(大)运动检测结果 {@link com.ruoyi.dahua.lib.enumeration.EM_PTZ_MOVING_RESULT_TYPE}
*/
    public			int            emPTZMovingZoomWide;
/** 
变倍(小)运动检测结果 {@link com.ruoyi.dahua.lib.enumeration.EM_PTZ_MOVING_RESULT_TYPE}
*/
    public			int            emPTZMovingZoomTele;

public NET_PTZ_MOVING_DETECTIONRESULT(){
		this.dwSize=this.size();
}
}

