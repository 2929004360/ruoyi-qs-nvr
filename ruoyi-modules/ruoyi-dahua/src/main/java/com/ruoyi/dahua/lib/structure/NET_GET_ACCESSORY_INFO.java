package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/** 
* @author 291189
* @description  获取配件信息(对应DH_DEVSTATE_GET_ACCESSORY_INFO) 
* @date 2022/08/31 20:25:40
*/
public class NET_GET_ACCESSORY_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
配件序列号数组个数（当个数为0时,返回主机下所有配件状态）
*/
    public			int            nSNNum;
/** 
配件序列号数组
*/
    public			BYTE_32[]      szSN = new BYTE_32[56];
/** 
申请的配件个数
*/
    public			int            nMaxInfoNum;
/** 
配件信息指针,由调用者分配内存,分配的大小为 nMaxAccessoryInfo*sizeof(NET_WPAN_ACCESSORY_INFO)
*/
    public Pointer          pstuInfo;
/** 
返回的配件数
*/
    public			int            nInfoNum;

public NET_GET_ACCESSORY_INFO(){

	for(int i=0;i<szSN.length;i++){
		szSN[i]=new BYTE_32();
	}
	this.dwSize=this.size();
}
}

