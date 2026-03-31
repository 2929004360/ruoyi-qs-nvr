package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description CLIENT_GetStateManager 接口出参
 * @date 2023/05/11 14:18:50
 */
public class NET_OUT_GET_STATEMANAGER_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 字节对齐
	 */
    public byte[]           szReserved1 = new byte[4];
	/**
	 * 返回的状态信息，需要用户申请内存，与入参枚举对应，详见EM_STATEMANAGER_STATE枚举说明
	 */
    public Pointer          pStateInfo;

	public NET_OUT_GET_STATEMANAGER_INFO() {
		this.dwSize = this.size();
	}
}

