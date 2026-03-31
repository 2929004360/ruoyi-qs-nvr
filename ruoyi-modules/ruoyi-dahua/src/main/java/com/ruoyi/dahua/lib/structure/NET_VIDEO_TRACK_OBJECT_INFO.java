package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  视频目标信息 
* @origin autoTool
* @date 2023/09/22 13:56:25
*/
public class NET_VIDEO_TRACK_OBJECT_INFO extends NetSDKLib.SdkStructure {
/** 
/ 目标uuid
*/
    public			byte[]         szObjectUUID = new byte[128];
/** 
/ 目标类型
*/
    public			int[]          emObjectType = new int[32];
/** 
/ 目标类型的个数
*/
    public			int            nObjectTypeNum;
/** 
/ 包围盒
*/
    public NET_RECT         stuBoundingBox = new NET_RECT();

public NET_VIDEO_TRACK_OBJECT_INFO(){
}
}

