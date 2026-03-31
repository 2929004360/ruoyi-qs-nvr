package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_AccessControlCaptureCmd 输入结构体
 * @date 2022/12/30 10:55:59
 */
public class NET_IN_ACCESSCONTROL_CAPTURE_CMD extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 采集类型 (EM_COLLECTION_TYPE已被使用)
	 * {@link com.ruoyi.dahua.lib.enumeration.EM_GATHER_TYPE}
	 */
    public int              emGathertype;
	/**
	 * 用户ID（智能楼宇需求，可选）
	 */
    public byte[]           szUserID = new byte[12];

	public NET_IN_ACCESSCONTROL_CAPTURE_CMD() {
		this.dwSize = this.size();
	}
}

