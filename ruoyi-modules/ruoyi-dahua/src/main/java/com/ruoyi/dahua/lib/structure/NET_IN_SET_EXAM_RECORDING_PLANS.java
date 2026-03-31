package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/** 
* @author 291189
* @description  CLIENT_SetExamRecordingPlans 接口入参 
* @date 2022/05/16 20:40:47
*/
public class NET_IN_SET_EXAM_RECORDING_PLANS extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
考试计划个数
*/
    public			int            nPlansNum;
/** 
考试计划, 由用户申请和释放, 最大为1024个 {@link NET_EXAM_RECORDING_PLAN_INFO}
*/
    public Pointer          pstuPlans;

public NET_IN_SET_EXAM_RECORDING_PLANS(){
		this.dwSize=this.size();
}
}

