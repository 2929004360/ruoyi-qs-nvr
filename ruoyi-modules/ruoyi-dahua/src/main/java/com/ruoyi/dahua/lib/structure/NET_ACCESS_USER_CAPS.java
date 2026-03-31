package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  user操作能力集 
* @origin autoTool
* @date 2023/02/10 14:34:21
*/
public class NET_ACCESS_USER_CAPS extends NetSDKLib.SdkStructure {
/** 
每次下发的最大数量
*/
    public			int            nMaxInsertRate;
/** 
用户数量上限
*/
    public			int            nMaxUsers;
/** 
每个用户可以记录的最大信息数量
*/
    public			int            nMaxFingerPrintsPerUser;
/** 
每个用户可以记录的最大卡片数量
*/
    public			int            nMaxCardsPerUser;
/** 
保留字节
*/
    public			byte[]         byReserved = new byte[512];

public			NET_ACCESS_USER_CAPS(){
}
}

