package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  获取输出状态 输出参数。此时CLIENT_GetAlarmRegionInfo的emType参数为NET_EM_GET_ALARMREGION_INFO_OUTPUTSTATE 
* @origin autoTool
* @date 2023/08/09 16:12:44
*/
public class NET_OUT_GET_OUTPUT_STATE extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
状态个数
*/
    public			int            nStateRet;
/** 
状态false:关闭true打开
*/
    public			byte[]         arrStates = new byte[82];
/** 
对齐
*/
    public			byte[]         byReserved = new byte[6];
/** 
状态个数扩展 超过82个使用这个字段
*/
    public			int            nStateRetEx;
/** 
状态FLASE:关闭TRUE打开
*/
    public			int[]          arrStatesEx = new int[256];
/** 
对齐
*/
    public			byte[]         byReserved1 = new byte[4];

public NET_OUT_GET_OUTPUT_STATE(){
		this.dwSize=this.size();
}
}

