package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  算法百分占比，总100 
* @date 2022/09/15 15:38:18
*/
public class CFG_VIDEO_ALGORITHMTYPE_ALGORITHM extends NetSDKLib.SdkStructure {
/** 
检测百分比
*/
    public			int            nDetectionPercent;
/** 
识别百分比
*/
    public			int            nRecognitionPercent;
/** 
属性百分比
*/
    public			int            nAttributePercent;

public CFG_VIDEO_ALGORITHMTYPE_ALGORITHM(){
}
}

