package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description CLIENT_SetStateManager 接口入参
 * @date 2023/05/11 14:19:40
 */
public class NET_IN_SET_STATEMANAGER_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 状态枚举 {@link com.ruoyi.dahua.lib.enumeration.EM_STATEMANAGER_STATE}
	 */
    public int              emState;
	/**
	 * 状态信息，需要用户申请内存，枚举对应，详见EM_STATEMANAGER_STATE枚举说明
	 */
    public Pointer          pStateInfo;

	public NET_IN_SET_STATEMANAGER_INFO() {
		this.dwSize = this.size();
	}
}

