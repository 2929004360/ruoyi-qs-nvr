package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  fingerprint操作能力集 
* @origin autoTool
* @date 2023/02/10 14:34:21
*/
public class NET_ACCESS_FINGERPRINT_CAPS extends NetSDKLib.SdkStructure {
/** 
每次下发的最大数量
*/
    public			int            nMaxInsertRate;
/** 
单信息数据的最大字节数
*/
    public			int            nMaxFingerprintSize;
/** 
信息数量上限
*/
    public			int            nMaxFingerprint;
/** 
保留字节
*/
    public			byte[]         byReserved = new byte[512];

public			NET_ACCESS_FINGERPRINT_CAPS(){
}
}

