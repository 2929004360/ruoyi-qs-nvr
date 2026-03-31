package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  诱导屏系统配置(对应 CFG_CMD_GUIDESCREEN 命令) 
* @origin autoTool
* @date 2023/06/27 15:49:59
*/
public class CFG_GUIDESCREEN_INFO extends NetSDKLib.SdkStructure {
/** 
上下屏时,上屏显示内容
英文或常用中文汉字，中文需要GB2312编码，长度不超过64字节
*/
    public			byte[]         szText = new byte[128];
/**
 箭头标志数量
*/
    public			int            nArrowNum;
/** 
箭头标志设置, 对应的屏从左至右依次排列
*/
    public			CFG_GUISCREEN_ARROW[] stuArrow = new CFG_GUISCREEN_ARROW[8];

public			CFG_GUIDESCREEN_INFO(){
		for(int i=0;i<stuArrow.length;i++){
			stuArrow[i]=new CFG_GUISCREEN_ARROW();
			}
}
}

