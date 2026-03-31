package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_DeleteWorkSuitBySourceUID 接口输入参数
 * @date 2022/10/08 20:13:18
 */
public class NET_IN_DELETE_WORKSUIT_BY_SOURCEUID extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 全景图唯一标识符个数
	 */
    public int              nSourceUIDNum;
	/**
	 * 全景图唯一标识符,由平台生成
	 */
    public Byte64Arr[]      szSourceUID = new Byte64Arr[64];
	/**
	 * 工装合规组ID
	 */
    public byte[]           szGroupID = new byte[64];

	public NET_IN_DELETE_WORKSUIT_BY_SOURCEUID() {
		this.dwSize = this.size();
		for (int i = 0; i < szGroupID.length; i++) {
			szSourceUID[i]= new Byte64Arr();
		}
	}
}

