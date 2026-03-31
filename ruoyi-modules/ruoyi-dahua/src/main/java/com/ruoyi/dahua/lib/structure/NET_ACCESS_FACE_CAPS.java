package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  face操作能力集 
* @origin autoTool
* @date 2023/02/10 14:34:21
*/
public class NET_ACCESS_FACE_CAPS extends NetSDKLib.SdkStructure {
/** 
每次下发的最大数量
*/
    public			int            nMaxInsertRate;
/** 
目标存储上限
*/
    public			int            nMaxFace;
/** 
目标识别类型，0:白光 1:红外
*/
    public			int            nRecognitionType;
/** 
目标识别算法，0:未知1:华2:商3:依4:汉5:火
*/
    public			int            nRecognitionAlgorithm;
/** 
目标识别算法(模型)版本号，0x00010502 为1.5.2
*/
    public			int            dwRecognitionVersion;
/** 
白光目标照片的最小尺寸 ,单位 KB
*/
    public			int            nMinPhotoSize;
/** 
白光目标照片的最大尺寸 ,单位 KB
*/
    public			int            nMaxPhotoSize;
/** 
批量获取白光目标的最大数量
*/
    public			int            nMaxGetPhotoNumber;
/** 
是否支持获取白光照片
*/
    public			int            bIsSupportGetPhoto;
/** 
是否支持只下发目标特征值, 该字段已废弃
*/
    public			int            bIsSupportOnlyIssueFaceEigen;
/** 
是否支持只下发目标特征值
*/
    public			int            bIsSupportOnlyImportFaceEigen;
/** 
保留字节
*/
    public			byte[]         byReserved = new byte[496];

public			NET_ACCESS_FACE_CAPS(){
}
}

