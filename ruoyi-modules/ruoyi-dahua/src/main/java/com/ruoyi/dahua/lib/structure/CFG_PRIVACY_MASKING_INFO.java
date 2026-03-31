package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 251823
* @description 
* @date 2022/07/21 14:54:08
*/
public class CFG_PRIVACY_MASKING_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 隐私遮挡配置个数
	 */
    public int              nMaskingInfoCount;
	/**
	 * 隐私遮挡配置信息
	 */
    public CGF_MASKING_INFO[] stuMaskingInfo = new CGF_MASKING_INFO[64];

	public CFG_PRIVACY_MASKING_INFO() {
		for (int i = 0; i < stuMaskingInfo.length; i++) {
			stuMaskingInfo[i] = new CGF_MASKING_INFO();
		}
	}
}

