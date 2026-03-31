package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 119178
 * @version 1.0
 * @description 配置二维码有效时间
 * 对应枚举{@link com.ruoyi.dahua.lib.enumeration.NET_EM_CFG_OPERATE_TYPE#NET_EM_CFG_QR_CODE}
 * @date 2020/12/11
 */
public class NET_CFG_QR_CODE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 业主二维码有效时间，单位分钟
	 */
    public int              nValidTime;

	public NET_CFG_QR_CODE_INFO() {
		 this.dwSize = this.size();
	}
}

