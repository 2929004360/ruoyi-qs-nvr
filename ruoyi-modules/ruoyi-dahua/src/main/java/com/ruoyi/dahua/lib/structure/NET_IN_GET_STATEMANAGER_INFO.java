package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GetStateManager 接口入参
 * @date 2023/05/11 14:18:32
 */
public class NET_IN_GET_STATEMANAGER_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 状态枚举 {@link com.ruoyi.dahua.lib.enumeration.EM_STATEMANAGER_STATE}
	 */
    public int              emState;

	public NET_IN_GET_STATEMANAGER_INFO() {
		this.dwSize = this.size();
	}
}

