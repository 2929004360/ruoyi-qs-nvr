package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description WLAN配置(对应 CFG_CMD_WLAN)
 * @date 2023/03/16 11:15:26
 */
public class CFG_NETAPP_WLAN extends NetSDKLib.SdkStructure {
	/**
	 * stuWlanInfo有效的WLAN配置个数
	 */
    public int              nNum;
	/**
	 * WLAN配置信息
	 */
    public CFG_WLAN_INFO[]  stuWlanInfo = new CFG_WLAN_INFO[8];

	public CFG_NETAPP_WLAN() {
		for (int i = 0; i < stuWlanInfo.length; i++) {
			stuWlanInfo[i] = new CFG_WLAN_INFO();
		}
	}
}

