package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  lris操作能力集 
* @origin autoTool
* @date 2023/02/10 14:34:21
*/
public class NET_ACCESS_IRIS_CAPS extends NetSDKLib.SdkStructure {
/** 
每次最大插入量
*/
    public			int            nMaxInsertRate;
/** 
眼睛信息图片最小尺寸,单位KB
*/
    public			int            nMinIrisPhotoSize;
/** 
眼睛信息图片最大尺寸，单位KB
*/
    public			int            nMaxIrisPhotoSize;
/** 
每个用户最多支持多少组
*/
    public			int            nMaxIrisGroup;
/** 
眼睛识别算法提供标识，0未知，1DH
*/
    public			int            nRecognitionAlgorithmVender;
/** 
算法(模型)版本号,如果版本号有多位，按Major/Minor从高到低每8bit表示一个版本 如1.5.2表示成0x00010502
*/
    public			int            nRecognitionVersion;
/** 
眼睛信息存储上限
*/
    public			int            nMaxIrisesCount;
/** 
保留字节
*/
    public			byte[]         byReserved = new byte[500];

public			NET_ACCESS_IRIS_CAPS(){
}
}

