package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  二维空间点 
* @origin autoTool
* @date 2023/05/29 11:40:40
*/
public class DH_POLY_POINTS extends NetSDKLib.SdkStructure {
/** 
顶点数
*/
    public			int            nPointNum;
/** 
顶点信息
*/
    public			DH_POINT[]     stuPoints = new DH_POINT[20];

public DH_POLY_POINTS(){
		for(int i=0;i<stuPoints.length;i++){
			stuPoints[i]=new DH_POINT();
			}
}
}

