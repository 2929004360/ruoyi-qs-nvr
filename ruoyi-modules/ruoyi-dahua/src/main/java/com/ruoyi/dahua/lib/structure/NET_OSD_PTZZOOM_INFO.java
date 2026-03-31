package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  编码物件-变倍叠加配置 
* @origin autoTool
* @date 2023/03/01 10:02:37
*/
public class NET_OSD_PTZZOOM_INFO extends NetSDKLib.SdkStructure {
    public			int            dwSize;
/** 
是否叠加到主码流
*/
    public			int            bMainBlend;
/** 
是否叠加到预览码流
*/
    public			int            bPreviewBlend;
/** 
前景色
*/
    public			NET_COLOR_RGBA stuFrontColor = new NET_COLOR_RGBA();
/** 
背景色
*/
    public			NET_COLOR_RGBA stuBackColor = new NET_COLOR_RGBA();
/** 
区域, 坐标取值0~8191, 仅使用left和top值, 点(left,top)应和(right,bottom)设置成同样的点
*/
    public NET_RECT         stuRect = new NET_RECT();
/** 
显示时间，单位为妙，0表示一直显示
*/
    public			int            nDisplayTime;

public NET_OSD_PTZZOOM_INFO(){
		this.dwSize=this.size();
}
}

